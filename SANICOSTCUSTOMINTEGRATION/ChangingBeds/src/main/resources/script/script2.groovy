import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
      //Get Properties  
       def map      = message.getProperties();
       def TipoPrestacionID  = map.get("TipoPrestacionID");
       def NHC1     = map.get("NHCDeudor1");
       def NHC2     = map.get("NHCDeudor2");
       def filter   = "";
   
   
       //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       //DeudorExtID
       def DeudorExtID  = completeXml.'**'.findAll{ node-> node.name() == 'DeudorExtID' }*.text();
       //NHC
       def NHC  = completeXml.'**'.findAll{ node-> node.name() == 'NHC' }*.text();
       
       //Debe haber encontrado los dos Deudores
       if(DeudorExtID.size() != 2){
            String exceptionBody = "No se ha encontrado los dos Deudores para el intercambio de camas.";
            throw new Exception(exceptionBody);
       }
       
      //Montamos el filtro dinamico para ambos pacientes
       for (i = 0; i <DeudorExtID.size(); i++) {
          //Añadimos todos los PacienteExtID a buscar
          if(i == 0){
            filter= filter + "PacienteExtID eq " + "'" + DeudorExtID[i] + "'";
          }else{ 
            filter= filter + " or " + "PacienteExtID eq " + "'" + DeudorExtID[i] + "'";
          }
         
         //Asignamos el codigo externo del Deudor para posteriormente asignar las camas
         if(NHC[i] == NHC1){
            message.setProperty("PacienteExt1", DeudorExtID[i]);
          }else{ 
             message.setProperty("PacienteExt2", DeudorExtID[i]);
          }
     
       } 
       
       //Añadimos el filtro por TipoPrestacionID
       filter= filter + " and " +" TipoPrestacionID eq "+"'"+TipoPrestacionID+"'";
       message.setProperty("DynamicFilterActivities", filter);
       return message;
}