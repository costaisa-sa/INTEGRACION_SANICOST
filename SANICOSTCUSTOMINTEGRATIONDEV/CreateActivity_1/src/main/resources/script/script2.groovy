import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
//Leemos los datos de consulta de la báscula de calidad
	def body = message.getBody(String);
	def xml  = new XmlSlurper().parseText(body);

	//Creamos la base del xml
	def ROOT = new Node(null, 'Activities');
    headers = new Node(ROOT,'ActivitiesType');
	
	if(xml.content.properties.size() > 0){
		xml.content.properties.each{ node ->
			node.children().each { child ->
					new Node(headers,child.name(),child.text());
				}
			}
		message.setBody(groovy.xml.XmlUtil.serialize(ROOT));
	}else{
		message.setBody("Error: No data in select");
	}
    return message
}