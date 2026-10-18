import com.sap.gateway.ip.core.customdev.util.Message
def Message processData(Message message) {
    message.setProperty("guid", message.getBody(java.lang.String)?.trim())
    return message
}