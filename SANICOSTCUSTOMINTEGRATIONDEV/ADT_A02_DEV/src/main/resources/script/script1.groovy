import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       //ZPV10
       def ZPV10 = completeXml.ZPV.'**'.findAll{ node-> node.name() == 'ZPV.10' }*.text();
       if(ZPV10){
           def valor= ZPV10[0];
           message.setProperty("ZPV10", valor);
       }
       return message;
}