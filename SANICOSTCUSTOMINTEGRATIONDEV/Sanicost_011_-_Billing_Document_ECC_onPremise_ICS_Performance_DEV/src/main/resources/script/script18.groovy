import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def ids = message.getProperty("LIST_ACTIVITYIDS") ?: []
    int chunkSize = 100                                   // ~100 IDs por URL (seguro)

    def sb = new StringBuilder("<chunks>")
    ids.collate(chunkSize).each { group ->
        def f = group.collect { "ActivityID eq '${it}'" }.join(" or ")
        sb.append("<c>").append(f.replace("&", "&amp;").replace("<", "&lt;")).append("</c>")
    }
    sb.append("</chunks>")
    message.setBody(sb.toString())

    // Traza para validar en Fase 1
    def mpl = messageLogFactory.getMessageLog(message)
    if (mpl != null) {
        int nChunks = (int) Math.ceil(ids.size() / (double) chunkSize)
        mpl.addAttachmentAsString("PreFetch_IdChunks",
            "Total IDs: ${ids.size()} - Chunks generados: ${nChunks} (tam. ${chunkSize})", "text/plain")
    }
    return message
}