import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*

def Message processData(Message message) {
    def mapHeaders = message.getHeaders()
    def mapProps = message.getProperties()
    
    // Inicializamos el indicador de error en true
    message.setProperty("hasError", "true")

    try {
        def sMessageProcessingLogID = mapHeaders.get("SAP_MessageProcessingLogID")
        def sMessageContent = mapProps.get("DataValidationJSON")
        def body = message.getBody(String.class)

        if (!body) throw new Exception("Body vacío")

        def jsonSlurper = new JsonSlurper()
        def jsonDataObject = jsonSlurper.parseText(body)
        
        jsonDataObject.MessageProcessingLogID = sMessageProcessingLogID

        // Procesar el contenido extra si existe
        if (sMessageContent != null && !sMessageContent.trim().isEmpty()) {
            def jsonMessageContentObject = jsonSlurper.parseText(sMessageContent)
            def activitiesType = jsonMessageContentObject?.Activities?.ActivitiesType
            
            if (activitiesType) {
                jsonDataObject.content = activitiesType
            } else {
                jsonDataObject.content = "No ActivitiesType"
            }
        } else {
            jsonDataObject.content = "No DataValidationJSON"
        }

        // Si todo sale bien, actualizamos el body
        message.setBody(JsonOutput.toJson(jsonDataObject))

    } catch (Exception e) {
        // En caso de CUALQUIER error, solo marcamos la propiedad
        message.setProperty("hasError", "true")
        
        // El flujo continuará con el body original o el que tuviera en ese momento
    }

    return message
}
