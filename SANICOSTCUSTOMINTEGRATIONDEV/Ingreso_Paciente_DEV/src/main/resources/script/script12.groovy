import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    def messageLog = messageLogFactory.getMessageLog(message);
    if (messageLog != null) {
        // Esto aparecerá SI O SI en las propiedades de la cabecera en el monitor
        messageLog.addCustomHeaderProperty("DEBUG_LOG", "EJECUTADO_EXCEPTION_3")
    }
    return message;
}