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
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);

       //Fecha/Hora programación                          
       def FechaHoraInicio =  completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.23' }*.text();
       def valor3 = FechaHoraInicio[0];
       if(valor3){
           String timestamp = Date.parse('yyyyMMddHHmmss', valor3).format("yyyy-MM-dd'T'HH:mm:ss");
           message.setProperty("FechaHoraInicio", timestamp);
       }
       
       //EtiquetaHis
        def EtiquetaHIS = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.ORDER_OBSERVATION'.'ORU_R01.OBSERVATION'.OBX.'OBX.3'.'**'.findAll{ node-> node.name() == 'CE.4' }*.text();
        def valor4 = EtiquetaHIS[0];
         if(valor4){
           message.setProperty("PrestacionHIS", valor4); 
         }else{message.setProperty("PrestacionHIS", ''); }
       
//Codigo Actividad       
        def Actividad = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.PV1.'PV1.52'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text();
        def valor5 = Actividad[0];
         if(valor5){
           message.setProperty("ActivityCodExt", valor5); 
         }else{message.setProperty("ActivityCodExt", ''); }
         
         
       return message
}