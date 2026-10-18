import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.*;
import groovy.util.*;
import groovy.xml.*;
import org.codehaus.*;
import java.util.HashMap;
def Message processData(Message message) {
//Properties
	def pMap = message.getProperties();
	def CamaAntDeudor1 = pMap.get("CamaAntDeudor1");
	def CamaActualDeudor1 = pMap.get("CamaActualDeudor1");
    
//Body
    def body_xml= message.getBody(java.lang.String) as String;
	def root = new XmlParser().parseText(body_xml);
	
    root.appendNode("CamaAntDeudor", [:], CamaAntDeudor1);
	root.appendNode("CamaActualDeudor", [:], CamaActualDeudor1);
	
	def xml = XmlUtil.serialize(root).replaceFirst("<\\?xml version=\"1.0\".*\\?>", "");
	message.setBody(xml); 

return message;
}