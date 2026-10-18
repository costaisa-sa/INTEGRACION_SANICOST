import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog == null) return message 

    def headers = message.getHeaders()
    
    // 1. Cabeceras que ya se extrayeron en Script 1
    def parentKeys = ["RequesterID", "MessageID", "PatientN", "ActivityID", "Center", "PlanCoberturaID"]
    
    // 2. Cabeceras que vienen del Hijo (Mapeo o Errores)
    def childKeys = ["Status", "DeudorID", "ActivityCheck", "ErrorHIS", "ErrorHISDesc"]

    // Unificamos y escribimos en el MPL
    (parentKeys + childKeys).each { key ->
        def value = headers.get(key)
        
        // Lógica condicional: Status y Center siempre con N/A, el resto solo si existen
        if (key == "Status" || key == "Center") {
            messageLog.addCustomHeaderProperty(key, value ?: "N/A")
        } else if (value && value.toString().trim() != "") {
            messageLog.addCustomHeaderProperty(key, value.toString())
        }
    }

    return message
}