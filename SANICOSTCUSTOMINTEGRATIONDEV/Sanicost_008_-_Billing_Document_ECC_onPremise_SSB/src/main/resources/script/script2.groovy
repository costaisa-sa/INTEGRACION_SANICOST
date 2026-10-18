import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.XmlSlurper
import groovy.json.JsonOutput
import javax.xml.parsers.DocumentBuilderFactory
import java.io.ByteArrayInputStream

def Message processData(Message message) {
    
    // ── CONFIGURACIÓN ─────────────────────────────────────────────
    def TRUNCAR_E_CODIGO_CLIENTE = false  // true = quita la "E" del inicio; false = deja el valor tal cual
    // ──────────────────────────────────────────────────────────────

    def body = message.getBody(java.lang.String)
    def xml = new XmlSlurper(false, false).parseText(body)

    def codigoCliente = xml.CABECERA.CODIGO_CLIENTE.text()
    if (TRUNCAR_E_CODIGO_CLIENTE && codigoCliente.startsWith("E")) {
        codigoCliente = codigoCliente.substring(1)
    }

    def cabecera = [
        TIPO_MENSAJE        : xml.CABECERA.TIPO_MENSAJE.text(),
        ORGANIZACION_VENTAS : xml.CABECERA.ORGANIZACION_VENTAS.text(),  // NUEVO
        PLAN_COBERTURA      : xml.CABECERA.PLAN_COBERTURA.text(),        // MOVIDO aquí desde posiciones
        NUMERO_FACTURA      : xml.CABECERA.NUMERO_FACTURA.text(),
        CODIGO_CLIENTE      : codigoCliente,
        FECHA_FACTURA       : xml.CABECERA.FECHA_FACTURA.text(),
        PACIENTE            : xml.CABECERA.PACIENTE.text()               // NUEVO
    ]

    def posiciones = []
    xml.POSICIONES.item.each { item ->
        posiciones << [
            producto_facturable_sanicost : item.producto_facturable_sanicost.text(),
            cantidad                     : item.cantidad.text(),
            precio_neto                  : item.precio_neto.text(),
            // PLAN_COBERTURA ya no va aquí
            paciente : item.paciente.text(),   // NUEVO (vacío por ahora)
            servicio : item.servicio.text(),   // NUEVO (vacío por ahora)
            edificio : item.edificio.text()    // NUEVO (vacío por ahora)
        ]
    }

    def jsonPayload = JsonOutput.toJson([CABECERA: cabecera, POSICIONES: posiciones])

    def soapString = """<ns0:ZSC_DEMO_FACT xmlns:ns0="urn:sap-com:document:sap:rfc:functions">
  <IN><![CDATA[${jsonPayload}]]></IN>
</ns0:ZSC_DEMO_FACT>""".toString()

    def factory = DocumentBuilderFactory.newInstance()
    factory.setNamespaceAware(true)
    def doc = factory.newDocumentBuilder().parse(new ByteArrayInputStream(soapString.getBytes("UTF-8")))

    message.setBody(doc)
    return message
}