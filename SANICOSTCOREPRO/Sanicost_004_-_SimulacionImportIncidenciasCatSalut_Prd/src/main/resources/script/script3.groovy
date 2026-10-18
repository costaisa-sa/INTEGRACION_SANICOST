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
import groovy.xml.MarkupBuilder;

def Message processData(Message message) {
    //Body 
    def multiline = message.getBody(java.lang.String) as String;  
    
    def writer = new StringWriter();
    def xml = new MarkupBuilder(writer); 
    
    xml.ActivitiesCatSalut() {
        multiline.eachLine {line, count-> 
            buildXMLDetails(xml,line);
        }
    }
    message.setBody(writer.toString());
    return message;
}


def buildXMLDetails(xml,line){
    xml.ActividadDetails(){

        NumeroFactura(line.substring(1,11));
        NumeroLiniaFactura(line.substring(11,17))
        //>>>PRG 21.08.2021 Cambio tecnico de fichero de incidencias por parte de Catsalut
        //Errors(line.substring(371,377).replace(" ",""));
        Errors(line.substring(376,383).replace(" ",""));
        //<<<PRG 21.08.2021
   
    }
}
