import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    
    // 1. Obtenemos el valor de la property 'DeudorID'
    def deudorID = message.getProperty("DeudorID")
    
    // 2. Si es nulo o está vacío (""), le asignamos el guion "-"
    if (!deudorID) {
        message.setProperty("DeudorID", "-")
    }
    
    return message
}