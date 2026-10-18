import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       //MSH92
       def MSH92 = completeXml.MSH.'MSH.9'.'**'.findAll{ node-> node.name() == 'MSG.2' }*.text();
       if(MSH92){
           def valor= MSH92[0];
           message.setProperty("MSH92", valor);
       }
      //PV118
       def PV118 = completeXml.PV1.'**'.findAll{ node-> node.name() == 'PV1.18' }*.text();
       if(PV118){
           def valor2 = PV118[0];
           message.setProperty("PV118", valor2);
       }
       return message;
}