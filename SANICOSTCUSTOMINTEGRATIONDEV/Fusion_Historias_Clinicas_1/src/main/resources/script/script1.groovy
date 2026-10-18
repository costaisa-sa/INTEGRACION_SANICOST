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
       //NHCNew
       def NHCNew = completeXml.'ADT_A39.PATIENT'.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text()
       if(NHCNew){
         def valor= NHCNew[0];
         message.setProperty("NHCNew", valor)
       }
      //NHCOld
       def NHCOld = completeXml.'ADT_A39.PATIENT'.MRG.'MRG.4'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text()
       if(NHCOld){
         def valor2 = NHCOld[0];
         message.setProperty("NHCOld", valor2)
       }
       
       return message
}