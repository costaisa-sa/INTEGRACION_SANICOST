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
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class)
       def completeXml= new XmlSlurper().parseText(xml)
       //Generamos el ID Actividad para relacionarlo con el Ingreso 
       def PV18 =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.18' }*.text();
       def PV50 =  completeXml.PV1.'PV1.50'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       if(PV18 && PV50){
           def ActivityUniqueID = PV18[0]+PV50[0];
            message.setProperty("ActivityUniqueID", ActivityUniqueID);
       }
        
        def bSkipCalculationDays;
        
       //Calculamos la EtiquetaHIS de la siguiente forma PV1.2 + PV1.3.3 (Ámbito + cama_alta) (Solo en ingreso hospitalario, en urgencias solo se quedará el PV1.2)
        //def Ambito      = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
        //def AmbitoValor = Ambito[0];
        def CamaAlta    = completeXml.PV1.'PV1.3'.'**'.findAll{ node-> node.name() == 'PL.3' }*.text();
        def CamaAltaValor = CamaAlta[0];
       //si existen ambos valores construimos la etiqueta
       if(PV18[0] == 'URG'){ //Urgencia
            message.setProperty("PrestacionHIS", PV18[0]); 
            //>>>PRG 06.04.2020
            bSkipCalculationDays='X';
            //<<<PRG 06.04.2020
       }else{
         if(PV18 && CamaAltaValor){
            def EtiquetaHIS = PV18[0]+CamaAltaValor;
            message.setProperty("PrestacionHIS", EtiquetaHIS); 
         }
         //>>>PRG 06.04.2020
         bSkipCalculationDays='';
         //<<<PRG 06.04.2020
       }
       
       //NHC
        def NHC = completeXml.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
        if(NHC){
          def valor = NHC[0];
          message.setProperty("NHC", valor);
        }
        
       //Garante
        def Garante = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text();
        if(Garante){
         def valor2 = Garante[0];
         message.setProperty("Garante", valor2);
        }
        
        //Cama
        def Cama =  completeXml.PV1.'PV1.3'.'**'.findAll{ node-> node.name() == 'PL.3' }*.text();
        if(Cama && PV18[0] != 'URG'){ //No grabaremos la cama cuando sea Urgencia
          message.setProperty("Cama", Cama[0]);
        }
        //ServicioRealizadorID
        def ServicioRealizadorID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.10' }*.text();
        if(ServicioRealizadorID){
          message.setProperty("ServicioRealizadorID", ServicioRealizadorID[0]);
        }
        
        //>>>PRG 16.04.2020
        message.setProperty("MedicoRealizadorID", '');
        message.setProperty("MedicoAutofacturaID", '');
        message.setProperty("MedicoResponsableID", '');
        //<<<PRG 16.04.2020
        
        //MedicoRealizadorID
        def MedicoRealizadorID =  completeXml.PV1.'PV1.17'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text();
        if(MedicoRealizadorID){
           message.setProperty("MedicoRealizadorID", MedicoRealizadorID[0]); 
        //MedicoAutofacturaID (idem MedicoRealizadorID)
           message.setProperty("MedicoAutofacturaID", MedicoRealizadorID[0]);
        //MedicoResponsableID (idem MedicoRealizadorID)
           message.setProperty("MedicoResponsableID", MedicoRealizadorID[0]);
        }
        
        //MotivoAltaID
        def MotivoAltaID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.36' }*.text();
        if(MotivoAltaID){
           message.setProperty("MotivoAltaID", MotivoAltaID[0]); 
        }
       
        //TipoPrestacionID
         def TipoPrestacionID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
        if(TipoPrestacionID){
           message.setProperty("TipoPrestacionID", TipoPrestacionID[0]); 
        }
        
        //FechaHoraInicio
        def FechaHoraInicio =  completeXml.PV1.'PV1.44'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
        def fecha = FechaHoraInicio[0];
        if(fecha){
          String timestamp = Date.parse('yyyyMMddHHmmss', fecha).format("yyyy-MM-dd'T'HH:mm:ss");
           message.setProperty("FechaHoraInicio", timestamp);
        }
        //FechaHoraFin
        def FechaHoraFin =  completeXml.PV1.'PV1.45'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
        def fecha2 = FechaHoraFin[0];
        if(fecha2){
          String timestamp = Date.parse('yyyyMMddHHmmss', fecha2).format("yyyy-MM-dd'T'HH:mm:ss")
           message.setProperty("FechaHoraFin", timestamp) 
        }
        //>>>PRG 16.04.2020
        if (fecha && fecha2 && bSkipCalculationDays == '') {
            message.setProperty("SkipCalculationDays", bSkipCalculationDays);
        }
        else {
            message.setProperty("SkipCalculationDays", 'X');
        }
        //<<<PRG 16.04.2020
        
         //>>>PRG 14.05.2020 INT-8
        def CodigoSanitario = '';
        def ZPV26 =  completeXml.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.26' }*.text();
        if(ZPV26){
            CodigoSanitario = ZPV26[0];
        }
        message.setProperty("CodigoSanitario", CodigoSanitario);
        //<<<PRG 14.05.2020 INT-8
        return message
}