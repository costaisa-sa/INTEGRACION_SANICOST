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
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
      //NHC
       def NHC = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       if(NHC){
         def valor = NHC[0];
         message.setProperty("NHC", valor);
       }
       
       //Garante
       def Garante = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.PV1.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text();
       if(Garante){
         def valor2 = Garante[0];
          //>>>PRG 17.04.2020 ERR-3 Si viene vacío, poner catsalut como garante
         valor2 = valor2.trim();
         if (valor2=='') valor2 = 'S99';
         //<<<PRG 17.04.2020 ERR-3
         message.setProperty("Garante", valor2);
       }
       
       //Fecha/Hora programación                          
       def FechaHoraInicio =  completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.23' }*.text();
       def valor3 = FechaHoraInicio[0];
       if(valor3){
           String timestamp = Date.parse('yyyyMMddHHmmss', valor3).format("yyyy-MM-dd'T'HH:mm:ss");
           message.setProperty("FechaHoraInicio", timestamp);
       }
       
       
       //EtiquetaHis
        def EtiquetaHIS = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.ORDER_OBSERVATION'.'ORU_R01.OBSERVATION'.OBX.'OBX.3'.'**'.findAll{ node-> node.name() == 'CE.4' }*.text();
         if(EtiquetaHIS){
           message.setProperty("PrestacionHIS", EtiquetaHIS[0]); 
         }
       
       
       //MedicoResponsable/Solicitante
       def MedicoResponsable =  completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.17' }*.text()
       if(MedicoResponsable){
         message.setProperty("MedicoResponsable", MedicoResponsable[0])
       }else{message.setProperty("MedicoResponsable", '')}
       //MedicoRealizador/Autofactura
       def MedicoRealizador =  completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.PV1.'PV1.7'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text()
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
       
       return message
}