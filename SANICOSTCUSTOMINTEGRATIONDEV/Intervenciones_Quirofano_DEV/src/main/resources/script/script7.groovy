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
    def mapHeaders = message.getHeaders();
    def mapProps = message.getProperties();
    def sMessageProcessingLogID= mapHeaders.get("SAP_MessageProcessingLogID");
    def sMessageContent= mapProps.get("DataValidationJSON");
    
	//Get Body 
    def body = message.getBody(String.class);
    def jsonSlurper = new JsonSlurper();
    def jsonSlurperContent = new JsonSlurper();
    
    def jsonDataObject = jsonSlurper.parseText(body);
    jsonDataObject.MessageProcessingLogID = sMessageProcessingLogID;
    
    def jsonMessageContentObject = jsonSlurperContent.parseText(sMessageContent);
    jsonDataObject.content = jsonMessageContentObject.Activities.ActivitiesType;

    body = new JsonBuilder(jsonDataObject).toString();
    
    message.setBody(body);
    
    //No funciona
    //throw new Exception(body);
    
    return message;
}