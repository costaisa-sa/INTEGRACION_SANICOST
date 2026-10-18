import com.sap.gateway.ip.core.customdev.util.Message;
import org.apache.camel.component.ahc.AhcOperationFailedException;
import org.json.simple.*
import groovy.json.*
def Message processData(Message message) {

    def oMapProps = message.getProperties();
    def oException = oMapProps.get("CamelExceptionCaught");
    
    
    /*def sFullExceptionMessage = oException.getResponseBody();
    def jsonSlurper = new JsonSlurper();
    def jsonDataObject = jsonSlurper.parseText(sFullExceptionMessage);
    def body = new JsonBuilder(jsonDataObject.data.body).toString();*/

    //message.setBody(oException.getResponseBody());
    
    message.setBody(oException)
    
    return message;
}