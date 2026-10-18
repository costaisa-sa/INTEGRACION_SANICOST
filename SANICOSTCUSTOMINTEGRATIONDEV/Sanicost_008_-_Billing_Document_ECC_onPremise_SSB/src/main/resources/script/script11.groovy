import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

def Message processData(Message message) {
    // Leer body en formato XML
    def body = message.getBody(java.lang.String)
    def root = new XmlParser().parseText(body)

    // Extraer ReferenceDocument y quedarnos solo con los números
    String refDoc = root.ReferenceDocument.text()
    String numRefDoc = refDoc.replaceAll("[^0-9]", "")

    // Guardar en property para usarlo en las llamadas OData
    message.setProperty("P_RefDocNum", numRefDoc)

    // Guardar el payload original para trazas
    def messageLog = messageLogFactory.getMessageLog(message)
    if(messageLog != null){
        messageLog.addAttachmentAsString("1. Payload Original Convertido", body, "text/xml")
        messageLog.addCustomHeaderProperty("RefDocument_Numerico", numRefDoc)
    }
    return message
}