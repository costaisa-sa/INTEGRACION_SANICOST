import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    def i;    

    //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
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
      
        //Generamos el ID Actividad para relacionarlo con la codificación
       def PV18 =  completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.18' }*.text();
       //def PV50 =  completeXml.PV1.'PV1.50'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       
       //>>>modificacion 09.01.2020
        //def Interv = completeXml.ZQUI.'**'.findAll{ node-> node.name() == 'ZQUI.1' }*.text();
       def Interv = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.52' }*.text();
       //<<<modificacion 09.01.2020
       
       if(PV18 && Interv){
        //>>>PRG 27.02.2020   
         //def ActivityUniqueID = PV18[0]+Interv[0];
         def ActivityUniqueID = Interv[0];
        //<<<PRG 27.02.2020 
         message.setProperty("ActivityUniqueID", ActivityUniqueID);
       }
        
       /*Calculamos la EtiquetaHIS
         def Ambito      = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
         def AmbitoValor = Ambito[0];
        if(PV18[0] == 'QUI'){ //Quirofano
          def EtiquetaHIS = AmbitoValor+PV50[0];
          message.setProperty("PrestacionHIS", EtiquetaHIS ); 
       }*/
               
       //Calculamos la EtiquetaHIS
        if(PV18){
          message.setProperty("PrestacionHIS", PV18[0] ); 
          //>>>PRG 24.11.2020 Guardar motivo alta = 'sin motivo' cuando es una Cirugia Mayor Ambulatoria y no viene informado ningún motivo.
          if (PV18[0] == 'CMA') {
              message.setProperty("MotivoAltaID",'CM'); //Ver tabla 'Reasons'
          }
          else {
              message.setProperty("MotivoAltaID",''); 
          }
          //<<<PRG 24.11.2020
       }
       
       //FechaHoraInicio&FechaHoraFin
       //>>>Modificacion 15.01.2020
       /*def obxType =  completeXml.'OBX'.'**'.findAll{ node-> node.name() == 'OBX.1' }*.text();
       def obxDate =  completeXml.'OBX'.'**'.findAll{ node-> node.name() == 'OBX.5' }*.text();
       for (i = 0; i <obxType.size(); i++) {
          if(obxType[i] == '1'){
            def valor3 = obxDate[i];
            String timestamp = Date.parse('yyyyMMddHHmmss', valor3).format("yyyy-MM-dd'T'HH:mm:ss");
            message.setProperty("FechaHoraInicio", timestamp);
            
          }else if(obxType[i] == '8'){ 
            valor3 = obxDate[i];
            timestamp = Date.parse('yyyyMMddHHmmss', valor3).format("yyyy-MM-dd'T'HH:mm:ss");
            message.setProperty("FechaHoraFin", timestamp);
          }
       }*/
       def oFechaInicioNode = completeXml.PV1.'PV1.44'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
       def sFechaInicio = oFechaInicioNode[0];
       //>>>PRG ERR-PRD-2021-48
       //String timestamp1 = Date.parse('yyyyMMddHHmmss', sFechaInicio).format("yyyy-MM-dd'T'HH:mm:ss");
       String timestamp1 = Date.parse('yyyyMMddHHmmSSSSSSS', sFechaInicio).format("yyyy-MM-dd'T'HH:mm:ss");
       //<<<PRG ERR-PRD-2021-48
       String timestamp2 = timestamp1;
       
       //>>>PRG 11.03.2021
       def oFechaFinNode = completeXml.PV1.'PV1.45'.'**'.findAll{ node-> node.name() == 'TS.1' }*.text();
        if (oFechaFinNode[0]) {
            def sFechaFin = oFechaFinNode[0];
            //>>>PRG ERR-PRD-2021-48
            //timestamp2 = Date.parse('yyyyMMddHHmmss', sFechaFin).format("yyyy-MM-dd'T'HH:mm:ss");
            timestamp2 = Date.parse('yyyyMMddHHmmSSSSSSS', sFechaFin).format("yyyy-MM-dd'T'HH:mm:ss");
            //<<<PRG ERR-PRD-2021-48
        }
       //<<<>PRG 11.03.2021
       
       message.setProperty("FechaHoraInicio", timestamp1);
       message.setProperty("FechaHoraFin", timestamp2);
       //<<<Modificacion 15.01.2020
       return message
}