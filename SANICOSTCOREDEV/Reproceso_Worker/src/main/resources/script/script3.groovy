import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def payload = message.getBody(java.lang.String)
    if (payload == null || payload.trim().isEmpty()) {
        throw new RuntimeException("Payload vacío al reconstruir (guid=" + message.getProperty("guid") + ")")
    }
    def runtimeBase    = (message.getProperty("runtimeBase")    ?: "").toString()
    def soapActionBase = (message.getProperty("soapActionBase") ?: "").toString()
    def sufijo         = (message.getProperty("endpointSuffix") ?: "").toString()

    // MSH.9.2 = evento (A01, A04...) -> endpoint /cxf/ADT_<evento><sufijo>
    def evento = ""
    def mEvt = (payload =~ /<MSH\.9>[\s\S]*?<MSG\.2>([^<]+)<\/MSG\.2>/)
    if (mEvt.find()) { evento = mEvt.group(1).trim() }

    // MSH.9.3 = estructura (ADT_A01...) -> SOAPAction
    def estructura = ""
    def mEst = (payload =~ /<MSH\.9>[\s\S]*?<MSG\.3>([^<]+)<\/MSG\.3>/)
    if (mEst.find()) { estructura = mEst.group(1).trim() }

    if (evento.isEmpty()) {
        throw new RuntimeException("No se pudo extraer MSH.9.2 (evento) (guid=" + message.getProperty("guid") + ")")
    }

    def soap = '<?xml version="1.0" encoding="UTF-8"?>' +
        '<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<SOAP-ENV:Body>' + payload + '</SOAP-ENV:Body>' +
        '</SOAP-ENV:Envelope>'

    message.setBody(soap)
    message.setHeader("urlReproc",      runtimeBase + "/cxf/ADT_" + evento + sufijo)
    message.setHeader("SOAPAction",     soapActionBase + (estructura.isEmpty() ? ("ADT_" + evento) : estructura))
    message.setHeader("Content-Type",   "text/xml;charset=UTF-8")
    message.setHeader("artifactReproc", "ADT_" + evento + sufijo)
    return message
}