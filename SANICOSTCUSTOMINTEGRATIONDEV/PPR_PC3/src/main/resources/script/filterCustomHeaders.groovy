import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null) {
        // Lee la variable NHC que creaste en tu Content Modifier
        def nhc = message.getProperty("NHC")
        
        if (nhc != null && nhc.toString().trim() != "") {
            // Lo añade al Message Processing Log para que sea buscable
            messageLog.addCustomHeaderProperty("NHC_Paciente", nhc.toString())
        }
    }
    return message
}