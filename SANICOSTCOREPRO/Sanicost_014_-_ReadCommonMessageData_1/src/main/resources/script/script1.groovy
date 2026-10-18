/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message;

//ActivityCodExt
def getActivityID(completeXml){
    def sActivityCodExt = "";
    
    def aActivityCodExt = completeXml.PV1.'PV1.52'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text()
    if(aActivityCodExt){
        sActivityCodExt = aActivityCodExt[0];
    }
           
    return sActivityCodExt;
}

//Obtener paciente
def getPaciente(completeXml){
    def sPaciente = "";
    
    def aPaciente = completeXml.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
    if(aPaciente){
        sPaciente = aPaciente[0];
    }
    
    return sPaciente;
}

//Obtener garante y plan de cobertura aplicado
def getDeudor(completeXml){
    def oReturn = {};
    
    //Por defecto consideramos que es un privado.
    oReturn.DeudorID = getPaciente(completeXml);
    oReturn.PlanCoberturaID = ""; 
    oReturn.ClasifEconomicaID = 'X'; //Clasificación Economica generica
    oReturn.EsCatSalut = "";
    
    def aGaranteRelationNode = completeXml.GT1.'GT1.11'.'**';
    if (aGaranteRelationNode){
        def sGaranteRelation = aGaranteRelationNode[0];
        if (sGaranteRelation != 'SEL') {
            //Es un privado pero quien paga no es él mismo.
            //Otro garante que no es el propio paciente
            def aGaranteRelatedNode = completeXml.GT1.'GT1.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
            if (aGaranteRelatedNode){
                oReturn.DeudorID = aGaranteRelatedNode[0];
            }
        }
    }
    else {
        //Es una mutua o aseguradora
        def aMutuaNode = completeXml.IN1.'IN1.3'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
        if (aMutuaNode){
            //Código de aseguradora
            oReturn.DeudorID = aMutuaNode[0];
            def aPlanCoberturaNode = completeXml.IN1.'IN1.2'.'**'.findAll{ node-> node.name() == 'CWE.1' }*.text();
            if (aPlanCoberturaNode){
                //Plan de cobertura aplicado
                oReturn.PlanCoberturaID = aPlanCoberturaNode[0]
            }
        }
    }
    
    if (oReturn.PlanCoberturaID == 'SCS'){
        oReturn.ClasifEconomicaID = 'S';
        oReturn.EsCatSalut = 'X';
    }
    
    return oReturn;
}

//Prestacion HIS
def getPrestacionHIS(completeXml){
    def sPrestacionHIS = ""
    
    /*
    // En el caso de las intervenciones quirúrgicas no viene prestación explícitamente.
    def sPatientType = completeXml.PV1.'PV1.18'.text()
    if (sPatientType && (sPatientType == 'CMA' || sPatientType == 'QUI' || sPatientType == 'ADM') ) {
        sPrestacionHIS = sPatientType
    }
    else {
        def aPrestacionHIS = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.4' }*.text()
        if (aPrestacionHIS){
            sPrestacionHIS = aPrestacionHIS[0]
        }
    }
    */

    // Solo usamos PV1.4
    def aPrestacionHIS = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.4' }*.text()
    if (aPrestacionHIS){
        sPrestacionHIS = aPrestacionHIS[0]
    }

    return sPrestacionHIS
}


def getFechaInicioFin(completeXml){
    String timestamp1;
    String timestamp2;
    
    //Fecha inicio
    def aFechaInicioNode = completeXml.PV1.'PV1.44'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
    if (aFechaInicioNode){
        def sFechaInicio = aFechaInicioNode[0];
        timestamp1 = Date.parse('yyyyMMddHHmmSSSSSSS', sFechaInicio).format("yyyy-MM-dd'T'HH:mm:ss");
        
        //Por defecto fecha fin = fecha inicio
        timestamp2 = timestamp1;
    }
    
    //Fecha fin
    def aFechaFinNode = completeXml.PV1.'PV1.45'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
    if (aFechaFinNode[0]) {
        def sFechaFin = aFechaFinNode[0];
        timestamp2 = Date.parse('yyyyMMddHHmmSSSSSSS', sFechaFin).format("yyyy-MM-dd'T'HH:mm:ss");
    }
    
    def oReturn = {};
    oReturn.FechaHoraInicio = timestamp1;
    oReturn.FechaHoraFin = timestamp2;
    
    return oReturn;
}

