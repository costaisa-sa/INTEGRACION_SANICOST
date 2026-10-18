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
	
	if (uriInfo.getTop() != null){
		def top = uriInfo.getTop();
		if(odataURI.size()!=0)
			odataURI.append(urlDelimiter);
		odataURI.append("\$top=").append(top);
	}
	
	if (uriInfo.getSkip() != null){
		def skip = uriInfo.getSkip();
		if(odataURI.size()!=0)
			odataURI.append(urlDelimiter);
		odataURI.append("\$skip=").append(skip);
		log.logErrors(LogMessage.TechnicalError, "Skip value:"+skip);
	}

    //filter
    def filter_estupes = "ProductType eq '001' and YY1_MM_Estupefacientes_PRD eq true";
    if (uriInfo.getFilter()!= null){
		def filter = uriInfo.getFilter().getExpressionString();
		
		if(odataURI.size()!=0) odataURI.append(urlDelimiter);
			
		odataURI.append("\$filter=").append(filter+" and "+filter_estupes);
	}
	else {
	    if(odataURI.size()!=0) odataURI.append(urlDelimiter);
	    
	    odataURI.append("\$filter=").append(filter_estupes);
	}
    
    //expand y select
    if(odataURI.size()!=0){
        odataURI.append(urlDelimiter);
    }
    odataURI.append("\$expand=to_Description&\$select=to_Description/Product,to_Description/ProductDescription");
    

	//Set Headers
	message.setHeader("odataEntity",entityName);
	message.setHeader("odataURI",odataURI.toString());

	return message;
}