import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
    
//Body 
  def xml = message.getBody(String.class);
  def completeXml= new XmlSlurper().parseText(xml);   
  
//Seleccion campo Referencia  
  def ReferenceDocument = completeXml.'ReferenceDocument' ;
  def PropertyValue= ReferenceDocument.toString();
  
// Nos vendra SCS_numeroID, Dividimos el string en el _  
  String[] str;
  str = PropertyValue.split('_');
  SimulacionID = str[1];
  
  message.setProperty("SimulacionID", SimulacionID);
  
  return message;
}