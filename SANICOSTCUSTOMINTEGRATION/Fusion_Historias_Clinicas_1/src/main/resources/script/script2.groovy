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
    
    //Recuperar las Properties      
       def map = message.getProperties();
       def NHCNew = map.get("NHCNew");
       def NHCOld = map.get("NHCOld");
       
    //Body 
       def xml = message.getBody(String.class)
       def completeXml= new XmlSlurper().parseText(xml)
       //DeudorIdNew & DeudorIdOld
       def NHC    = completeXml.'**'.findAll{ node-> node.name() == 'NHC' }*.text()
       def Deudor = completeXml.'**'.findAll{ node-> node.name() == 'DeudorExtID' }*.text()
       def DeudorID = completeXml.'**'.findAll{ node-> node.name() == 'DeudorID' }*.text()
       for (i = 0; i <NHC.size(); i++) {
          if( NHC[i] == NHCOld ) { 
           // Es el Deudor viejo
            message.setProperty("DeudorIdOld", Deudor[i])
            //Para setear el campo fused
            message.setProperty("DeudorOldIdInternal", DeudorID[i])
          }else if(NHC[i] == NHCNew) { 
            // Es el Deudor nuevo
            message.setProperty("DeudorIdNew", Deudor[i])
          }
       }
       return message
}