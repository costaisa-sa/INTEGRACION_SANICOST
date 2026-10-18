import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
    
//Body 
  def xml = message.getBody(String.class);
  def completeXml= new XmlSlurper().parseText(xml);  

//seleccionar items a sumar
def result = completeXml.BillableDocumentItems.'**'.findAll{ node-> node.name() == 'ConditionRateValue' }*.text();

//Sumar items
def total = result.sum { it.toBigDecimal() } ;

//Asignar a propiedad
  message.setProperty("ImporteTotal", total);
  
  return message;
}