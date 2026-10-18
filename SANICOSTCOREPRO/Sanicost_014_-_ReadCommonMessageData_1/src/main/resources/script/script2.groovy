import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    def properties = message.getProperties();
    def sFechaHoraInicioCurrent = properties.get("FechaHoraInicioCurrent");
    
    def sFormattedDate = "";
    if (sFechaHoraInicioCurrent) {
        sFormattedDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss.sss", sFechaHoraInicioCurrent).format("yyyy-MM-dd'T'HH:mm:ss");
    } 
    
    message.setProperty("FechaHoraInicioCurrent", sFormattedDate);
    
    return message;
}