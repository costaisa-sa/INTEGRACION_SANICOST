import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.util.XmlParser;
import groovy.xml.XmlUtil;
import java.util.HashMap;
def Message processData(Message message) {
    def bodyMessage = message.getBody(java.lang.String) as String;
    def patientProperty = message.getProperty("Patient");
    def patient = new XmlParser().parseText(patientProperty);
    
    message.setBody(patientProperty);
    return message;
}