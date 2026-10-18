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
    // get a map of iflow properties
    def map = message.getProperties();
    def sCLSNumeroLotEnviamentFitxer = map.get("CLSNumeroLotEnviamentFitxer");
    def sCodiUnitatProveidora = map.get("CodiUnitatProveidora");
    def sTipusFitxer = map.get("TipusFitxer");
    
    sCLSNumeroLotEnviamentFitxer = zeroLeapLeft("00000",sCLSNumeroLotEnviamentFitxer );
    sCodiUnitatProveidora = zeroLeapLeft("00000",sCodiUnitatProveidora );
    
    //>>>PRG 23.07.2020
    def sType = '1';
    def sAction = map.get("Action");
    if (sAction == 'Reasignar') {sType='5';}
    else if (sAction == 'Abonar') {sType='4';}
    //<<<PRG 23.07.2020
    message.setProperty("filename","FSE"+sCodiUnitatProveidora+sType+sCLSNumeroLotEnviamentFitxer+sTipusFitxer);
    
    return message;
}

//Pone ceros a la izquierda
def zeroLeapLeft(sZeroString,sValue){
    return sZeroString.substring(0,sZeroString.size()-sValue.size())+sValue;
}
