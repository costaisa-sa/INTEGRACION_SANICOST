import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    
    // Leer el body como String
    def body = message.getBody(String) 
    
    // Parsear XML ignorando namespaces
    def xml = new XmlSlurper(false, false).parseText(body)
    
    // Buscar el nodo PlanCobertura (IN1.2/CWE.1)
    def planCobertura = xml.'**'.find { it.name() == 'IN1.2' }?.'CWE.1'?.text()
    
    // Si no lo encuentra, setear vacío
    if (!planCobertura) {
        planCobertura = ""
    }
    
    // Guardar como property en el Message
    message.setProperty("PlanCoberturaID", planCobertura)
    
    return message
}
