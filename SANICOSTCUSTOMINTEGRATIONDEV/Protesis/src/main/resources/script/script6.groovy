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
// get an exception java class instance
    def mapProps = message.getProperties();
    def ex = mapProps.get("CamelExceptionCaught");
    
    def sFullExceptionMessage = "";
    try {
        sFullExceptionMessage = ex.getResponseBody() as String;
    }
    catch(e){
        sFullExceptionMessage = ex.getMessage();
    }
    
    def iStartIndexExceptionBody = sFullExceptionMessage.lastIndexOf(":")+1;
    def iEndIndexExceptionBody = sFullExceptionMessage.indexOf("@");
    if (iEndIndexExceptionBody==-1) {
        iEndIndexExceptionBody = sFullExceptionMessage.size()-1;
    }
    
    sFullExceptionMessage = sFullExceptionMessage.substring(iStartIndexExceptionBody,iEndIndexExceptionBody);
    
    def sJSONExceptionData;
    def iStartMessageIndex = sFullExceptionMessage.indexOf("{");
    def iEndMessageIndex = sFullExceptionMessage.lastIndexOf("}");
    
    if (iStartMessageIndex>-1 && iEndMessageIndex>-1) {
        sJSONExceptionData = sFullExceptionMessage.substring(iStartMessageIndex,iEndMessageIndex+1);
    }
    else {
        sJSONExceptionData = '{"type": "","field": "","valor": "","text": "'+sFullExceptionMessage+'"}"';
    }

    message.setBody(sJSONExceptionData);
    
    return message;
}