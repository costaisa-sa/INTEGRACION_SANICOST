import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def reader = message.getBody(java.io.Reader)
    def xml = new XmlSlurper().parse(reader)
    message.setBody(xml.text().trim())
    return message
}