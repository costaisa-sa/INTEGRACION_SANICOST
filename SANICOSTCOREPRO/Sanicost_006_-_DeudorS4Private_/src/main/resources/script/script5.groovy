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
import groovy.json.*;
import org.json.simple.*
def Message processData(Message message) {
    
    def map      = message.getProperties();
    def sPacienteExtID = map.get("PacienteExtID");
    
    // get an exception java class instance
    def ex = map.get("CamelExceptionCaught");
    def sFullExceptionMessageCatch = "";
    try {
        sFullExceptionMessageCatch = ex.getResponseBody() as String;
    }
    catch(e){
        sFullExceptionMessageCatch = ex.getMessage();
    }
    
    def sFullExceptionMessage= "Paciente "+sPacienteExtID+"."+sFullExceptionMessageCatch;
    def sJSONExceptionData = '{"type": "","field": "PacienteExtID","valor": "","text": "'+sFullExceptionMessage+'"}"';

    message.setBody(sJSONExceptionData);

    return message;
}