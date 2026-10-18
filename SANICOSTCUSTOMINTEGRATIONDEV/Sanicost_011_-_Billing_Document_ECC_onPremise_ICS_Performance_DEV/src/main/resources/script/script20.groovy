import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def id = (message.getProperty("P_ActivityID") ?: "").toString()
    def r  = (message.getProperty("MAP_ACT_SC") ?: [:])[id] ?: [:]
    message.setProperty("P_UPorigen",             r.UPorigen ?: "")
    message.setProperty("P_NumeroAsistencia",     r.NumeroAsistencia ?: "")
    message.setProperty("P_FechaAsistencia",      r.FechaAsistencia ?: "")
    message.setProperty("P_Autorizacion",         r.Autorizacion ?: "")
    message.setProperty("P_GFH",                  r.GFH ?: "")
    message.setProperty("P_AsociadoID",           r.AsociadoID ?: "")
    message.setProperty("P_ServicioRealizadorID", r.ServicioRealizadorID ?: "")
    return message
}