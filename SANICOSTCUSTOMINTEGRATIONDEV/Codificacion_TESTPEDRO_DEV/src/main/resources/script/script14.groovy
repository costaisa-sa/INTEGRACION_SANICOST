import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

Message processData(Message message) {

    def body = message.getBody(String)

    // Definir el namespace HL7
    def ns = new groovy.xml.Namespace("urn:hl7-org:v2xml", "ns0")

    // Parsear XML con namespace
    def xml = new XmlSlurper(false, false).parseText(body)

    // Log inicial de entrada
    def log = messageLogFactory.getMessageLog(message)
    if (log) {
        log.addAttachmentAsString("XML de entrada", body, "text/xml")
    }

    // Lista para campos que falten
    def missingFields = []

    // Extraer valores respetando el namespace
    def diag       = xml[ns.BAR_P12][ns.DG1][ns.'DG1.3'][ns.'CWE.1']?.text()
    if (!diag) missingFields << "Diagnostic"

    def diagTxt    = xml[ns.BAR_P12][ns.PV1][ns.'PV1.3'][ns.'PL.2']?.text()
    if (!diagTxt) missingFields << "DiagnosticTxt"

    def activityID = message.getProperty("ActivityID")
    if (!activityID) missingFields << "ActivityID"

    def principal  = xml[ns.BAR_P12][ns.DG1][ns.'DG1.6']?.text()
    if (!principal) missingFields << "Principal"

    // Construir XML de salida
    def writer = new StringWriter()
    def xmlOut = new MarkupBuilder(writer)

    xmlOut.ActivityDiagnosticSet {
        ActivityDiagnosticSetType {
            Diagnostic(diag ?: "")
            DiagnosticTxt(diagTxt ?: "")
            ActivityID(activityID ?: "")
            Principal(principal ?: "")
        }
    }

    // Log de campos faltantes
    if (missingFields && log) {
        log.addAttachmentAsString("Campos faltantes", missingFields.join(", "), "text/plain")
    }

    message.setBody(writer.toString())
    return message
}
