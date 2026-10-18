import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def headers = message.getHeaders()
    def value = headers.get("ActivityCheck")

    // Normalización:
    // - Si es null, vacío, o distinto de "0" → 1
    // - Si es "0" → se mantiene "0"
    def normalized = "1"  // valor por defecto
    if (value != null && value.toString().trim() == "0") {
        normalized = "0"
    }

    // Actualizar el header
    message.setHeader("ActivityCheck", normalized)

    return message
}
