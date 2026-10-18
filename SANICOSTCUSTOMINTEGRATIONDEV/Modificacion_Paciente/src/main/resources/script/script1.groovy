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

//PCC – Indicador de Paciente Crónico Complejo
def getPCC(completeXml){

    def obxNode = completeXml.'**'.find { node -> 
        node.name() == 'OBX' && node.'OBX.3'.'OBX.3.1'.text() == 'PCC'
    }
    
    def sPCC; 
    if (obxNode) {
        sPCC = obxNode.'OBX.5'.'CF.1'.text()
    }
    
    def bPCC;
    if (sPCC == 'Y') bPCC = true;
    if (sPCC == 'N') bPCC = false;

    return bPCC;
}

//MACA - indicador de paciente con una enfermedad avanzada con pronóstico de vida limitada (MACA)
def getMACA(completeXml){

    // Buscamos nodo OBX donde OBX.3/CE.1 == 'MACA'
    def obxNode = completeXml.'**'.find { node ->
        node.name() == 'OBX' && node.'OBX.3'.'OBX.3.1'.text() == 'MACA'
    }
    
    def sMACA; 
    if (obxNode) {
        sMACA = obxNode.'OBX.5'.'CF.1'.text()
    }
    
    def bMACA;
    if (sMACA == 'Y') bMACA = true;
    if (sMACA == 'N') bMACA = false;


    return bMACA;
}

def getDocument(completeXml,sID){
    def sDocument= "";
    
    def aDocument = completeXml.PID.'PID.3'.findAll{ node-> node.'CX.5'.text() == sID }*.'CX.1';
    if (aDocument){
        sDocument = aDocument[0];
    }
    
    return sDocument;
}

def getTelefono(completeXml, sTag){
    def sNumero = "";
    
    def aNumero = completeXml.PID.'**'.find{node -> node.name() == sTag}*.'XTN.1';
    if (aNumero) {
        sNumero = aNumero[0];
    }
    
    return sNumero;
}

def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class)
       def completeXml= new XmlSlurper().parseText(xml)
       
//Idioma       
       def Idioma = completeXml.ZPID.'PID.15'.findAll{ node-> node.name() == 'CWE.1' }*.text()
       if(Idioma){
         def valor5 = Idioma[0];
         message.setProperty("Idioma", valor5)
       }
       
       /// Country
def Country = completeXml.PID.'**'.findAll{ node-> node.name() == 'XAD.6' }*.text()
def countryValue = Country ? Country[0] : ''
message.setProperty("Country", countryValue)

// Province (solo si Country == "ES")
if (countryValue == "ES") {
    def Province = completeXml.PID.'**'.findAll{ node-> node.name() == 'XAD.4' }*.text()
    def provinceValue = Province ? Province[0] : ''
    message.setProperty("Province", provinceValue)
} else {
    message.setProperty("Province", '')
}
      //Deudor
       def Deudor = completeXml.PID.'**'.findAll{ node-> node.name() == 'CX.1' }*.text()
       if(Deudor){
         def valor3 = Deudor[0];
         //def h_str = 'H';
         //valor3 = h_str.concat(valor3);         
         message.setProperty("Deudor", valor3)
       }
       /*
      //CIP
       def CIP = completeXml.PID.'PID.3'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text()
//leemos los HD1       
       def HD1 = completeXml.PID.'PID.3'.'**'.findAll{ node-> node.name() == 'HD.1' };
       
       if(CIP.size() > 1){
// BUSCAMOS SI HAY HD1 = CIP
        def pos = HD1.findIndexOf{ it=='CIP'};//
//        def valor4 = CIP[1];
        def valor4 = CIP[pos]; // si no hay CIP leemos el primero, si no el que toca
        message.setProperty("CIP", valor4)  
       }
       */
       
       //CIP
       message.setProperty("CIP",getDocument(completeXml,"JHN"));
       //SNS
       message.setProperty("SNS",getDocument(completeXml, "SS"));
       
       //Telefono1
       message.setProperty("Telefono1", getTelefono(completeXml, "PID.13"));
       //Telefono2
       message.setProperty("Telefono2", getTelefono(completeXml, "PID.14"));       
       
       //PCC y MACA
       message.setProperty("PCC",getPCC(completeXml));
       message.setProperty("MACA",getMACA(completeXml));
       
       return message
}