//Episodio
def getEpisodio(completeXml){
    def sEpisodio = "";
    
    //def aEpisodio = completeXml.PV1.'PV1.50'.findAll{ node-> node.name() == 'CX.1' }*.text();
    def aEpisodio = completeXml.PV1.'PV1.50';
    if (aEpisodio){
        sEpisodio = aEpisodio[0];
    }
    
    return sEpisodio;
}

//Tipo Prestacion
def getTipoPrestacion(completeXml){
    def sTipoPrestacion = "";
    
    //def aTipoPrestacion = completeXml.PV1.findAll{ node-> node.name() == 'PV1.2' }*.text();
    def aTipoPrestacion = completeXml.PV1.'PV1.2';
    if (aTipoPrestacion){
        sTipoPrestacion = aTipoPrestacion[0];
    }
    
    return sTipoPrestacion;
}

//Servicio Realizados
def getServicioRealizador(completeXml){
    def sServicioRealizador = "";
    
    //def aServicioRealizador = completeXml.PV1.findAll{ node-> node.name() == 'PV1.10' }*.text();
    def aServicioRealizador = completeXml.PV1.'PV1.10';
    if (aServicioRealizador){
        sServicioRealizador = aServicioRealizador[0];
    }
    
    return sServicioRealizador;
}

//Centro
def getCenter(completeXml){
    def sCenter = "";
    
    //PV1/PV1.3/PL.4/HD.1
    //def aCenter = completeXml.PV1.'PV1.3'.'PL.4'.findAll{ node-> node.name() == 'HD.1' }*.text();
    def aCenter = completeXml.PV1.'PV1.3'.'PL.4'.'HD.1';
    if (aCenter){
        sCenter = aCenter[0];
    }

    return sCenter;    
}

//Medicos
def getMedicos(completeXml){
    def oReturn = {};
    def bEsConsultaEx = true;
    
    ////////////////////
    //Medico Realizador
    ////////////////////
    oReturn.MedicoRealizador = "";
    
    //comprobamos si es una consulta externa
    def sConsExt = completeXml.PV1.'PV1.18';
    if (sConsExt && sConsExt != 'CEX') {
        bEsConsultaEx = false;
    }
    
    def aMedicoRealizador;
    if (bEsConsultaEx) { //Consulta externa
        aMedicoRealizador = completeXml.PV1.'PV1.7'.'XCN.1';
    }
    else {  //Ingreso, Alta, etc...(resto)
        aMedicoRealizador = completeXml.PV1.'PV1.9'.'XCN.1';
    }
    
    if (aMedicoRealizador) {
         oReturn.MedicoRealizadorID  = aMedicoRealizador[0];
    }
    
    ////////////////////
    //Medico Solicitante
    ////////////////////
    oReturn.MedicoSolicitanteID = "";
    
    def aMedicoSolicitante;
    aMedicoSolicitante = completeXml.PV1.'PV1.17'.'XCN.1';
    if (aMedicoSolicitante) {
        oReturn.MedicoSolicitanteID = aMedicoSolicitante[0];
    }

    return oReturn;
}

//CMBDH
def getCMBDH(completeXml){
    def sCMBDH = "";
    
    ///PV1/PV1.7/XCN.1
    def aCMBDH = completeXml.OBX.'OBX.3'.findAll{ node-> node.'CE.1'.text() == 'ID_CMDBH' }*.'CE.2';
    if (aCMBDH){
        sCMBDH = aCMBDH[0];
    }
    
    return sCMBDH;
}

//Numero asistencia
def getNumeroAsistencia(completeXml) {
    def sNumeroAsistencia= "";
    
    def aNumeroAsistencia = completeXml.OBX.'OBX.3'.findAll{ node-> node.'CE.1'.text() == 'ID_AS' }*.'CE.2';
    if (aNumeroAsistencia){
        sNumeroAsistencia = aNumeroAsistencia[0];
    }
    
    return sNumeroAsistencia;
}

//Codigo Sanitario
def getCodigoSanitario(completeXml){
    def sCodigoSanitario= "";
    
    def aCodigoSanitario = completeXml.OBX.'OBX.3'.findAll{ node-> node.'CE.1'.text() == 'ID_FISSWEB' }*.'CE.2';
    if (aCodigoSanitario){
        sCodigoSanitario = aCodigoSanitario[0];
    }
    
    return sCodigoSanitario;
}

