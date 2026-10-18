import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    // 1. Obtener el cuerpo del mensaje (Payload)
    def body = message.getBody(java.lang.String)
    
    // 2. Parsear el JSON
    def jsonParser = new JsonSlurper()
    def jsonResponse = jsonParser.parseText(body)
    
    // 3. Lógica de validación
    // Verificamos si field es 'UPrealID' y status es 'fail'
    if (jsonResponse.field == "UPrealID" && jsonResponse.status == "fail") {
        message.setProperty("UPrealIDFlag", "Y")
    } else {
        message.setProperty("UPrealIDFlag", "N")
    }
    
    return message
}