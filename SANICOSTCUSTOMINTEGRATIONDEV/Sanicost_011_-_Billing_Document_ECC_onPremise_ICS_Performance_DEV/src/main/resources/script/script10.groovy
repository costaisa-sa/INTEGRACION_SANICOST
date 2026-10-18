import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    // 1. Obtener el cuerpo del mensaje
    def body = message.getBody(String)
    def jsonSlurper = new JsonSlurper()
    def list = jsonSlurper.parseText(body)

    // 2. Extraer ProfitCenter (o asignar vacío si no existe)
    def profitCenter = list.BillableDocumentItem[0]?.ProfitCenter ?: ""
    
    // 3. Inicializar valores por defecto (si no hay pipe, se quedarán así)
    def servicioRealizadorID = ""
    def edificio = ""

    // 4. Lógica de división si existe el pipe
    if (profitCenter.contains("|")) {
        // Usamos -1 para que "VALOR|" genere un segundo elemento vacío
        def parts = profitCenter.split("\\|", -1)
        
        servicioRealizadorID = parts[0]?.trim() ?: ""
        edificio = (parts.length > 1) ? parts[1]?.trim() : ""
    }

    // 5. Guardar siempre las propiedades
    message.setProperty("ServicioRealizadorID", servicioRealizadorID)
    message.setProperty("Edificio", edificio)

    return message
}