import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
       def filter   = "";
   
       //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       
       //NHC1
       def NHC1  = completeXml.'**'.findAll{ node-> node.name() == 'NHCDeudor1' }*.text();
       if(NHC1){
         message.setProperty("NHCDeudor1",  NHC1[0]);
       }
       //NHC2
       def NHC2  = completeXml.'**'.findAll{ node-> node.name() == 'NHCDeudor2' }*.text();
       if(NHC2){
         message.setProperty("NHCDeudor2",  NHC2[0]);
       }
       //CamaAntDeudor1
       def CamaAnt1 = completeXml.'**'.findAll{ node-> node.name() == 'CamaAntDeudor1' }*.text();
       if(CamaAnt1){
         message.setProperty("CamaAntDeudor1", CamaAnt1[0]);
       }
       //CamaActualDeudor1
       def CamaAct1 = completeXml.'**'.findAll{ node-> node.name() == 'CamaActualDeudor1' }*.text();
       if(CamaAct1){
         message.setProperty("CamaActualDeudor1", CamaAct1[0]);
       }
       //CamaAntDeudor2
       def CamaAnt2 = completeXml.'**'.findAll{ node-> node.name() == 'CamaAntDeudor2' }*.text();
       if(CamaAnt2){
         message.setProperty("CamaAntDeudor2", CamaAnt2[0]);
       }
       //CamaActualDeudor2
       def CamaAct2 = completeXml.'**'.findAll{ node-> node.name() == 'CamaActualDeudor2' }*.text();
       if(CamaAct2){
         message.setProperty("CamaActualDeudor2", CamaAct2[0]);
       }
       //TipoPrestacionID
       def TipoPrestacionID = completeXml.'**'.findAll{ node-> node.name() == 'TipoPrestacionID' }*.text();
       if(TipoPrestacionID){
         message.setProperty("TipoPrestacionID", TipoPrestacionID[0]);
       }
       //Montamos el filtro dinamico para la busqueda de Deudores
       if(NHC1 && NHC2){
         filter= "DeudorExtID eq " + "'" + NHC1[0] + "'" +" or  DeudorExtID eq " + "'" + NHC2[0] + "'";
       }
        
       //Añadimos el filtro por tipo de Deudor
       filter= filter + " and DeudorTypeID eq 'P'";
       message.setProperty("DynamicFilter", filter);
       return message;
}