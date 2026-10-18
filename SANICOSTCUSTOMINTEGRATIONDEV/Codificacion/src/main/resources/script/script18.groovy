import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.XmlUtil
import java.util.HashMap

/*
 * =====================================================================================
 * SCRIPT PARA SAP CPI - PROCESAMIENTO DE MENSAJES HL7 v2 XML
 * =====================================================================================
 * OBJETIVO:
 * Este script asegura que cada nodo <PR1> en un mensaje HL7 XML contenga una
 * estructura <PR1.18><CWE.1></CWE.1> si no viene informado y así no dar problemas en el mapeo.
 * =====================================================================================
 */
def Message processData(Message message) {
    // 1. Obtener el cuerpo del mensaje (payload) como un String
    def body = message.getBody(String.class)
    
    // 2. Crear una instancia de XmlParser. Es 'namespace-aware' por defecto.
    def parser = new XmlParser()
    def barP12 = parser.parseText(body)

    // 3. Encontrar todos los nodos PR1 dentro de BAR_P12.PROCEDURE
    def procedures = barP12.'BAR_P12.PROCEDURE'.PR1

    // 4. Iterar sobre cada nodo PR1 encontrado
    procedures.each { pr1 ->
        // 5. Buscar el nodo hijo 'PR1.18'. Esto devuelve una lista de nodos.
        def pr1_18_nodes = pr1.'PR1.18'

        // 6. Lógica de verificación y creación de nodos.
        // Caso A: El nodo PR1.18 no existe en absoluto.
        if (pr1_18_nodes.isEmpty()) {
            // Creamos el nodo PR1.18 y, encadenado, su hijo CWE.1 vacío.
            pr1.appendNode('PR1.18').appendNode('CWE.1', '')
        } 
        // Caso B: El nodo PR1.18 ya existe.
        else {
            // Trabajamos con el primer nodo encontrado (debería haber solo uno).
            def pr1_18 = pr1_18_nodes[0]
            // Comprobamos si DENTRO de este PR1.18 falta el hijo CWE.1.
            if (pr1_18.'CWE.1'.isEmpty()) {
                // Si falta, lo añadimos vacío.
                pr1_18.appendNode('CWE.1', '')
            }
        }
    }

    // 7. Convertir el objeto XML modificado de nuevo a un String formateado
    def modifiedXml = XmlUtil.serialize(barP12)

    // 8. Establecer el String XML modificado como el nuevo cuerpo del mensaje
    message.setBody(modifiedXml)

    // 9. Devolver el mensaje modificado para que siga el flujo del iFlow
    return message
}

