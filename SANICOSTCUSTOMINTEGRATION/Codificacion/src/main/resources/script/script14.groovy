import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

Message processData(Message message) {

    // 1. Obtener el cuerpo del mensaje y preparar el log
    def body = message.getBody(String)
    def log = messageLogFactory.getMessageLog(message)
    if (log) {
        log.addAttachmentAsString("XML de Entrada (Para Bucle Final)", body, "text/xml")
    }

    // 2. Parsear el XML
    def xml = new XmlSlurper(false, false).parseText(body)

    // 3. Obtener el valor que es CONSTANTE para todas las iteraciones
    def activityID = message.getProperty("ActivityID") ?: ""

    // 4. Construir el XML de salida usando un bucle sobre cada DG1
    def writer = new StringWriter()
    def xmlOut = new MarkupBuilder(writer)

    // Se crea el nodo raíz <ActivityDiagnosticSet>
    xmlOut.ActivityDiagnosticSet {

        // --- INICIO DEL BUCLE 1-a-N ---
        xml.DG1.each { dg1Node ->

            // Para CADA DG1, se extraen sus valores específicos:
            def currentDiagnostic = dg1Node.'DG1.3'.'CE.1'?.text()
            def currentDiagnosticTxt = dg1Node.'DG1.3'.'CE.2'?.text()
            def currentPrincipal = (dg1Node.'DG1.1'.text() == "1") ? "1" : ""
            
            // --- ¡ORDEN DE CAMPOS CORREGIDO AQUÍ! ---
            // Se crea un nuevo nodo <ActivityDiagnosticSetType> con el orden solicitado.
            ActivityDiagnosticSetType {
                ActivityID(activityID)
                Diagnostic(currentDiagnostic ?: "")
                Principal(currentPrincipal)
                DiagnosticTxt(currentDiagnosticTxt ?: "")
            }
        }
        // --- FIN DEL BUCLE ---
    }

    // 5. Establecer el XML generado como el nuevo cuerpo del mensaje
    def finalXml = writer.toString()
    if (log) {
        log.addAttachmentAsString("XML de Salida Generado (Final)", finalXml, "text/xml")
    }
    message.setBody(finalXml)
    
    return message
}