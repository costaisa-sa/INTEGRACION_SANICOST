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
import groovy.xml.MarkupBuilder;

def Message processData(Message message) {
    
    def inputStream = message.getBody(InputStream.class);
    //El fichero viene en UTF-8, ya que llega dentro de un JSON. 
    //La conversión se debe hacer en el cliente, antes de incluirlo en el JSON.
    def multiline = inputStream.getText("utf-8");
    
    def writer = new StringWriter();
    def xml = new MarkupBuilder(writer);

    xml.NomenclatorList() {
        multiline.eachLine {line, count-> 
            if (count>0) {
                buildXMLRecord(xml,line, message);
            }
        }
    }
    
    message.setHeader("Content-Type", "application/xml; charset=utf-8");
    message.setBody(writer.toString());
    
    return message;
}

def buildXMLRecord(xml,line, message){
    if (line.length() > 7 && line.substring(0,7) == "1000001") {
            message.setProperty("VersionID",line.substring(24,30));
    }
    
    if (line.length() > 7 && line.substring(0,7) == "2000001") {
        xml.record(){
            CodigoNacional(line.substring(15,21).replaceFirst('^0+(?!$)', ''));
            DescCodigoNacional(line.substring(21,121));
            FechaInicioVigencia(formatDateVigencia(line.substring(132,142)));
            FechaFinVigencia(formatDateVigencia(line.substring(142,152)));
            Familia(line.substring(152,157)); 
            Importe(formatImporte(line.substring(157,167)));
            
            //DescCodigoNacional(line.substring(132,142));
            //DescCodigoNacional('CECENUñàèíòú40 mg 20 cápsules                                            abcdefghijklmnñopqrstuvwxyz'.substring(0,100));
            //FechaInicioVigencia(formatDateVigencia('23/11/2021'));
            //FechaFinVigencia(formatDateVigencia('          '));
            //Familia('61400');
            //Importe(formatImporte('0000000050'));
        }
        
    }
    
}

def formatImporte(sImporteIn){
    def sImporteOut = new String("0.00");
    
     if (sImporteIn && sImporteIn.substring(0,9) != "         ") {
        sImporteOut = sImporteIn.substring(0,7)+'.'+sImporteIn.substring(7,9);
    }
    
    return sImporteOut.toString();
}

def formatDateVigencia(sFechaIn){
    def sFechaOut = new String("99991231");
    
    if (sFechaIn && sFechaIn.substring(0,10) != "          ") {
        sFechaOut = sFechaIn.substring(6,10)+sFechaIn.substring(3,5)+ sFechaIn.substring(0,2);
    }
    
    return sFechaOut.toString();

}