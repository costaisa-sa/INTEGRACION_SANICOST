import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
       def body = message.getBody();
       def root = new XmlParser().parse(body)
        root.A[0].value = "Groovy was here."
       message.setBody(root);
       return message;
}