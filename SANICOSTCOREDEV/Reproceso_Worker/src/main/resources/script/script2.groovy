import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    def guid = message.getProperty("guid")
    def body = message.getBody(java.lang.String)
    def parsed = new JsonSlurper().parseText(body ?: "{}")
    def results = []
    if (parsed?.d?.results instanceof List) { results = parsed.d.results }
    def url = null
    for (r in results) {
        def nm = (r?.Name == null) ? "" : r.Name.toString()
        if (nm.startsWith("SendPayload")) {
            url = r?.__metadata?.media_src
            break
        }
    }
    if (url == null) { throw new RuntimeException("Sin attachment SendPayload para guid=" + guid) }
    message.setHeader("attUrl", url.toString())
    return message
}