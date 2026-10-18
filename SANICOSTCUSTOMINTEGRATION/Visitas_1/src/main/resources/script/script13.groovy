import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    // 1. Obtener el valor del Header
    def errorHeader = message.getHeaders().get("ErrorHISDesc")?.toString()?.trim()
    
    // Si el header no existe o está vacío, salimos
    if (!errorHeader) {
        return message
    }

    def extractedTexts = []

    try {
        // Truco: Si el header trae JSONs pegados {"a":"b"}{"c":"d"}, los separamos con comas
        // y los envolvemos en [] para que sea una lista válida de Groovy
        def formattedJson = "[" + errorHeader.replace("}{", "},{") + "]"
        def slurper = new JsonSlurper()
        def data = slurper.parseText(formattedJson)
        
        if (data instanceof List) {
            data.each { if (it.text) extractedTexts.add(it.text) }
        } else if (data.text) {
            extractedTexts.add(data.text)
        }
    } catch (Exception e) {
        // Si el JSON falla, usamos Regex para extraer el contenido de "text"
        def matcher = (errorHeader =~ /"text"\s*:\s*"([^"]*)"/)
        while (matcher.find()) {
            extractedTexts.add(matcher.group(1))
        }
    }

    // 2. Si logramos extraer algo, creamos la Property
    if (!extractedTexts.isEmpty()) {
        // Unimos los textos con un separador y eliminamos duplicados
        message.setProperty("ErrorHISDescPortal", extractedTexts.unique().join(" | "))
    } else {
        // Si no hay texto pero el header no estaba vacío, podrías querer guardar el valor original
        message.setProperty("ErrorHISDescPortal", "")
    }

    return message
}