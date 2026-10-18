import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

Message processData(Message message) {
    // Obtener body como String
    def body = message.getBody(String)

    // Parsear XML
    def xml = new XmlSlurper(false, false).parseText(body)

    // Lista de campos de fecha que dan error si viajan vacíos
    def nodosALimpiar = ['FechaHoraInicio', 'FechaHoraFin', 'FechaAutorizacion']

    // Iterar sobre cada nombre de nodo de la lista
    nodosALimpiar.each { nombreNodo ->
        // Buscar el nodo en el XML
        def nodoEncontrado = xml.'**'.find { it.name() == nombreNodo }
        
        if (nodoEncontrado) {
            // Verificar si está vacío o solo tiene espacios
            def valor = nodoEncontrado.text()?.trim()
            if (!valor) {
                // Si está vacío, eliminar el nodo
                nodoEncontrado.replaceNode {}
            }
        }
    }

    // Convertir el XML de nuevo a String (sin la declaración XML para evitar problemas con OData, si aplica)
    def output = XmlUtil.serialize(xml)

    // Establecer nuevo body
    message.setBody(output)

    return message
}