import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def refItem = message.getProperty("P_RefItem")
    def key = refItem != null ? (refItem as Integer).toString() : "0"
    def map = message.getProperty("MAP_POS2ACT") ?: [:]
    message.setProperty("P_ActivityID", (map[key] ?: "").toString())
    return message
}