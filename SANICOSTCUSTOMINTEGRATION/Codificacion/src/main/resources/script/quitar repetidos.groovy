import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
    
    //Body 
    def body = message.getBody(String.class);
    def completeXml= new XmlSlurper().parseText(body);
    
//guardar todos los procesos
    def result = completeXml.'**'.findAll{ node-> node.name() == 'Process' }*.text();

// dejamos 1 de cada, , en Rsult hay un codigo proceso de cada 
    result = result.unique();

def allPrincipal = completeXml.'**'.findAll{it->it.name() == 'ActivityProcessSetType'}*.Process*.each{node-> 
        if (result.containsAll(node) == true) 
        { 
            result.removeElement(node) //Lo borramos, para que no lo vuelva a encontrar
            
        } 
        else 
        { 
            node.replaceBody("") //No lo he encontrado, es repetido. Guardamos en blanco.
            
        }
        
    };
    
    message.setBody(groovy.xml.XmlUtil.serialize( completeXml));
       
    return message;
}