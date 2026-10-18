import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.XmlUtil
import java.util.HashMap

def Message processData(Message message) {
    // 1. Obtener el cuerpo del mensaje (payload) como un String
    def body = message.getBody(String.class)
    
    // 2. Crear una instancia de XmlParser para leer el XML
    def parser = new XmlParser()
    def barP12 = parser.parseText(body)

    // 3. Encontrar todos los nodos PR1 dentro de BAR_P12.PROCEDURE
    def procedures = barP12.'BAR_P12.PROCEDURE'.PR1

    // 4. Inicializar la variable con el valor por defecto
    def valorQuirurgico = ""

    // 5. Buscar el procedimiento quirúrgico
    // Usamos 'find' para localizar el primer nodo PR1 que cumpla la condición.
    def quirurgicoProcedure = procedures.find { pr1 ->
        // Se comprueba que el nodo PR1.6 exista y que su valor de texto sea 'X'
        !pr1.'PR1.6'.isEmpty() && pr1.'PR1.6'.text() == 'X'
    }

    // 6. Si se encontró el procedimiento, intentar obtener el valor
    if (quirurgicoProcedure) {
        // Se extrae el valor de PR1.18.CWE.1 usando GPath.
        // Si no encuentra el nodo, .text() devuelve un string vacío, que es el
        // comportamiento deseado.
        valorQuirurgico = quirurgicoProcedure.'PR1.18'.'CWE.1'.text()
    }

    // 7. Establecer la propiedad incondicionalmente.
    // Tendrá el valor encontrado o el valor por defecto "".
    message.setProperty("Quirurgico", valorQuirurgico)

    // 8. Devolver el mensaje sin modificar su cuerpo.
    return message
}