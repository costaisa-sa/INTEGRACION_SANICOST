import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
 
     //escape special charater from JSON Body
     
    def body = message.getBody(java.lang.String) as String;

	body = body.replace('\r\n', '');

    message.setBody(body)

	return message;

 }