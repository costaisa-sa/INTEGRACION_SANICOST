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
import groovy.util.*;
import java.io.ByteArrayInputStream
def Message processData(Message message) {
    //Body 
    def body = message.getBody();
    def stock_inicial = Integer.parseInt(""+message.getProperties().get("Balance_inicial"));
    def Fecha_clave_datetime = message.getProperties().get("Fecha_clave");
    def Fecha_clave_value = Fecha_clave_datetime.substring(9,Fecha_clave_datetime.size()-1);
      
      
    def completeXml = new XmlSlurper().parseText(body.toString().trim());
    //def completeXml = new XmlParser().parse(new ByteArrayInputStream(body.toString().getBytes("utf-8")));
    //def completeXml = new XmlParser().parseText(body.toString().trim().replaceFirst("^([\\W]+)<","<"));
    /*def stock_inicial_node = new NodeBuilder().EntradaSalidaType() {
        ID('1')
        DocumentDate(Fecha_clave_value )
        GoodsReceiptQtyInBaseUnit(0)
        GoodsIssueQtyInBaseUnit(0)
        Balance(stock_inicial)
    }
    def EntradaSalidas = completeXml.children();
    EntradaSalidas.add(0,stock_inicial_node );
    */
    def EntradaSalidas = completeXml;
    EntradaSalidas.appendNode({
        EntradaSalidaType{
            ID('1')
            DocumentDate(Fecha_clave_value )
            GoodsReceiptQtyInBaseUnit(0)
            GoodsIssueQtyInBaseUnit(0)
            Balance(stock_inicial)
        }
    } );
    
    
    def Balance_anterior = stock_inicial;
    EntradaSalidas.eachWithIndex{ EntradaSalidaType, index -> 
        
        if (index > 0) {
            entrada = EntradaSalidaType.findAll{it->it.name() == 'GoodsReceiptQtyInBaseUnit'};
            salida = EntradaSalidaType.findAll{it->it.name() == 'GoodsIssueQtyInBaseUnit'};
            balance = EntradaSalidaType.findAll{it->it.name() == 'Balance'};
        
            balance[0].value = Balance_anterior + Integer.parseInt(""+entrada[0].text()) - Integer.parseInt(""+salida[0].text());
    
            Balance_anterior = balance[0].value;
        }
        
    };
      
    return message;
}