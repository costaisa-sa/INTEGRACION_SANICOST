import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*
import java.text.SimpleDateFormat

def Message processData(Message message) {

    // Body
    def xmlString = message.getBody(String.class)
    def xml = new XmlSlurper().parseText(xmlString)

    // -------------------------------
    // Asignar propiedades al mensaje
    // -------------------------------
    message.setProperty("ActivityCodExt", getActivityID(xml))
    message.setProperty("PacienteExtID", getPaciente(xml))
    
    def oDeudor = getDeudor(xml)
    message.setProperty("DeudorExtID", oDeudor.DeudorExtID)
    message.setProperty("DeudorTypeID", oDeudor.DeudorTypeID)
    message.setProperty("PlanCoberturaID", oDeudor.PlanCoberturaID)

    message.setProperty("PrestacionExtID", getPrestacionHIS(xml))
    
    def oFecha = getFechaInicioFin(xml)
    message.setProperty("FechaHoraInicio", oFecha.FechaHoraInicio)
    message.setProperty("FechaHoraFin", oFecha.FechaHoraFin)
    
    def sEpisodioID = getEpisodio(xml)
    message.setProperty("EpisodioID", sEpisodioID)
    message.setProperty("ProcesoID", sEpisodioID)
    
    message.setProperty("TipoPrestacionID", getTipoPrestacion(xml))
    message.setProperty("SituacionID", getSituacion(xml))
    message.setProperty("Destino", getDestino(xml))
    message.setProperty("Procedencia", getProcedencia(xml))

    message.setProperty("ServicioRealizadorID", getServicioRealizador(xml))
    message.setProperty("Center", getCenter(xml))
    message.setProperty("Edificio", getEdificio(xml))
    
    def oMedicos = getMedicos(xml)
    message.setProperty("MedicoRealizadorID", oMedicos.MedicoRealizadorID)
    message.setProperty("MedicoSolicitanteID", oMedicos.MedicoSolicitanteID)
    
    message.setProperty("CMBDH", getCMBDH(xml))
    message.setProperty("NumeroAsistencia", getNumeroAsistencia(xml))
    message.setProperty("CodigoSanitario", getCodigoSanitario(xml))
    message.setProperty("Cama", getCama(xml))
    message.setProperty("UPrealID", getUnidadProductiva(xml))
    message.setProperty("Cantidad", getCantidad(xml))
    // Ceco (irá mapeado a GFH)
    message.setProperty("Ceco", getCeco(xml))

    // -------------------------------
    // Autorizacion y FechaAutorizacion
    // -------------------------------
    def oAuth = getAutorizacion(xml)
    message.setProperty("Autorizacion", oAuth.Autorizacion)
    message.setProperty("FechaAutorizacion", oAuth.FechaAutorizacion)
    
    return message
}

// -------------------------------
// Funciones auxiliares
// -------------------------------

def getActivityID(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.52'?.'XCN.1'?.text() ?: ''
}

def getPaciente(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PID' }?.'PID.2'?.'CX.1'?.text() ?: ''
}

def getDeudor(xmlNode) {
    def gt1 = xmlNode.'**'.find { it.name() == 'GT1' }
    def in1 = xmlNode.'**'.find { it.name() == 'IN1' }
    
    // 1. Determinar el Tipo de Deudor: Si existe GT1 es Privado ('P'), si no, es Entidad ('E')
    def deudorTypeID = gt1 ? 'P' : 'E'
    
    // 2. Obtener DeudorExtID: 
    // Si es GT1 y la relación no es 'SEL', buscamos en GT1.2. 
    // Si no hay GT1, buscamos en IN1.3. Si no, por defecto el paciente.
    def garanteRel = gt1?.'GT1.11'?.text()
    def deudorExtID = ""
    
    if (gt1 && garanteRel != 'SEL') {
        deudorExtID = gt1.'GT1.2'.'**'.find { it.name() == 'CX.1' }?.text() ?: getPaciente(xmlNode)
    } else if (in1) {
        deudorExtID = in1.'IN1.3'.'**'.find { it.name() == 'CX.1' }?.text() ?: getPaciente(xmlNode)
    } else {
        deudorExtID = getPaciente(xmlNode)
    }

    // 3. Obtener Plan de Cobertura (solo desde IN1)
    def planCoberturaID = in1.'IN1.2'.'**'.find { it.name() == 'CWE.1' }?.text() ?: ""
    
    // 4. Lógica CatSalut / Clasificación
    def isSCS = (planCoberturaID == 'SCS')
    
    return [
        DeudorExtID       : deudorExtID,
        DeudorTypeID      : deudorTypeID,
        PlanCoberturaID   : planCoberturaID
    ]
}

def getPrestacionHIS(xmlNode) {
    def obx = xmlNode.'**'.find { it.name() == 'OBX' }
    obx?.'OBX.3'?.'CE.4'?.text() ?: ''
}

def getFechaInicioFin(xmlNode) {
    def pv1 = xmlNode.'**'.find { it.name() == 'PV1' }
    def inicioRaw = pv1.'PV1.44'?.'TS.1'?.text() ?: ''
    def finRaw = pv1.'PV1.45'?.'TS.1'?.text() ?: inicioRaw
    
    def outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    def inputFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    
    def inicio = ''
    def fin = ''
    
    try { inicio = inicioRaw ? outputFormat.format(inputFormat.parse(inicioRaw)) : '' } catch(e) { inicio = '' }
    try { fin = finRaw ? outputFormat.format(inputFormat.parse(finRaw)) : '' } catch(e) { fin = '' }
    
    [FechaHoraInicio: inicio, FechaHoraFin: fin]
}

def getEpisodio(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.50'?.'CX.1'?.text() ?: ''
}

