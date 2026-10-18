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
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    String sHTTPQuery = message.getHeaders()["CamelHttpQuery"];
    //Han de venir 2 parámetros: SimulaciónID y MessageID
    int iIndexSeparator = sHTTPQuery.indexOf("&");
    
    if (iIndexSeparator==-1) iIndexSeparator = sHTTPQuery.length();
    String sFullParam1 = sHTTPQuery.substring(0,iIndexSeparator);
    String[] aParam1Data = parseStringParams(sFullParam1);
    message.setProperty(aParam1Data[0],aParam1Data[1]);
    
    String sFullParam2 = sHTTPQuery.substring(iIndexSeparator+1,sHTTPQuery.length());
    String[] aParam2Data = parseStringParams(sFullParam2);
    message.setProperty(aParam2Data[0],aParam2Data[1]);

    return message;
}

def parseStringParams(String sParamString){
    def aInfo = new String[2];
    
    int iIndexSeparator = sParamString.indexOf("=");
    aInfo[0] = sParamString.substring(0,iIndexSeparator);
    aInfo[1] = sParamString.substring(iIndexSeparator+1,sParamString.length());
    
    return aInfo;
    
}