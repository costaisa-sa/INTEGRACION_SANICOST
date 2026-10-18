import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.XmlSlurper
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def Message processData(Message message) {

    // ═══════════════════════════════════════════
    // PASO 1: Leer respuesta SOAP de R3
    // ═══════════════════════════════════════════
    def body     = message.getBody(java.lang.String)
    def xml      = new XmlSlurper(false, false).parseText(body)
    def outValue = xml.Out.text().trim()

    // ═══════════════════════════════════════════
    // PASO 2: Recuperar properties previas
    // ═══════════════════════════════════════════
    def referenceDocument = message.getProperty("ReferenceDocument") ?: ""
    def id               = message.getProperty("ID")                ?: ""
    def creationDateTime = message.getProperty("CreationDateTime")   ?: ""

    // Mapa de posiciones guardado al inicio del iFlow
    def itemMapRaw = message.getProperty("RefDocItemMap") ?: "{}"
    def itemMap    = new JsonSlurper().parseText(itemMapRaw)

    // ═══════════════════════════════════════════
    // PASO 3: Función para resolver ReferenceDocumentItem
    //   Busca 6 dígitos al final de la nota, ej: "000010" → "10"
    //   Si no hay coincidencia, devuelve "1er item del mapa"
    // ═══════════════════════════════════════════
    def resolveRefDocItem = { note ->
        def matcher = (note =~ /(\d{6})\s*$/)
        if (matcher) {
            def sapKey = matcher[0][1]  // ej: "000010"
            return itemMap[sapKey] ?: sapKey
        }
        // Si no encontramos posición en la nota, devolvemos primer item del mapa
        return itemMap.values().find() ?: ""
    }

    def responseJson

    // ═══════════════════════════════════════════
    // PASO 4: OK o KO
    // ═══════════════════════════════════════════
    if (outValue.contains('|')) {

        message.setProperty("BillingStatus", "KO")
        def failedItems = []

        if (outValue.startsWith('$')) {
            // ── KO: UNA posición → "$000000001E|VF|100|nota..."
            def sinDollar    = outValue.substring(1)
            def partes       = sinDollar.split('\\|', 4)
            def header       = partes[0]                        // "000000001E"
            def typeID       = header.size() > 0 ? header[-1..-1] : ""  // "E"
            def severityCode = partes.size() > 1 ? partes[1].trim() : ""
            def note         = partes.size() > 3 ? partes[3].trim() :
                               (partes.size() > 2 ? partes[2].trim() : sinDollar)

            failedItems << [
                ReferenceDocument    : referenceDocument,
                ReferenceDocumentItem: resolveRefDocItem(note),
                TypeID               : typeID,
                SeverityCode         : severityCode,
                Note                 : note
            ]

        } else {
            // ── KO: VARIAS posiciones → una línea por error
            def errorLines = outValue.split('\n').findAll { it.trim() }

            failedItems = errorLines.collect { line ->
                def partes       = line.trim().split('\\|', 4)
                def typeID       = partes.size() > 0 ? partes[0].trim() : ""
                def severityCode = partes.size() > 1 ? partes[1].trim() : ""
                def note         = partes.size() > 3 ? partes[3].trim() :
                                   (partes.size() > 2 ? partes[2].trim() : line.trim())

                return [
                    ReferenceDocument    : referenceDocument,
                    ReferenceDocumentItem: resolveRefDocItem(note),
                    TypeID               : typeID,
                    SeverityCode         : severityCode,
                    Note                 : note
                ]
            }
        }

        responseJson = [Response: [ID: id, BillingDocumentFailed: failedItems]

    } else {
        // ── OK: Out = número de factura R3
        message.setProperty("BillingStatus", "OK")

        responseJson = [Response: [
            ID: id,
            BillingDocument: [
                ReferenceDocument: referenceDocument,
                BillingDocument  : outValue,
                CreationDateTime : creationDateTime
            ]
        ]]
    }

    // ═══════════════════════════════════════════
    // PASO 5: Serializar y preparar headers
    // ═══════════════════════════════════════════
    message.setBody(JsonOutput.toJson(responseJson))
    message.setHeader("Content-Type", "application/json")
    message.setHeader("CamelHttpMethod", "POST")

    return message
}
