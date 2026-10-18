import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    
    def messageLog = messageLogFactory.getMessageLog(message)

    // Obtener valor del header de forma compatible con todas las versiones de CPI
    def activityValue = message.getHeaders().get("ActivityCheck")

    // Registrar en el monitor igual que las otras 3
    if (messageLog != null) {
        messageLog.addCustomHeaderProperty("ActivityCheck", activityValue ?: "N/A")
    }

    return message
}