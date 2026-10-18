import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap

def Message processData(Message message) {
    
    // 1. Obtenemos la instancia del log de mensajes
    def messageLog = messageLogFactory.getMessageLog(message)
    
    if (messageLog != null) {
        // 2. Creamos un adjunto en el log que será visible en el monitor
        String logText = "¡ÉXITO! Este script fue añadido en Calidad (ADT_01_DEV) y se ha transportado correctamente a Producción. Las lógicas nuevas se han aplicado."
        messageLog.addAttachmentAsString("Test_Transporte_Log", logText, "text/plain")
    }
    
    // 3. Añadimos también una property por si prefieres mirarlo en modo Trace
    message.setProperty("VersionDespliegue", "Actualizado desde Calidad")
    
    return message
}