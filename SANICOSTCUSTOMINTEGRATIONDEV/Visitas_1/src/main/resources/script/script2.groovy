import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class)
       def completeXml= new XmlSlurper().parseText(xml)
       //ConvenioID
       def ConvenioID   = completeXml.'**'.findAll{ node-> node.name() == 'ConvenioID' }*.text()
       def ConvenioSize =  ConvenioID.size()
       
       //Solo setearemos valor cuando el convenio sea igual a 1
       if(ConvenioSize > 1 || ConvenioSize == 0){
         message.setProperty("ConvenioID", "")
       }else{
         message.setProperty("ConvenioID", ConvenioID[0])
       }
       return message
}