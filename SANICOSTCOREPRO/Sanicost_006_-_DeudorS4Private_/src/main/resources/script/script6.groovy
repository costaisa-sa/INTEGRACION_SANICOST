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
import groovy.json.*
def Message processData(Message message) {
    /*def body      = message.getBody(String.class);
   
    throw new Exception(body); 
       
    return message;*/
    
    
    def mapHeaders = message.getHeaders();
    def sMessageProcessingLogID= mapHeaders.get("SAP_MessageProcessingLogID");

    //Get Body 
    def body = message.getBody(String.class);

    //Define JSONSlurper
    def jsonSlurper = new JsonSlurper()
    def oJSONBody = jsonSlurper.parseText(body);
    oJSONBody.MessageProcessingLogID = sMessageProcessingLogID;
    
    //Format the JSON output
    def OJSONReturn = JsonOutput.toJson(oJSONBody);

    //Set the modified JSON to body and return
    throw new Exception(new JsonBuilder(OJSONReturn).toString());
    
    
    return message;
    
}