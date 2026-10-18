import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
    
//Body 
  def xml = message.getBody(String.class);
  def completeXml = new XmlSlurper().parseText(xml);
  
//Seleccion campo Referencia  
 // def ReferenceDocument = completeXml.'BillingDocument.ReferenceDocument' ;
//  def PropertyValue= ReferenceDocument.toString();
  
// Nos vendra SCS_numeroID, Dividimos el string en el _  
 // String[] str;
 // str = PropertyValue.split('_');
 // SimulacionID = str[0];
  
  //Asignar Propiedad para llamada a CEBES
  //message.setProperty("SimulacionID", SimulacionID);
  
  // Datos Cabecera
    def ID = completeXml.'ID' ;
  message.setProperty("Header.ID", ID); 
  
  def CreationDateTime = completeXml.'CreationDateTime' ;
  message.setProperty("Header.CreationDateTime", CreationDateTime); 
  
  def ReferenceDocumentLogicalSystem = completeXml.'ReferenceDocumentLogicalSystem' ;
  message.setProperty("Header.ReferenceDocumentLogicalSystem", ReferenceDocumentLogicalSystem);
  
  ReferenceDocument = completeXml.'ReferenceDocument' ;
  message.setProperty("Header.ReferenceDocument", ReferenceDocument);
  
  return message;
}