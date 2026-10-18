import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    def reader = message.getBody(java.io.Reader)
    def parsed = reader ? new JsonSlurper().parse(reader) : [:]
    def guids  = parsed?.guids ?: []
    if (guids.isEmpty()) {
        throw new RuntimeException("Petición sin guids")
    }
    message.setProperty("numGuids", guids.size())
    return message
}