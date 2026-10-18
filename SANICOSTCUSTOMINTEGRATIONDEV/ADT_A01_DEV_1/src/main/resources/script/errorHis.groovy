import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    // Obtenemos el header (si es una property, cambia getHeader por getProperty)
    def jsonString = message.getHeader("errorhisdesc", String.class)
    
    if (jsonString != null && !jsonString.isEmpty()) {
        try {
            // Parseamos el JSON
            def jsonSlurper = new JsonSlurper()
            def parsedJson = jsonSlurper.parseText(jsonString)
            
            // Extraemos el valor del campo 'text' y lo guardamos en una Property
            message.setProperty("textoError", parsedJson.text)
        } catch (Exception e) {
            // Si el JSON falla, devolvemos el texto original para no romper el flujo
            message.setProperty("textoError", jsonString)
        }
    } else {
        message.setProperty("textoError", "")
    }
    
    return message
}