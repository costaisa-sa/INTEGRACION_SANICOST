import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
def Message processData(Message message) {
 
     //escape special charater from JSON Body
     
    def body = message.getBody(java.lang.String) as String;

	body = body.replace('\r\n', '');
	body = body.replace('�', '');
   

    message.setBody(body)

	return message;

 }