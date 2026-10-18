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
import org.apache.olingo.odata2.api.uri.UriInfo;
import com.sap.gateway.ip.core.customdev.logging.*; 
import java.text.*;

//import com.sap.gateway.ip.core.customdev.logging.LogMessage;
def Message processData(Message message) {
	def uriInfo = message.getHeaders().get("UriInfo");
	/*
	The following script copies the content of UriInfo into the respective fields for OData reciever.
	Currently supports only top and skip. 
	Reference for UriInfo:com.sap.gateway.core.ip.provider.data.UriInfoImpl.java
	In OData Reciever use {header.odataEntity}?{header.odataURI}
	*/
	def odataURI = new StringBuilder();
	def urlDelimiter = "&";
	def urlConcat = "?";
	def entityName = uriInfo.getTargetEntitySet().getName();

	if (uriInfo.getFilter()!= null){
		def filter = uriInfo.getFilter().getExpressionString();
		if(odataURI.size()!=0)
			odataURI.append(urlDelimiter);
		odataURI.append("\$filter=").append(filter);
		
		def Fecha_clave = '';
		def Fecha_inicial = ''; //es la fecha de la primera linea del listado. Ha de ser el 'ge' o el 'gt' de DocumentDate
		def Filter_material = '';
		def Filter_product = '';
		def Filter_nobrackets = filter.replace(")","").replace("(","");
		def result = (Filter_nobrackets =~ /(?<Filter>(?<Resource>[^ ]+?)\s(?<Operator>eq|ne|gt|ge|lt|le|add|sub|mul|div|mod|)\s(datetime)?(?<Value>(datetime)?'.+?'))\s*(?:[^']*$|\s+(?:or|and|not))/).findAll();
		for (item in result){
            if (item[2] == 'DocumentDate'){
                if (item [3] == 'ge' || item [3] == 'gt') {
                    Fecha_clave = item [4]+item [5];
                    Fecha_clave = Fecha_clave.substring(9,Fecha_clave.size()-1);
                    Fecha_inicial = Fecha_clave;
                    
                    //Calculamos el dia previo
                    DateFormat dateFormatNoTime = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat dateFormatTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    def previousDay = new Date(dateFormatNoTime.parse(Fecha_clave).getTime() -1);
                    Fecha_clave = dateFormatTime.format(previousDay);
                }
            }
            
            if (item[2] == "Material"){
                Filter_material = item[1];
                Filter_product = Filter_material.replace("Material","Product");
            }
        }
		
		message.setProperty("Fecha_clave",Fecha_clave);
		message.setProperty("Fecha_inicial",Fecha_inicial);
		message.setProperty("Filter_material",Filter_material);
		message.setProperty("Filter_product",Filter_product);
		
	}
	
	//Set Headers
	message.setHeader("odataEntity",entityName);
	message.setHeader("odataURI",odataURI.toString());

	return message;
}