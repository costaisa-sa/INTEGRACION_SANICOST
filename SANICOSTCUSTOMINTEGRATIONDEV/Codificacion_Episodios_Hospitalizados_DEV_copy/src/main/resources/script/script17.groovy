import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

Message processData(Message message) {
    
    // Leer el payload de entrada
    def body = message.getBody(String)
    def jsonSlurper = new JsonSlurper()
    def parsed = jsonSlurper.parseText(body)
    
    // Acceder al objeto anidado
    def entity = parsed.ActivityDiagnosticEntity?.ActivityDiagnosticEntity
    
    // Manejo seguro de Principal (si viene vacío o null)
    def principalValue = null
    if (entity?.Principal && entity.Principal.toString().trim()) {
        try {
            principalValue = entity.Principal.toInteger()
        } catch (Exception e) {
            principalValue = entity.Principal // lo deja como está si no es numérico
        }
    } else {
        principalValue = "" 
    }
    
    // Construir nuevo JSON plano
    def newJson = [
        ActivityID   : entity?.ActivityID,
        Diagnostic   : entity?.Diagnostic,
        Principal    : principalValue,
        DiagnosticTxt: entity?.DiagnosticTxt
    ]
    
    // Convertir a string JSON
    def output = JsonOutput.toJson(newJson)
    message.setBody(output)
    
    return message
}
