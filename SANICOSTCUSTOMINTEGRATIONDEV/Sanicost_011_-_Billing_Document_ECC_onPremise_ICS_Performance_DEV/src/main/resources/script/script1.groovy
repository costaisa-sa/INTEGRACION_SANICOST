import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil

def Message processData(Message message) {
    def body = message.getBody(java.lang.String)
    def xml = new XmlSlurper(false, false).parseText(body)

    xml.BillableDocumentItem.each { item ->
        def pmp0 = item.PricingElement.find { pe ->
            pe.ConditionType.text() == 'PMP0'
        }
        if (pmp0) {
            item.appendNode { PMP0Price(pmp0.ConditionRateValue.text()) }
        } else {
            item.appendNode { PMP0Price('') }
        }
    }

    message.setBody(XmlUtil.serialize(xml))
    return message
}
