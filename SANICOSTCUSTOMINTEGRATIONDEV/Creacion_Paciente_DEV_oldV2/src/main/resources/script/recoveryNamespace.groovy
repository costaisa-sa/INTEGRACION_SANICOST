import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def body = message.getBody(String.class)
    
    if (body) {
        // Usamos una expresión regular para buscar la etiqueta raíz (ej. <ADT_A05> o <ADT_A28>)
        // y la reemplazamos añadiendo el namespace que espera el Message Mapping
        body = body.replaceFirst(/<(ADT_[A-Z0-9]{3})>/, '<$1 xmlns="urn:hl7-org:v2xml">')
        
        message.setBody(body)
    }
    
    return message
}