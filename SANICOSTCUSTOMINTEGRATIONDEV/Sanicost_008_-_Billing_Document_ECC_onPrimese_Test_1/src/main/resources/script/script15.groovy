import com.sap.gateway.ip.core.customdev.util.Message
//import groovy.xml.*

def Message processData(Message message) {
    // 1. Recuperamos el fragmento usando el nombre correcto de tu property
    def splitBodyStr = message.getProperty("P_OriginalSplitBody") as String
    
    // SALVAVIDAS: Comprobamos si la property existe
    if (splitBodyStr == null || splitBodyStr.trim().isEmpty()) {
        throw new Exception("ERROR CRÍTICO: La property 'P_OriginalSplitBody' está vacía o no existe.")
    }
    
    // 2. Recuperamos los datos de OData
    def edificio = message.getProperty("P_AsociadoID") ?: ""
    def servicio = message.getProperty("P_ServicioRealizadorID") ?: ""
    def gfh = message.getProperty("P_GFH") ?: ""

    // 3. Parseamos el fragmento
    def itemXml = new XmlParser().parseText(splitBodyStr)
    
    // 4. Inyectamos las etiquetas DENTRO de <BillableDocumentItem>
    itemXml.appendNode('edificio_OData', edificio)
    itemXml.appendNode('servicio_OData', servicio)
    itemXml.appendNode('gfh_OData', gfh)

    // 5. Guardamos y seteamos
    def newBody = groovy.xml.XmlUtil.serialize(itemXml)
    message.setBody(newBody)

    return message
}