import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*

def Message processData(Message message) {
    def mapProps = message.getProperties();
    def mapHeaders = message.getHeaders();
    def ex = mapProps.get("CamelExceptionCaught");
    
    def sFullExceptionMessage = ex.getResponseBody() as String;
    def sJSONExceptionData = sFullExceptionMessage.substring(sFullExceptionMessage.indexOf("{"),sFullExceptionMessage.lastIndexOf("}")+1);
    
    def jsonSlurper = new JsonSlurper();
    def jsonExceptionDataObject = jsonSlurper.parseText(sJSONExceptionData);
    
    // 1. Recuperar el ActivityID del Header (asegúrate de que el nombre coincida exactamente)
    def sActivityID = mapHeaders.get("ActivityID"); 
    def sActivityCodExt = mapHeaders.get("ActivityCodExt"); 
    
    // 2. Inyectar el valor en el nodo content del JSON si existe en el header
    if (sActivityID != null) {
        jsonExceptionDataObject.content.ActivityID = sActivityID;
        jsonExceptionDataObject.content.ActivityCodExt = sActivityID;
    }
    
    def sMessageProcessingLogID = jsonExceptionDataObject.MessageProcessingLogID;
    
    // 3. Ahora al construir el String, ya llevará el ActivityID correcto
    def sActivityContent = new JsonBuilder(jsonExceptionDataObject.content).toString();
    
    jsonExceptionDataObject.remove("MessageProcessingLogID");
    jsonExceptionDataObject.remove("content");
    sJSONExceptionData = new JsonBuilder(jsonExceptionDataObject).toString();
    
    mapProps.put("ErrorContent", sJSONExceptionData);
    mapProps.put("MessageProcessingLogID", sMessageProcessingLogID);
    mapProps.put("ActivityContent", sActivityContent);
    
    message.setBody("");
    
    return message;
}