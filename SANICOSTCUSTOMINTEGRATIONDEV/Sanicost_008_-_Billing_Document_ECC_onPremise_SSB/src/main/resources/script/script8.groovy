import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.XmlSlurper
import groovy.json.JsonOutput

def Message processData(Message message) {

    def body = message.getBody(java.lang.String)
    def xml  = new XmlSlurper(false, false).parseText(body)

    // Construir mapa: clave = posición SAP en 6 dígitos (ej: "000010")
    //                 valor = ReferenceDocumentItem original (ej: "10")
    // Así cuando R3 nos diga "in transaction 000010" sabemos qué item es
    def itemMap = [:]
    xml.BillableDocumentItem.each { item ->
        def refDocItem = item.ReferenceDocumentItem.text()?.trim()
        if (refDocItem) {
            try {
                // SAP formatea como 6 dígitos: 10 → "000010"
                def sapKey = String.format("%06d", Integer.parseInt(refDocItem))
                itemMap[sapKey] = refDocItem
            } catch (Exception e) {
                // Si no es numérico lo guardamos tal cual
                itemMap[refDocItem] = refDocItem
            }
        }
    }

    message.setProperty("RefDocItemMap", JsonOutput.toJson(itemMap))
    return message
}
