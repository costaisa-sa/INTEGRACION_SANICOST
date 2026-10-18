/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/es-ES/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/es-ES/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
def Message processData(Message message) {
    //Body
    def body = message.getBody(String)

    // Parsear el XML
    def xml = new XmlSlurper().parseText(body)
    
    def businessPartner = xml.entry.content.properties.BusinessPartner.text()
    def bp = "No"
    
    if(businessPartner){
        bp = "Yes"
    }
    
    // Guardar como propiedad
    message.setProperty("BusinessPartnerId", bp)

    return message
}