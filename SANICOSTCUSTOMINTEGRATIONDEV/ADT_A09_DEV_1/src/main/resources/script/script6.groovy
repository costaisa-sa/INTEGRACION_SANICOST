import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.Base64
def Message processData(Message message) {
    //Body
    def body = message.getBody(java.lang.String);
/*To set the body, you can use the following method. Refer SCRIPT APIs document for more detail*/
    //message.setBody(body + " Body is modified");
    //Headers
   
    //Properties
    def properties = message.getProperties();
    def valor = properties.get("DataEntryXML");
    
    // Codificar el cuerpo en base64
    def base64Encoded = Base64.getEncoder().encodeToString(valor.bytes)
    
    message.setProperty("DataEntryXML", base64Encoded);
    return message;
}