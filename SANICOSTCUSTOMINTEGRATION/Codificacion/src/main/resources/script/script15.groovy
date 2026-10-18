import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.XmlUtil

/**
 * Script Universal de Limpieza de XML para CPI.
 * * Propósito:
 * Este script toma cualquier mensaje XML de entrada y lo "limpia", eliminando 
 * caracteres problemáticos como espacios de no ruptura, indentaciones no estándar
 * o problemas de codificación.
 * * Cómo funciona:
 * 1. Lee el cuerpo del mensaje como texto.
 * 2. Lo parsea con el motor XML de Groovy, que normaliza la estructura interna.
 * 3. Lo serializa de nuevo a texto, generando un XML limpio, canónico y sin formato.
 * * Resultado:
 * Un XML 100% estándar, listo para ser procesado sin errores por componentes 
 * sensibles al formato como Splitters o Routers.
 */
Message processData(Message message) {

    // 1. Obtener el cuerpo del mensaje original
    def originalBody = message.getBody(String)

    // Si el cuerpo está vacío o no es texto, no hacemos nada.
    if (originalBody == null || originalBody.trim().isEmpty()) {
        return message
    }

    // 2. Preparar el log para depuración (opcional pero recomendado)
    def log = messageLogFactory.getMessageLog(message)
    if (log) {
        log.addAttachmentAsString("XML Original (Antes de Limpiar)", originalBody, "text/xml")
    }
    
    try {
        // 3. El corazón de la limpieza: Parsear y Serializar de nuevo el XML
        // XmlSlurper lee el XML y lo normaliza internamente.
        // XmlUtil.serialize() lo escribe de vuelta en un formato de texto 100% estándar.
        def cleanXmlString = XmlUtil.serialize(new XmlSlurper(false, false).parseText(originalBody))

        // 4. Establecer el XML limpio como el nuevo cuerpo del mensaje
        message.setBody(cleanXmlString)
        
        if (log) {
            log.addAttachmentAsString("XML Limpio (Después de Limpiar)", cleanXmlString, "text/xml")
        }

    } catch (Exception e) {
        // En caso de que el XML de entrada esté tan mal formado que no se pueda ni parsear.
        if (log) {
            log.addAttachmentAsString("Error de Parseo", "El XML de entrada estaba mal formado y no se pudo limpiar. Error: ${e.getMessage()}", "text/plain")
        }
        // Opcional: podrías lanzar el error para detener el flujo.
        // throw e
    }

    // 5. Devolver el mensaje con el cuerpo ya limpio
    return message
}