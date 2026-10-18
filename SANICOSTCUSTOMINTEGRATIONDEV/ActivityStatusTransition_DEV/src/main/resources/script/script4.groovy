import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def Message processData(Messagemessage){
    def body =message.getBody(java.lang.String)asString
    def jsonParser =new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)message.setBody(JsonOutput.toJson(jsonObject["StatusTransitionRequest"]))returnmessage;}