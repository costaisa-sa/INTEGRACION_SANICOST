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
    //def referenceDocument = message.getProperty("ReferenceDocument") ?: ""
    def referenceDocument = message.getProperty("P_RefDocNum") ?: ""
    def id               = message.getProperty("ID")                ?: ""
    def creationDateTime = message.getProperty("CreationDateTime")   ?: ""
    def itemMapRaw       = message.getProperty("RefDocItemMap")      ?: "{}"
    def itemMap          = new JsonSlurper().parseText(itemMapRaw)

    // ═══════════════════════════════════════════
    // PASO 3: Resolver ReferenceDocumentItem
    //   Busca 6 dígitos al final del texto de nota (ej: "000010" → "10")
    //   Si no hay, usa el primer item del mapa
    // ═══════════════════════════════════════════
    def resolveRefDocItem = { note ->
        def matcher = (note =~ /(\d{6})\s*$/)
        if (matcher) {
            def sapKey = matcher[0][1]            // ej: "000010"
            return itemMap[sapKey] ?: sapKey
        }
        return itemMap.values().find() ?: ""      // fallback: primer item
    }

    def responseJson
    
    def errorLines          = new JsonSlurper().parseText(outValue);
    def hasError = errorLines.RETURN.any { it.TYPE == "E" }
    
    if (hasError) {

        // ── CASO KO ─────────────────────────────────────────────────
        message.setProperty("BillingStatus", "KO")

        def allItems = []

        allItems = errorLines.RETURN.collect { entry ->
            [
                TypeID      : entry.TYPE,
                SeverityCode: entry.NUMBER,
                Note        : entry.MESSAGE
            ]
        }
        
        // BillingDocumentFailed = objeto plano (NO array)
        // Item = array con todos los errores (uno por posición fallida)
        responseJson = [
            Response: [
                ID: id,
                BillingDocumentFailed: [
                    ReferenceDocument    : referenceDocument,
                    ReferenceDocumentItem: 0,
                    Item                 : allItems
                ]
            ]
        ]

    } else {
        // ── CASO OK ─────────────────────────────────────────────────
        message.setProperty("BillingStatus", "OK")
        
        def successRecord = errorLines.RETURN.find { it.TYPE == "S" && it.ID == "VF" && it.NUMBER == "311" }
        def BillingDocument = successRecord?.MESSAGE_V1
        
        responseJson = [
            Response: [
                ID: id,
                BillingDocument: [
                    ReferenceDocument: referenceDocument,
                    BillingDocument  : BillingDocument,
                    CreationDateTime : creationDateTime
                ]
            ]
        ]
    }

    // ═══════════════════════════════════════════
    // PASO 4: Serializar y preparar headers
    // ═══════════════════════════════════════════
    message.setBody(JsonOutput.toJson(responseJson))
    message.setHeader("Content-Type", "application/json")
    message.setHeader("CamelHttpMethod", "POST")

    return message
}
