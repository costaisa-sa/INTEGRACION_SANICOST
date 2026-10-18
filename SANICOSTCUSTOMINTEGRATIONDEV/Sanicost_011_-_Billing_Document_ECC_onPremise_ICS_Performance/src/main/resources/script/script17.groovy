import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    def json = new JsonSlurper().parse(message.getBody(java.io.Reader.class))
    def pos2act = [:]
    def ids = []
    (json?.d?.results ?: []).each { r ->
        def pos = r.Position != null ? (r.Position as Integer).toString() : ""
        def id  = (r.ActivityID ?: "").toString()
        if (pos && id) { pos2act[pos] = id; ids << id }
    }
    message.setProperty("MAP_POS2ACT", pos2act)
    message.setProperty("LIST_ACTIVITYIDS", ids.unique())

    def mpl = messageLogFactory.getMessageLog(message)
    if (mpl != null) {
        mpl.addAttachmentAsString("PreFetch_ActivityMap",
            "Posiciones mapeadas: ${pos2act.size()} - ActivityIDs unicos: ${ids.unique().size()}", "text/plain")
    }
    return message
}