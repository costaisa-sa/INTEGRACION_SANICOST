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
       //NHC
       def NHC = completeXml.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       if(NHC){
         message.setProperty("NHC", NHC[0]);
       }
       //TipoPrestacionID
       def TipoPrestacionID = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
       if(TipoPrestacionID){
         message.setProperty("TipoPrestacionID", TipoPrestacionID[0]); 
       }
     //FechaHoraInicio
        def FechaHoraInicio =  completeXml.EVN.'**'.findAll{ node-> node.name() == 'EVN.2' }*.text(); 
        def fecha = FechaHoraInicio[0];
        if(fecha){
          String timestamp = Date.parse('yyyyMMddHHmmss', fecha).format("yyyy-MM-dd'T'HH:mm:ss");
           message.setProperty("FechaHoraInicio", timestamp);
        }
        //ServicioRealizadorID
        def ServicioRealizadorID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.10' }*.text();
        if(ServicioRealizadorID){
           message.setProperty("ServicioRealizadorID", ServicioRealizadorID[0]);
        }
       //MedicoRealizadorID
        def MedicoRealizadorID =  completeXml.PV1.'PV1.17'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text();
        if(MedicoRealizadorID){
            message.setProperty("MedicoRealizadorID", MedicoRealizadorID[0]);
            //MedicoAutofacturaID (idem MedicoRealizadorID)
            message.setProperty("MedicoAutofacturaID", MedicoRealizadorID[0]);
            //MedicoResponsableID (idem MedicoRealizadorID)
            message.setProperty("MedicoResponsableID", MedicoRealizadorID[0]);
        }
        /*
       //ActivityIDExt
        def ActivityIDExt = completeXml.PV1.'PV1.52'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text();
        if(ActivityIDExt){
           message.setProperty("ActivityID", ActivityIDExt[0]);
        }
        */
       //Generamos el ID Actividad para relacionarlo con el Ingreso 
       def PV18 =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.18' }*.text();
       def PV50 =  completeXml.PV1.'PV1.50'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       if(PV18 && PV50){
           def ActivityUniqueID = PV18[0]+PV50[0];
            message.setProperty("ActivityID", ActivityUniqueID);
       }
    
       return message;
}