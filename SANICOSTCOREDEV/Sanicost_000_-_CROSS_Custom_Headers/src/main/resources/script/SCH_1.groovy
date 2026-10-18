import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def body = message.getBody(String)
    def headers = message.getHeaders()
    
    // 1. Identificación del Cliente (IP) (BETA)
    def ipMapping = ["193.146.181.2": "Dedalus", "83.39.198.106": "Costaisa"]
    def clientIp = headers.get("x-cf-true-client-ip")?.toString()?.replaceAll(/[\[\]\s]/, "") ?: ""
    message.setHeader("RequesterID", ipMapping.get(clientIp) ?: "Unknown (${clientIp})")

    // 2. Extracción de datos del HL7
    def pathMap = [
        "MessageID"       : ["MSH", "MSH.10"],
        "PatientN"        : ["PID", "PID.2", "CX.1"],
        "ActivityID"      : ["PV1", "PV1.52", "XCN.1"],
        "Center"          : ["PV1", "PV1.3", "PL.4", "HD.1"],
        "Edificio"        : ["PV1", "PV1.3", "PL.7"],
        "PlanCoberturaID" : ["IN1", "IN1.2", "CWE.1"],
        "PrestacionExtID" : ["PV1", "PV1.4"],
        "EpisodioID"      : ["PV1", "PV1.50"]
    ]

    pathMap.each { key, pathList ->
        message.setHeader(key, getValueFromPath(body, pathList) ?: "N/A")
    }
    
    return message
}

def getValueFromPath(xml, pathList) {
    try {
        def node = new XmlSlurper().parseText(xml)
        pathList.each { part -> node = node[part] }
        return node.text()
    } catch (Exception e) { return null }
}