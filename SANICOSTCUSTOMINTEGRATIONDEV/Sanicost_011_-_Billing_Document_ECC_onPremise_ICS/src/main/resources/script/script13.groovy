import com.sap.it.api.mapping.*
import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    // 1. Obtener el cuerpo del mensaje como String
    def body = message.getBody(java.lang.String)
    
    // 2. Almacenar el body en la propiedad deseada
    message.setProperty("Body0001", body)
    
    return message
}