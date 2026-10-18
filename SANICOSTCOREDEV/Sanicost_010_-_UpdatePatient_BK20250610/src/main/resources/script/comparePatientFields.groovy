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
    def bodyMessage = message.getBody();
    def body = new XmlSlurper().parseText(bodyMessage).children();
    def addr = body.to_BusinessPartnerAddress.A_BusinessPartnerAddressType;
    def tax = body.to_BusinessPartnerTax.A_BusinessPartnerTaxNumberType;

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
    
    message.setProperty("body.FirstName", body.FirstName);
    message.setProperty("body.CorrespondenceLanguage", body.CorrespondenceLanguage);
    message.setProperty("body.Language", body.Language);
    message.setProperty("body.SearchTerm1", body.SearchTerm1);
    message.setProperty("body.LastName", body.LastName);
    message.setProperty("body.IsFemale", body.IsFemale);
    message.setProperty("body.IsMale", body.IsMale);
    message.setProperty("body.BirthDate", body.BirthDate);
        
    if( body.FirstName != patient.FirstName || 
        body.CorrespondenceLanguage != patient.CorrespondenceLanguage ||
        body.Language != patient.Language ||
        body.SearchTerm1 != patient.SearchTerm1 ||
        body.LastName != patient.LastName ||
        body.IsFemale != patient.IsFemale ||
        body.IsMale != patient.IsMale ||
        body.BirthDate != patient.BirthDate) {
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