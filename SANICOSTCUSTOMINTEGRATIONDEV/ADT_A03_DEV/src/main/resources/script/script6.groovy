import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*

def Message processData(Message message) {
    def mapProps = message.getProperties();
    def ex = mapProps.get("CamelExceptionCaught");
    
    // Obtener el cuerpo del error (si no hay, usar un string vacío)
    def sFullExceptionMessage = ex?.getResponseBody() as String ?: "";
    
    // Variables por defecto en caso de que no sea JSON
    def sJSONExceptionData = '{"error": "Respuesta no-JSON del servidor"}'
    def sMessageProcessingLogID = ""
    def sActivityContent = "{}"

    try {
        // Intentar buscar los límites de un posible JSON
        int startIndex = sFullExceptionMessage.indexOf("{");
        int endIndex = sFullExceptionMessage.lastIndexOf("}");
        
        if (startIndex >= 0 && endIndex > startIndex) {
            def possibleJson = sFullExceptionMessage.substring(startIndex, endIndex + 1);
            
            def jsonSlurper = new JsonSlurper();
            def jsonExceptionDataObject = jsonSlurper.parseText(possibleJson); // Aquí probará si es JSON real
            
            // Si pasamos de aquí, SÍ es un JSON válido
            sMessageProcessingLogID = jsonExceptionDataObject.MessageProcessingLogID ?: "";
            sActivityContent = jsonExceptionDataObject.content ? new JsonBuilder(jsonExceptionDataObject.content).toString() : "{}";
            
            jsonExceptionDataObject.remove("MessageProcessingLogID");
            jsonExceptionDataObject.remove("content");
            sJSONExceptionData = new JsonBuilder(jsonExceptionDataObject).toString();
        } else {
            // No hay llaves, guardar el mensaje tal cual (evitando que rompa)
            sJSONExceptionData = '{"error": "HTTP Call failed, returned non-JSON format"}';
        }
    } catch (Exception parseEx) {
        // Si el parseText explota (como ocurrió con el HTML/CSS), caemos aquí
        // Guardamos todo el HTML del error en una propiedad para poder revisarlo
        mapProps.put("RawHTMLResponse", sFullExceptionMessage);
        sJSONExceptionData = '{"error": "Error de parseo JSON. El servidor devolvió HTML u otro formato."}';
    }
    
    mapProps.put("ErrorContent", sJSONExceptionData);
    mapProps.put("MessageProcessingLogID", sMessageProcessingLogID);
    mapProps.put("ActivityContent", sActivityContent);
    
    message.setBody("");
    
    return message;
}