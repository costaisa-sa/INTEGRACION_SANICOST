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
    def bodyMessage = message.getBody(String);
    def body = new XmlSlurper().parseText(bodyMessage);
    def addr;
    def tax;

    body.entry.link.each{add -> 
        if (add.@title == "to_BusinessPartnerAddress"){
            addr = add.inline.feed.entry.content.properties
        }
        if (add.@title == "to_BusinessPartnerTax"){
            tax = add.inline.feed.entry.content.properties
        } 
    }
    
    def patientProperty = message.getProperty("Patient");
    def patient = new XmlSlurper().parseText(patientProperty);

    // message.setProperty("body", body);
    // message.setProperty("tax", tax);
    // message.setProperty("addr", addr);
    // message.setProperty("patient", patient);
    message.setProperty("patient.FirstName", patient.FirstName);
    message.setProperty("patient.CorrespondenceLanguage", patient.CorrespondenceLanguage);
    message.setProperty("patient.Language", patient.Language);
    message.setProperty("patient.SearchTerm1", patient.SearchTerm1);
    message.setProperty("patient.LastName", patient.LastName);
    message.setProperty("patient.IsFemale", patient.IsFemale);
    message.setProperty("patient.IsMale", patient.IsMale);
    message.setProperty("patient.BirthDate", patient.BirthDate);
    
    message.setProperty("body.FirstName", body.entry.content.properties.FirstName);
    message.setProperty("body.CorrespondenceLanguage", body.entry.content.properties.CorrespondenceLanguage);
    message.setProperty("body.Language", body.entry.content.properties.Language);
    message.setProperty("body.SearchTerm1", body.entry.content.properties.SearchTerm1);
    message.setProperty("body.LastName", body.entry.content.properties.LastName);
    message.setProperty("body.IsFemale", body.entry.content.properties.IsFemale);
    message.setProperty("body.IsMale", body.entry.content.properties.IsMale);
    message.setProperty("body.BirthDate", body.entry.content.properties.BirthDate);
        
    if( body.entry.content.properties.FirstName != patient.FirstName || 
        body.entry.content.properties.CorrespondenceLanguage != patient.CorrespondenceLanguage ||
        body.entry.content.properties.Language != patient.Language ||
        body.entry.content.properties.SearchTerm1 != patient.SearchTerm1 ||
        body.entry.content.properties.LastName != patient.LastName ||
        body.entry.content.properties.IsFemale != patient.IsFemale ||
        body.entry.content.properties.IsMale != patient.IsMale ||
        body.entry.content.properties.BirthDate != patient.BirthDate) {
        message.setProperty("UpdatedPatient", 'X');
    } else {
        message.setProperty("UpdatedPatient", '');
    }

    message.setProperty("addr.CityName", addr.CityName);
    message.setProperty("addr.StreetName", addr.StreetName);
    message.setProperty("addr.HouseNumber", addr.HouseNumber);
    message.setProperty("addr.PostalCode", addr.PostalCode);
    message.setProperty("addr.Country", addr.Country);
    message.setProperty("addr.Region", addr.Region);
    
    message.setProperty("patient.CityName", patient.CityName);
    message.setProperty("patient.StreetName", patient.StreetName);
    message.setProperty("patient.HouseNumber", patient.HouseNumber);
    message.setProperty("patient.PostalCode", patient.PostalCode);
    message.setProperty("patient.Country", patient.Country);
    message.setProperty("patient.Region", patient.Region);

    message.setProperty("AddressID", addr.AddressID);
    if( addr.CityName != patient.CityName ||
        addr.StreetName != patient.StreetName ||
        addr.HouseNumber != patient.HouseNumber ||
        addr.PostalCode != patient.PostalCode ||
        addr.Country != patient.Country ||
        addr.Region != patient.Region) {
        message.setProperty("UpdatedPatientAddress", 'X');
    } else {
        message.setProperty("UpdatedPatientAddress", '');
    }

    message.setProperty("tax.BPTaxNumber", tax.BPTaxNumber);
    
    message.setProperty("patient.BPTaxNumber", patient.BPTaxNumber);

    message.setProperty("BPTaxType", tax.BPTaxType);
    if(tax.BPTaxNumber != patient.BPTaxNumber) {
        message.setProperty("UpdatedPatientTax", 'X');
    } else {
        message.setProperty("UpdatedPatientTax", '');
    }
    return message;
}