//Cama
def getCama(completeXml){
    def sCama = "";

    def aCama = completeXml.PV1.'PV1.3'.'PL.3';
    if (aCama){
        sCama = aCama[0];
    }

    return sCama;  
}

//Unidad Productiva
def getUnidadProductiva(completeXml){
	def sUP = "00745"; // temporal

    def aUPID = completeXml.PV1.'PV1.3'.'PL.10'.'EI.1';
    if (aUPID){
        sUP = aUPID[0];
    }
    
    return sUP;
}

//Situacion
def getSituacion(completeXml){
	return getTipoPrestacion(completeXml);
}


//Cantidad
def getCantidad(completeXml){
    def sCantidad = "1" // valor por defecto en cualquier caso

    try {
        // Buscamos nodo OBX donde OBX.3/CE.1 == 'CTD_LAT'
        def obxNode = completeXml.OBX.find { node ->
            node.'OBX.3'.'CE.1'.text() == 'CTD_LAT'
        }

        if (obxNode && obxNode.'OBX.5'.text()) {
            sCantidad = obxNode.'OBX.5'.text()

            // Normalizar separador decimal: si viene con coma, se convierte a punto
            sCantidad = sCantidad.replace(',', '.')
        }
    } catch(Exception e) {
        // Si hay cualquier problema, devolvemos "1"
        sCantidad = "1"
    }

    return sCantidad
}



//MESSAGE OUTPUT
def Message processData(Message message) {
   
    //Body 
    def xml = message.getBody(String.class)
    def completeXml= new XmlSlurper().parseText(xml);
    
    //ActivityExt
    message.setProperty("ActivityCodExt", getActivityID(completeXml));
    
    //PacienteExtID
    message.setProperty("PacienteExtID", getPaciente(completeXml));
    
    //Garante, Plan de cobertura, Clasificacion economica
    def oDeudor = getDeudor(completeXml);
    message.setProperty("DeudorID", oDeudor.DeudorID);
    message.setProperty("PlanCoberturaID", oDeudor.PlanCoberturaID);
    message.setProperty("ClasifEconomicaID", oDeudor.ClasifEconomicaID);
    message.setProperty("EsCatSalut",oDeudor.EsCatSalut);
    
    //Prestacion HIS - Esto se considera una etiqueta, no la prestación en sí mismo. Luego se mapeará a la prestación correcta.
    //message.setProperty("PrestacionHIS", getPrestacionHIS(completeXml));
    message.setProperty("PrestacionExtID", getPrestacionHIS(completeXml));
    
    
    //Fecha inicio/fin
    def oFechaInicioFin = getFechaInicioFin(completeXml);
    message.setProperty("FechaHoraInicio", oFechaInicioFin.FechaHoraInicio);
    message.setProperty("FechaHoraFin", oFechaInicioFin.FechaHoraFin);
    
    //Episodio
    def sEpisodioID = getEpisodio(completeXml);
    message.setProperty("EpisodioID", sEpisodioID);
    
    //Proceso = Episodio (?)
    message.setProperty("ProcesoID", sEpisodioID);
    
    //Tipo Prestacion
    message.setProperty("TipoPrestacionID", getTipoPrestacion(completeXml));
    
    //Situacion
     message.setProperty("SituacionID", getSituacion(completeXml));
    
    //Servicio Realizador
    message.setProperty("ServicioRealizadorID", getServicioRealizador(completeXml));
    
    //Centro
    message.setProperty("Center", getCenter(completeXml));
    
    //UPorigenID
    
    //Medicos
    def oMedicos = getMedicos(completeXml);
    message.setProperty("MedicoRealizadorID", oMedicos.MedicoRealizadorID);
     message.setProperty("MedicoSolicitanteID ", oMedicos.MedicoSolicitanteID );
    
    //CMBDH
    message.setProperty("CMBDH", getCMBDH(completeXml));
    
    //Numero asistencia
    message.setProperty("NumeroAsistencia", getNumeroAsistencia(completeXml));
    
    //Codigo Sanitario
    message.setProperty("CodigoSanitario", getCodigoSanitario(completeXml));
    
    //Cama
    message.setProperty("Cama", getCama(completeXml));
    
    //Unidad Productiva
     message.setProperty("UPrealID" , getUnidadProductiva(completeXml));
     
    //Cantidad
    message.setProperty("Cantidad", getCantidad(completeXml))

    
    return message;
}