import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.*

def Message processData(Message message) {
    def splitBodyStr = message.getProperty("P_OriginalSplitBody") as String
    if (splitBodyStr == null || splitBodyStr.trim().isEmpty()) {
        throw new Exception("ERROR: La property 'P_OriginalSplitBody' está vacía.")
    }
    
    def autorizacion = message.getProperty("P_Autorizacion") ?: ""
    def fechaAsistencia = message.getProperty("P_FechaAsistencia") ?: ""
    def asistencia = message.getProperty("P_NumeroAsistencia") ?: ""
    def upOrigen = message.getProperty("P_UPorigen") ?: ""
    
    // (Opcional) Mantener los antiguos si los necesitas
    def edificio = message.getProperty("P_AsociadoID") ?: ""
    def servicio = message.getProperty("P_ServicioRealizadorID") ?: ""

    def itemXml = new XmlParser().parseText(splitBodyStr)
    
    // Inyectar nuevas etiquetas
    itemXml.appendNode('autorizacion_OData', autorizacion)
    itemXml.appendNode('fechaAsistencia_OData', fechaAsistencia)
    itemXml.appendNode('asistencia_OData', asistencia)
    itemXml.appendNode('upOrigen_OData', upOrigen)
    
    itemXml.appendNode('edificio_OData', edificio)
    itemXml.appendNode('servicio_OData', servicio)

    def newBody = groovy.xml.XmlUtil.serialize(itemXml)
    message.setBody(newBody)

    return message
}