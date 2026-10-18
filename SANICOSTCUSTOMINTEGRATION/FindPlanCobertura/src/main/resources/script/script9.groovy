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

import groovy.json.JsonSlurper;
import groovy.json.JsonBuilder;


def Message processData(Message message) {
    //Get Body 
    def body = message.getBody(String.class);

    //Define JSONSlurper
    def jsonSlurper = new JsonSlurper();
    def oJSONBody = jsonSlurper.parseText(body);
    def OJSONReturn;
    String error = "";
    
    if (oJSONBody.d.findPlanCobertura.status == 'fail'){
        //Format the JSON output
        //OJSONReturn = JsonOutput.toJson(oJSONBody.d.findPlanCobertura.data.body);
        
        //message.setBody(new JsonBuilder(OJSONReturn).toString());
        error = oJSONBody.d.findPlanCobertura.data.body.text;
        
        //Set the modified JSON to body and return
        throw new Exception(error);
    }else if (oJSONBody.d.findPlanCobertura.status == 'error'){
        //Format the JSON output
        OJSONReturn = (oJSONBody.d.findPlanCobertura.data);
    
        message.setBody(new JsonBuilder(OJSONReturn).toString());
        
        error = oJSONBody.d.findPlanCobertura.data;
        
        //Set the modified JSON to body and return
        throw new Exception(new JsonBuilder(OJSONReturn).toString());
        
        
    }else{    
     //OJSONReturn = JsonOutput.toJson(oJSONBody);
     //>>>PRG 31.07.2020  
     //message.setBody("<PlanCoberturaID>"+oJSONBody.data.body.PlanCoberturaID+"</PlanCoberturaID>");
     message.setBody("<root><PlanCoberturaID>"+oJSONBody.d.findPlanCobertura.data.body.PlanCoberturaID+"</PlanCoberturaID><ClasifEconomicaID>"+oJSONBody.d.findPlanCobertura.data.body.ClasifEconomicaID+"</ClasifEconomicaID></root>");
     //<<<PRG 31.07.2020  
   }
    
    return message;
}