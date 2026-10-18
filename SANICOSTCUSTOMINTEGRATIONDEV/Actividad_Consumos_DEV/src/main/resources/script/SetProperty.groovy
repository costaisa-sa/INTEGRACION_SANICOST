import com.sap.it.api.mapping.*
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;

def Message processData(Message message) {
//leer CeCoID
  String PropertyValue= context.getProperty('CecoID');
  PropertyValue= PropertyValue.toString();

//Marcar o no segun este informado  
      if (PropertyValue == '')
      {
          message.setProperty("CentroCosteExcluido", ''); 
      }
      else
      {
        message.setProperty("CentroCosteExcluido", 'X')
      }

}