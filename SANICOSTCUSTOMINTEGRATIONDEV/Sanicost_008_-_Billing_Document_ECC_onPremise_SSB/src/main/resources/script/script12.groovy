import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

def Message processData(Message message) {
    // Obtenemos el mapa de Headers del mensaje
    def headers = message.getHeaders()
    
    // Leemos el payload del item original (este lo mantenemos como property)
    def splitBody = message.getProperty("P_OriginalSplitBody")
    
    // AHORA EXTRAEMOS EDIFICIO Y SERVICIO DE LOS HEADERS
    def edificio = headers.get("P_AsociadoID") ?: "0"
    def servicio = headers.get("P_ServicioRealizadorID") ?: "00000000"

    // Modificamos el XML del item para añadir estos nuevos nodos
    def itemXml = new XmlParser().parseText(splitBody)
    itemXml.appendNode('edificio', edificio)
    itemXml.appendNode('servicio', servicio)

    def newBody = groovy.xml.XmlUtil.serialize(itemXml)
    message.setBody(newBody)

    // Trazabilidad individual
    def activityId = message.getProperty("P_ActivityID")
    def messageLog = messageLogFactory.getMessageLog(message)
    if(messageLog != null){
        messageLog.addAttachmentAsString("Log Posicion " + message.getProperty("P_RefItem"), 
        "ActivityID Recuperado: ${activityId}\nEdificio (Header): ${edificio}\nServicio (Header): ${servicio}\nXML Final: \n${newBody}", 
        "text/plain")
    }
    return message
}