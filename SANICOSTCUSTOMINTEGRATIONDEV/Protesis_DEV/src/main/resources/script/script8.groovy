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
def Message processData(Message message) {
    def mapProps = message.getProperties();
    def sMessageContent= mapProps.get("DataValidationJSON");
    
    // get an exception java class instance
    def ex = mapProps.get("CamelExceptionCaught");
    
    def sFullExceptionMessage = "";
    try {
        sFullExceptionMessage = ex.getResponseBody() as String;
    }
    catch(e){
        sFullExceptionMessage = ex.getMessage();
    }
    
    def sJSONExceptionData;
    def iStartMessageIndex = sFullExceptionMessage.indexOf("{");
    def iEndMessageIndex = sFullExceptionMessage.lastIndexOf("}");
    sFullExceptionMessage = sFullExceptionMessage.substring(iStartMessageIndex,iEndMessageIndex+1);
    
    def jsonSlurper = new JsonSlurper();
    def jsonDataObject = jsonSlurper.parseText(sFullExceptionMessage);
    
    def jsonSlurperContent = new JsonSlurper();
    def jsonMessageContentObject = jsonSlurperContent.parseText(sMessageContent);
    jsonDataObject.content = jsonMessageContentObject.Activities.ActivitiesType;

    body = new JsonBuilder(jsonDataObject).toString();
    
    message.setBody(body);
    
    //Set the modified JSON to body and return
    //throw new Exception(body);
    
    return message;
}