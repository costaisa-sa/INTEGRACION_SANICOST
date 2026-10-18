import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       def HD2 =  completeXml.MSH.'MSH.4'.'**'.findAll{ node-> node.name() == 'HD.2' }*.text();
       message.setProperty("HD2", HD2[0]);
       return message;
}