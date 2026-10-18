import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
   
       //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       
       //Deudor
       def Deudor  = completeXml.PID.'PID.2'.'**'.findAll{ node-> node.name() == 'CX.1' }*.text();
       if(Deudor){
        message.setProperty("NHCDeudor1",  Deudor[0]);
        message.setProperty("NHCDeudor2",  Deudor[1]);
       }
      
       //CamaAnterior
       def CamaAnterior  = completeXml.PV1.'PV1.3'.'**'.findAll{ node-> node.name() == 'PL.3' }*.text();
       if(CamaAnterior){
        message.setProperty("CamaAntDeudor1",  CamaAnterior[0]);
        message.setProperty("CamaAntDeudor2",  CamaAnterior[1]);
       }
        
       //CamaActual
       def CamaActual  = completeXml.PV1.'PV1.6'.'**'.findAll{ node-> node.name() == 'PL.3' }*.text();
       if(CamaActual){
        message.setProperty("CamaActualDeudor1",  CamaActual[0]);
        message.setProperty("CamaActualDeudor2",  CamaActual[1]);
       }
       return message;
}