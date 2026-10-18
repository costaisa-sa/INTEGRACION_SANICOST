import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*

def Message processData(Message message) {
    //Get Body 
    def body = message.getBody(String.class);

    //Define JSONSlurper
    def jsonSlurper = new JsonSlurper()
    def oJSONBody = jsonSlurper.parseText(body);
    
   /* if (oJSONBody.status == 'fail'){
        //Format the JSON output
        def OJSONReturn = JsonOutput.toJson(oJSONBody.data.body);
        
        message.setBody(new JsonBuilder(OJSONReturn).toString());
        
        //Set the modified JSON to body and return
        throw new Exception(new JsonBuilder(OJSONReturn).toString());
    }*/
    
    return message;
}