def getTipoRepresentacion(xmlNode) { 
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.2'?.text() ?: ''
}

def getTipoPrestacion(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.2'?.text() ?: ''
}

//Situacion
def getSituacion(xmlNode){
	xmlNode.'**'.find { it.name() == 'PV2' }?.'PV2.8'?.text() ?: ''
}

//Destino
def getDestino(xmlNode){
	xmlNode.'**'.find { it.name() == 'PV2' }?.'PV2.27'?.text() ?: ''
}

//Procedencia
def getProcedencia(xmlNode){
	xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.14'?.text() ?: ''
}

def getServicioRealizador(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.10'?.text() ?: ''
}

def getCenter(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.3'?.'PL.4'?.'HD.1'?.text() ?: ''
}

def getEdificio(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.3'?.'PL.7'?.text() ?: ''
}

def getMedicos(xmlNode) {
    def pv1 = xmlNode.'**'.find { it.name() == 'PV1' }
    def medicoReal = pv1.'PV1.7'?.'XCN.1'?.text() ?: ''
    def medicoSolic = pv1.'PV1.17'?.'XCN.1'?.text() ?: ''
    [MedicoRealizadorID: medicoReal, MedicoSolicitanteID: medicoSolic]
}

/*def getCMBDH(xmlNode) {
    /*def obx = xmlNode.'**'.findAll { it.name() == 'OBX' }.find { it.'OBX.3'.'CE.1'.text() == 'ID_CMDBH' }
    obx?.'OBX.3'?.'CE.2'?.text() ?: ''*/
    /*def sCMBDH = "";

    // Buscamos nodo OBX donde OBX.3/CE.1 == 'CTD_LAT'
    def obxNode = completeXml.OBX.find { node ->
        node.'OBX.3'.'CE.1'.text() == 'ID_CMDBH'
    }

    if (obxNode && obxNode.'OBX.5'.text()) {
        sCMBDH = obxNode.'OBX.5'.text()
    }
    
    return sCMBDH;
}*/

def getCMBDH(xmlNode) {
    def sCMBDH = "";

    // Usamos xmlNode y buscamos todos los OBX recursivamente ('**') como en el resto del script
    def obxNode = xmlNode.'**'.findAll { it.name() == 'OBX' }.find { node ->
        node.'OBX.3'.'CE.1'.text() == 'ID_CMDBH'
    }

    if (obxNode && obxNode.'OBX.5'.text()) {
        sCMBDH = obxNode.'OBX.5'.text()
    }
    
    return sCMBDH;
}


/*def getNumeroAsistencia(xmlNode) {
    /*def obx = xmlNode.'**'.find { it.name() == 'OBX' }
    obx?.'OBX.3'?.'CE.1'?.text() ?: ''
    */
    /*def sNumeroAsistencia= "";

    // Buscamos nodo OBX donde OBX.3/CE.1 == 'ID_AS'
    def obxNode = completeXml.OBX.find { node ->
        node.'OBX.3'.'CE.1'.text() == 'ID_AS'
    }

    if (obxNode && obxNode.'OBX.5'.text()) {
        sNumeroAsistencia = obxNode.'OBX.5'.text()
    }
    
    return sNumeroAsistencia;
}*/

def getNumeroAsistencia(xmlNode) {
    def sNumeroAsistencia = "";

    // Corrección: Usar xmlNode y búsqueda recursiva
    def obxNode = xmlNode.'**'.findAll { it.name() == 'OBX' }.find { node ->
        node.'OBX.3'.'CE.1'.text() == 'ID_AS'
    }

    if (obxNode && obxNode.'OBX.5'.text()) {
        sNumeroAsistencia = obxNode.'OBX.5'.text()
    }
    
    return sNumeroAsistencia;
}

def getCodigoSanitario(xmlNode) {
    def obx = xmlNode.'**'.findAll { it.name() == 'OBX' }.find { it.'OBX.3'.'CE.1'.text() == 'ID_FISSWEB' }
    obx?.'OBX.3'?.'CE.2'?.text() ?: ''
}

def getCama(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.3'?.'PL.3'?.text() ?: ''
}

def getUnidadProductiva(xmlNode) {
    xmlNode.'**'.find { it.name() == 'PV1' }?.'PV1.3'?.'PL.10'?.'EI.1'?.text() ?: '00745'
}

def getCantidad(xmlNode) {
    def obx = xmlNode.'**'.findAll { it.name() == 'OBX' }.find { it.'OBX.3'.'CE.1'.text() == 'CTD_LAT' }
    obx?.'OBX.3'?.'CE.2'?.text() ?: '1'
}
def getCeco(xmlNode) {
    def sCeco = "";
    def obxNode = xmlNode.'**'.findAll { it.name() == 'OBX' }.find { node ->
        node.'OBX.3'.'CE.1'.text() == 'CECO'
    }
    if (obxNode && obxNode.'OBX.5'.text()) {
        sCeco = obxNode.'OBX.5'.text()
    }
    
    return sCeco;
}

// -------------------------------
// Autorizacion y Fecha
// -------------------------------
def getAutorizacion(xmlNode) {
    def in1 = xmlNode.'**'.find { it.name() == 'IN1' }
    
    def authID = in1?.'IN1.14'?.'AUI.1'?.text() ?: ''
    def fechaRaw = in1?.'IN1.14'?.'AUI.2'?.text() ?: ''
    
    def fechaFormateada = ''
    if (fechaRaw) {
        try {
            def inputFormat = new SimpleDateFormat("yyyyMMddHHmm")
            def outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            fechaFormateada = outputFormat.format(inputFormat.parse(fechaRaw))
        } catch(e) { 
            fechaFormateada = ''
        }
    }
    
    return [Autorizacion: authID, FechaAutorizacion: fechaFormateada]
}