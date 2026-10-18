import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
        //Get Properties  
       def map       = message.getProperties();
       def CamaAnt2  = map.get("CamaAntDeudor2");
       def CamaAct2  = map.get("CamaActualDeudor2");
   
       //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       
       //Add CamaAntDeudor2
       completeXml.Activities.ActivitiesType[0].appendNode("CamaAntDeudor2", [:], CamaAnt2);
       //Add CamaActualDeudor2
       completeXml.Activities.ActivitiesType[0].appendNode("CamaActualDeudor2", [:], CamaAct2);
       return message;
}