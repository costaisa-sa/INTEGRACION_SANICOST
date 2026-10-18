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
       def xml = message.getBody(String.class)
       def completeXml= new XmlSlurper().parseText(xml)
       //Generamos el ID Actividad para relacionarlo con el Ingreso 
       def PV18 =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.18' }*.text();
       def PV50 =  completeXml.PV1.'PV1.50'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       def Interv = completeXml.ZQUI.'**'.findAll{ node-> node.name() == 'ZQUI.1' }*.text();
       if(PV18[0] == 'QUI' || PV18[0] == 'CMA'){//Quirofano
         //>>>PRG 27.02.2020 
         //def ActivityUniqueID = PV18[0]+Interv[0];
         if (Interv) //A veces no viene el dato
         {
           def ActivityUniqueID = Interv[0];
         //<<<PRG 27.02.2020 
           message.setProperty("ActivityUniqueID", ActivityUniqueID);
         }
       }else{ //Hospitalario
         if(PV18 && PV50){
             ActivityUniqueID = PV18[0]+PV50[0];
             message.setProperty("ActivityUniqueID", ActivityUniqueID);
         }
       } 

    
       //Calculamos la EtiquetaHIS de la siguiente forma PV1.2 + PV1.3.3 (Ámbito + cama_alta) (Solo en ingreso hospitalario, en urgencias solo se quedará el PV1.2)
      // En intervenciones quirofano se cogera el numero de intervención 
        //def Ambito      = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
        //def AmbitoValor = Ambito[0];
        def CamaAlta    = completeXml.PV1.'PV1.3'.'**'.findAll{ node-> node.name() == 'PL.3' }*.text();
        def CamaAltaValor = CamaAlta[0];
        
       if(PV18[0] == 'QUI' || PV18[0] == 'CMA'){ //Quirofano
          //def EtiquetaHIS = PV18[0]+Interv[0];
          def EtiquetaHIS = PV18[0]; 
          message.setProperty("PrestacionHIS", EtiquetaHIS ); 
       }else{
           if(PV18 && CamaAltaValor){
               EtiquetaHIS = PV18[0]+CamaAltaValor;
               message.setProperty("PrestacionHIS", EtiquetaHIS); 
           }
       }
       
       //NHC
        def NHC = completeXml.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
        if(NHC){
          def valor = NHC[0];
          message.setProperty("NHC", valor);
        }
        
       //Garante
        def Garante = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text();
        if(Garante){
          def valor2 = Garante[0];
          message.setProperty("Garante", valor2);
        }
        
        //Cama
        def Cama =  completeXml.PV1.'PV1.3'.'**'.findAll{ node-> node.name() == 'PL.3' }*.text();
        if(Cama){
          message.setProperty("Cama", Cama[0]);
        }
        //ServicioRealizadorID
        def ServicioRealizadorID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.10' }*.text();
        if(ServicioRealizadorID){
          message.setProperty("ServicioRealizadorID", ServicioRealizadorID[0]);
        }

        message.setProperty("MedicoRealizadorID", ""); 
        message.setProperty("MedicoAutofacturaID", "");
        message.setProperty("MedicoResponsableID", "");
        def MedicoRealizadorID =  completeXml.PV1.'PV1.17'.'**'.findAll{ node-> node.name() == 'XCN.1' }*.text();
        if(MedicoRealizadorID){
           message.setProperty("MedicoRealizadorID", MedicoRealizadorID[0]); 
        //MedicoAutofacturaID (idem MedicoRealizadorID)
           message.setProperty("MedicoAutofacturaID", MedicoRealizadorID[0]);
        //MedicoResponsableID (idem MedicoRealizadorID)
           message.setProperty("MedicoResponsableID", MedicoRealizadorID[0]);
        }
        
        //MotivoAltaID
        def MotivoAltaID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.36' }*.text();
        if(MotivoAltaID){
           message.setProperty("MotivoAltaID", MotivoAltaID[0]); 
        }
        //>>>PRG 24.11.2020 Si motivo alta no viene informado, se indicará 'sin motivo' cuando es una Cirugia Mayor Ambulatoria
        else {
            if(PV18[0] == 'CMA'){
                message.setProperty("MotivoAltaID", 'CM'); //Ver tabla Reasons
            }
        }
        //<<<PRG 24.11.2020
       
        //TipoPrestacionID
         def TipoPrestacionID =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
        if(TipoPrestacionID){
           message.setProperty("TipoPrestacionID", TipoPrestacionID[0]); 
        }
        
        //Cirujano
        def Cirujano =  completeXml.ZQUI.'**'.findAll{ node-> node.name() == 'ZQUI.3' }*.text();
        if(Cirujano){
           message.setProperty("Cirujano", Cirujano[0]); 
        }
        
        //FechaPlanCobertura
        if(PV18[0] == 'QUI' || PV18[0] == 'CMA'){
          //Fecha de intervencion
          def fecha =  completeXml.ZQUI.'**'.findAll{ node-> node.name() == 'ZQUI.2' }*.text();
          if(fecha){
             //>>>PRG ERR-PRD-2021-48
             String timestamp = Date.parse('yyyyMMddHHmmss', fecha[0]).format("yyyy-MM-dd'T'HH:mm:ss");
             //String timestamp = Date.parse('yyyyMMddHHmmSSSSSSS', fecha[0]).format("yyyy-MM-dd'T'HH:mm:ss");
             //<<<PRG ERR-PRD-2021-48
             message.setProperty("FechaPlanCobertura", timestamp);
          }
        }else{
          //Fecha ingreso  
           fecha = completeXml.PV1.'PV1.44'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
           if(fecha){
            //>>>PRG ERR-PRD-2021-48
            timestamp = Date.parse('yyyyMMddHHmmss', fecha[0]).format("yyyy-MM-dd'T'HH:mm:ss");
            //timestamp = Date.parse('yyyyMMddHHmmSSSSSSS', fecha[0]).format("yyyy-MM-dd'T'HH:mm:ss");
            //<<<PRG ERR-PRD-2021-48
            message.setProperty("FechaPlanCobertura", timestamp);
           }
        }
        
        
        return message
}