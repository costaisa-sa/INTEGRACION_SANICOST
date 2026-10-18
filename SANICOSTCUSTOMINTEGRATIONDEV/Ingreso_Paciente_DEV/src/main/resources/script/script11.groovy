import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    // Leer body como String
    def body = message.getBody(String) as String

    // Parsear XML
    def xml = new XmlSlurper().parseText(body)

    // Obtener valores del XML (según la estructura <Activities><Activities>...)
    def status = xml.Activities.Status?.text() ?: ""
    def deudorId = xml.Activities.DeudorID?.text() ?: ""

    // Setear headers para que el padre los reciba
    message.setHeader("Status", status)
    message.setHeader("DeudorID", deudorId)

    return message
}