import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.XmlSlurper
import groovy.json.JsonOutput
import javax.xml.parsers.DocumentBuilderFactory
import java.io.ByteArrayInputStream

def Message processData(Message message) {
    
    def TRUNCAR_E_CODIGO_CLIENTE = false

    def body = message.getBody(java.lang.String)
    def xml = new XmlSlurper(false, false).parseText(body)

    def codigoCliente = xml.CABECERA.CODIGO_CLIENTE.text()
    if (TRUNCAR_E_CODIGO_CLIENTE && codigoCliente.startsWith("E")) {
        codigoCliente = codigoCliente.substring(1)
    }

    // Adaptado a la estructura de ICS
    def cabecera = [
        TIPO_MENSAJE        : xml.CABECERA.TIPO_MENSAJE.text(),
        ORGANIZACION_VENTAS : xml.CABECERA.ORGANIZACION_VENTAS.text(),
        PLAN_COBERTURA      : xml.CABECERA.PLAN_COBERTURA.text(),
        NUMERO_FACTURA      : xml.CABECERA.NUMERO_FACTURA.text(),
        CODIGO_CLIENTE      : codigoCliente,
        FECHA_FACTURA       : xml.CABECERA.FECHA_FACTURA.text(),
        //PACIENTE            : xml.CABECERA.PACIENTE.text(),
        expediente          : xml.CABECERA.EXPEDIENTE.text(),
        asegurado           : xml.CABECERA.ASEGURADO.text() ?: "",
        poliza              : xml.CABECERA.POLIZA.text() ?: "",
        matricula           : xml.CABECERA.MATRICULA.text() ?: "",
        siniestro           : xml.CABECERA.SINIESTRO.text() ?: "",
        fecha_siniestro     : xml.CABECERA.FECHA_SINIESTRO.text() ?: "",
        concepto            : xml.CABECERA.CONCEPTO.text() ?: "",
        region_sanitaria    : xml.CABECERA.REGION_SANITARIA.text()
    ]

    def posiciones = []
    xml.POSICIONES.item.each { item ->
        def fechaCruda = item.fecha_asistencia.text()
        def fechaLimpia = fechaCruda.contains("T") ? fechaCruda.split("T")[0] : fechaCruda

        posiciones << [
            producto_facturable_sanicost : item.producto_facturable_sanicost.text(),
            cantidad                     : item.cantidad.text(),
            precio_neto                  : item.precio_neto.text(),
            paciente                     : item.paciente.text(),
            servicio                     : item.servicio.text(),
            edificio                     : item.edificio.text(),
            asistencia                   : item.asistencia.text(),
            
            // 3. Asignamos la fecha ya recortada
            fecha_asistencia             : fechaLimpia, 
            
            autorizacion                 : item.autorizacion.text(),
            up_origen                    : item.up_origen.text()
        ]
    }

    def jsonPayload = JsonOutput.toJson([CABECERA: cabecera, POSICIONES: posiciones])

    def soapString = """<urn:ZintSanicost xmlns:urn="urn:sap-com:document:sap:soap:functions:mc-style">
  <In><![CDATA[${jsonPayload}]]></In>
</urn:ZintSanicost>""".toString()

    def factory = DocumentBuilderFactory.newInstance()
    factory.setNamespaceAware(true)
    def doc = factory.newDocumentBuilder().parse(new ByteArrayInputStream(soapString.getBytes("UTF-8")))

    message.setBody(doc)
    return message
}