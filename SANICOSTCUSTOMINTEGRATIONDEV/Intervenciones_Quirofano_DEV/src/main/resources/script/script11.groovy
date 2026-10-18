import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    // Leer body como String
    def body = message.getBody(String) as String

    // Parsear XML
    def xml = new XmlSlurper().parseText(body)

    // Obtener valores, si no existen poner ""
    def center = xml.Activities.Center?.text() ?: ""
    def status = xml.Activities.Status?.text() ?: ""

    // Setear headers
    message.setHeader("Center", center)
    message.setHeader("Status", status)

    return message
}