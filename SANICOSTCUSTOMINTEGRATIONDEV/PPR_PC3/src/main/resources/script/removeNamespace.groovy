import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    // Leemos el payload como texto
    def body = message.getBody(String.class)
    
    if (body) {
        // Buscamos la declaración del namespace y la borramos (la sustituimos por vacío)
        body = body.replace('xmlns="urn:hl7-org:v2xml"', '')
        
        // Guardamos el payload limpio
        message.setBody(body)
    }
    
    return message
}