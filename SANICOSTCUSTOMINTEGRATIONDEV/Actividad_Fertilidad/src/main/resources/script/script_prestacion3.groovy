import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

map = message.getProperties();

def evento  = map.get("ZTV.10");
def tecnica = map.get("PV2.24");
def tipo_ia = map.get("ZTV.9");

if (evento == "E01")
{
  if( (tecnica == "IAC")                    || 
      (tecnica == "IAD" && tipo_ia == "I" ) ||
      (tecnica == "IAD" && tipo_ia == "P") )
  {
    message.setProperty("OBX.3.CE.4","33105");
  } 
}
    
if (evento == "E11")
{
  if (tecnica == "IAC")                    {message.setProperty("OBX.3.CE.4","33113");} 
  if (tecnica == "IAD" && tipo_ia == "I" ) {message.setProperty("OBX.3.CE.4","33112");}
  if (tecnica == "IAD" && tipo_ia == "P" ) {message.setProperty("OBX.3.CE.4","33111");}

}
    return message;
}