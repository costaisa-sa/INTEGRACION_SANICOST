import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def reader = message.getBody(java.io.Reader.class)
    def xml = new XmlSlurper(false, false).parse(reader)

    def local = { n -> def x = n.name(); x.contains(':') ? x.substring(x.indexOf(':') + 1) : x }

    def sc = [:]
    xml.depthFirst().findAll { local(it) == 'Activities' }.each { p ->
        def get = { name -> (p.children().find { local(it) == name })?.text() ?: "" }
        def id = get('ActivityID')
        if (id) {
            sc[id] = [
                UPorigen             : get('UPorigenID'),
                NumeroAsistencia     : get('NumeroAsistencia'),
                FechaAsistencia      : get('FechaHoraFin'),
                Autorizacion         : get('Autorizacion'),
                GFH                  : get('GFH'),
                AsociadoID           : get('AsociadoID'),
                ServicioRealizadorID : get('ServicioRealizadorID')
            ]
        }
    }
    message.setProperty("MAP_ACT_SC", sc)

    def mpl = messageLogFactory.getMessageLog(message)
    if (mpl != null) {
        mpl.addAttachmentAsString("PreFetch_ScCache", "ActivityIDs cacheados: ${sc.size()}", "text/plain")
    }
    return message
}