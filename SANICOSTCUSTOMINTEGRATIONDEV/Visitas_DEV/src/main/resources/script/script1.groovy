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
def Message processData(Message message) {
    def filter   = "";
    
    //Body 
       def xml = message.getBody(String.class)
       def completeXml= new XmlSlurper().parseText(xml)
       
       /*
        //>>>PRG ERR-35
       //ActivityExt
       def ActivityCodExt = completeXml.PV1.'PV1.52'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text()
       if(ActivityCodExt){
         def valor = ActivityCodExt[0];
         message.setProperty("ActivityCodExt", valor)
       }
       else message.setProperty("ActivityCodExt", '');
       //<<<PRG ERR-35
       
      //NHC
       def NHC = completeXml.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text()
       if(NHC){
         def valor = NHC[0];
         message.setProperty("NHC", valor)
       }
       //Garante
       def Garante = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text()
       if(Garante){
         def valor2 = Garante[0];
         message.setProperty("Garante", valor2)
       }
       //PrestacionHIS
       def PrestacionHIS =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.4' }*.text()
       if(PrestacionHIS){
         def valor3 = PrestacionHIS[0];
         message.setProperty("PrestacionHIS", valor3)
       }
       //FechaHoraInicio
       def FechaHoraInicio =  completeXml.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.23' }*.text()
       def valor4 = FechaHoraInicio[0];
       if(valor4){
           String timestamp = Date.parse('yyyyMMddHHmmss', valor4).format("yyyy-MM-dd'T'HH:mm:ss")
           message.setProperty("FechaHoraInicio", timestamp)
       }
       //MedicoResponsable
       def MedicoResponsable =  completeXml.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.17' }*.text()
       if(MedicoResponsable){
         message.setProperty("MedicoResponsable", MedicoResponsable[0])
       }else{message.setProperty("MedicoResponsable", '')}
       //MedicoRealizador
       def MedicoRealizador =  completeXml.PV1.'PV1.7'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text()
       if(MedicoRealizador){
         message.setProperty("MedicoRealizador", MedicoRealizador[0])
       }else{message.setProperty("MedicoRealizador", '')} 
       
       //Montamos el filtro dinamico
       if(MedicoResponsable && MedicoRealizador){
         filter= "MedicoExtID eq " + "'" + MedicoResponsable[0] + "'" +" or  MedicoExtID eq " + "'" + MedicoRealizador[0] + "'";
       }else if(!MedicoResponsable && MedicoRealizador){
         filter= "MedicoExtID eq " + "'" + MedicoRealizador[0] + "'";  
       }else if(MedicoResponsable && !MedicoRealizador){
         filter= "MedicoExtID eq " + "'" + MedicoResponsable[0] + "'"; 
       }
       message.setProperty("DynamicFilter", filter);
       */
       
       return message
}
