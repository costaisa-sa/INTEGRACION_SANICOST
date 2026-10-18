import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.XmlSlurper

def Message processData(Message message) {

    def body = message.getBody(java.lang.String)
    def xml  = new XmlSlurper(false, false).parseText(body)

    String tipoMensaje

    if (xml.BillableDocumentItem.size() > 0) {
        // Nodo BillableDocumentItem presente → es FACTURA
        tipoMensaje = "FACTURA"

    } else if (xml.CABECERA.TIPO_MENSAJE.text()) {
        // Nodo CABECERA/TIPO_MENSAJE presente → tipo explícito (PACIENTE, IMPRIMIR...)
        tipoMensaje = xml.CABECERA.TIPO_MENSAJE.text().trim()

    } else {
        tipoMensaje = "DESCONOCIDO"
    }

    message.setProperty("TipoMensaje", tipoMensaje)
    return message
}
