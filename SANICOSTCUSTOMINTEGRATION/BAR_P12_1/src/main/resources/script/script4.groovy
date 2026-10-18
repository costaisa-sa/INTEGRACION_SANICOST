import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

                // get a map of iflow properties
                def map = message.getProperties();

                // get an exception java class instance
                def ex = map.get("CamelExceptionCaught");
                if (ex!=null)
                {

              // an http adapter throws an instance of org.apache.camel.component.ahc.AhcOperationFailedException
                    if (ex.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException"))
                    {

                        // copy the http error response to the message body
                        message.setBody(ex.getResponseBody());

                    }
                }

    return message;
}