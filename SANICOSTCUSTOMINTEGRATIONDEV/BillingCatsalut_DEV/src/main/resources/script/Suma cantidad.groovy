import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
    
//Body 
  def xml = message.getBody(String.class);
  def completeXml= new XmlSlurper().parseText(xml);  
  def total = 0;
//seleccionar items a sumar
def result = completeXml.'**'.findAll{ node-> node.name() == 'Cantidad' }*.text();

//Si vienen en blanco da error
//def total = result.sum { it.toBigDecimal() } ;
try 
{
  total = result.sum { it.toBigDecimal() } ;
}
catch (NumberFormatException e)  
{
  total = 1; 
}

//Asignar a propiedad
  message.setProperty("CantidadTotal", total);
  
  return message;
}