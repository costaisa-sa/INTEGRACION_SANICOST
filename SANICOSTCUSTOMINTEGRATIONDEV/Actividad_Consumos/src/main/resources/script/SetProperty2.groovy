import com.sap.it.api.mapping.*
import com.sap.it.api.mapping.*
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
//leer PrestacionID
  String PropertyValue= context.getProperty('PrestacionID');
  PropertyValue= PropertyValue.toString();

//Marcar o no segun este informado  
  if (PropertyValue == '')
  {
      message.setProperty("TieneConvenio", ''); 
  }
  else
  {
    message.setProperty("TieneConvenio", 'X')
  }
}

