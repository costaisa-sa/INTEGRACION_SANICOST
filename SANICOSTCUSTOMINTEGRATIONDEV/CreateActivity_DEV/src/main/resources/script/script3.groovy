import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

Message processData(Message message) {

    // Obtener body como String
    def body = message.getBody(String)

    // Parsear XML
    def xml = new XmlSlurper(false, false).parseText(body)

    // Buscar el nodo FechaAutorizacion (si existe)
    def fechaAut = xml.'**'.find { it.name() == 'FechaAutorizacion' }

    if (fechaAut) {
        // Verificar si está vacío o solo tiene espacios
        def valor = fechaAut.text()?.trim()
        if (!valor) {
            // Si está vacío, eliminar el nodo
            fechaAut.replaceNode {}
        }
    }

    // Convertir el XML de nuevo a String
    def output = XmlUtil.serialize(xml)

    // Establecer nuevo body
    message.setBody(output)

    return message
}
