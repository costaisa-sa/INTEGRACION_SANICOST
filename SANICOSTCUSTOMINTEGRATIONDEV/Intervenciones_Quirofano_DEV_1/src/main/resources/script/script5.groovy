import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import org.json.simple.*
import groovy.json.*

def Message processData(Message message) {
    //Get Body 
    def body = message.getBody(String.class);

    //Define JSONSlurper
    def jsonSlurper = new JsonSlurper()
    def list = jsonSlurper.parseText(body)
    
    //Define JSONObject and add a root node
    JSONObject myobj = new JSONObject(); 
    myobj.put("Root",list);

    //Format the JSON output
    def jsonOP = JsonOutput.toJson(myobj)
    
    //Set the modified JSON to body and return
    message.setBody(jsonOP);
    return message;
}