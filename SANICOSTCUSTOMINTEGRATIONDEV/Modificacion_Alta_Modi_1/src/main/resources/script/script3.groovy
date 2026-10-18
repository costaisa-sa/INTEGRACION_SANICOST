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
       //Generamos el ID Actividad 
       def PV18 =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.18' }*.text();
        if(PV18){
          message.setProperty("Type", PV18[0]); 
        }
       def PV50 =  completeXml.PV1.'PV1.50'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       if(PV18 && PV50){
           def ActivityUniqueID = PV18[0]+PV50[0];
            message.setProperty("ActivityUniqueID", ActivityUniqueID);
       }
        return message
}