import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    // 1. Recuperamos la posición actual en la que está iterando el Splitter
    def refItemStr = message.getProperty("P_RefItem") as String
    int currentPosition = refItemStr ? refItemStr.toInteger() : 0

    // 2. Leemos el body JSON devuelto por la llamada OData HTTP
    def body = message.getBody(java.lang.String)
    def jsonParser = new JsonSlurper()
    def jsonResponse = jsonParser.parseText(body)

    // 3. Buscamos el ActivityID específico que coincide con nuestra Position
    String targetActivityID = ""
    
    // Navegamos por el JSON de forma segura
    if (jsonResponse?.d?.results?.size() > 0) {
        def activities = jsonResponse.d.results[0].Activities?.results
        if (activities) {
            // Buscamos dentro del array el objeto donde la Position sea igual a la actual
            def matchedActivity = activities.find { it.Position as Integer == currentPosition }
            if (matchedActivity) {
                targetActivityID = matchedActivity.ActivityID
            }
        }
    }

    // 4. Guardamos el ActivityID correcto en la property para la siguiente llamada
    message.setProperty("P_ActivityID", targetActivityID)

    // 5. Dejamos una traza en el log para verificar que coge el correcto
    def messageLog = messageLogFactory.getMessageLog(message)
    if(messageLog != null){
        messageLog.addAttachmentAsString("ActivityID Seleccionado (Pos: ${currentPosition})", 
        "Position buscada: ${currentPosition}\nActivityID encontrado: ${targetActivityID}", "text/plain")
    }

    return message
}