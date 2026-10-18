/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/es-ES/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/es-ES/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
def Message processData(Message message) {
   def body = message.getBody(String)
   def messageLog = messageLogFactory.getMessageLog(message);

    // Mapa de rutas: clave = nombre descriptivo, valor = lista de nodos
    def pathMap = [
        "MessageID" : ["MSH", "MSH.10"],
        "PatientN": ["PID", "PID.2", "CX.1"],
        "ActivityID": ["PV1", "PV1.52", "XCN.1"]
    ]
    // Extraer valores y guardarlos como propiedades del mensaje
    pathMap.each { key, pathList ->
        def value = getValueFromPath(body, pathList)
        message.setProperty(key, value)    
        if (messageLog != null) {
            messageLog.addCustomHeaderProperty(key, value ?: "N/A")
        }
    }
    
    return message
}

// Función genérica para navegar por el XML
def getValueFromPath(xml, pathList) {
    def node = new XmlSlurper().parseText(xml)
    pathList.each { part ->
        node = node[part]
    }
    return node.text()

}