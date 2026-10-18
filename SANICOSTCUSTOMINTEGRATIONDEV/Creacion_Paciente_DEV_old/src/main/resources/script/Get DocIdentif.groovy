import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.XmlUtil;
//import groovy.xml.XmlSlurper;

def Message processData(Message message) {

    // Extraer el cuerpo del mensaje
    def xml = message.getBody(String.class);
    
    // Parsear el XML ignorando namespaces para mayor flexibilidad
    def completeXml = new XmlSlurper().parseText(xml);
    
    def cx_str;
    def dln_str;
    def zpi_str;
    def docIdentif;

    // Búsqueda de campos mediante 'deep scan' (**)
    def cx = completeXml.'**'.find{ node -> node.name() == 'CX.1' }*.text();
    def dln = completeXml.'**'.find{ node -> node.name() == 'DLN.1' }*.text();

    // CAMBIO SOLICITADO: Acceso a /ns0:ADT_A05/ns0:PID/ns0:PID.15/ns0:CWE.2
    // Buscamos el nodo PID.15 y de ahí extraemos el valor de su hijo CWE.2
    def zpi = completeXml.'**'.find{ node -> node.name() == 'PID.15' }.'CWE.2'*.text();

    // Normalización: Si no existe el nodo o está vacío, asignamos cadena vacía
    cx_str  = (cx && cx[0]) ? cx[0] : ""
    dln_str = (dln && dln[0]) ? dln[0] : ""
    zpi_str = (zpi && zpi[0]) ? zpi[0] : ""

    // Lógica de prioridad (Petición Luis Montero)
    // 1º DLN.1 (DNI)
    // 2º CWE.2 dentro de PID.15 (NIE)
    // Se ignora CX.1 por requerimiento
    
    if (dln_str != "") {
        docIdentif = dln_str
    } else if (zpi_str != "") {
        docIdentif = zpi_str
    } else {
        docIdentif = ""
    }

    // Guardar el resultado en la propiedad de intercambio
    message.setProperty("DocIdentif", docIdentif)  

    return message
}