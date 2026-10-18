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
//import java.util.HashMap;
def Message processData(Message message) {
    String sHTTPQuery = message.getHeaders()["CamelHttpQuery"];
    String sVersionID = null;
    if (sHTTPQuery != null && !sHTTPQuery.trim().isEmpty()) {
        sVersionID = sHTTPQuery.substring(sHTTPQuery.indexOf("=")+1);
    } else {
        sVersionID = '';
    }
    message.setProperty("VersionID",sVersionID);
    
    //byte[] bodyBytes = message.getBody(byte[].class);
    //String content = new String(bodyBytes, "Windows-1252");
    //message.setBody(content.getBytes("UTF-8"));
    
    return message;
}