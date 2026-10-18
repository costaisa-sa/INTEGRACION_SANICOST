import com.sap.it.api.mapping.*;
import java.util.HashMap;

/**
 * Función robusta para chequear "Q".
 * 1. Primero comprueba si la entrada es nula (nodo no existe).
 * 2. Si no es nula, quita espacios (trim) y la compara con "Q".
 */
def String checkQ(String inputX) {

    // --- 1. Comprobación de Nulo ---
    // Si el nodo CWE.1 no existe, inputX será 'null'.
    // Esta es la comprobación más importante para que "no se raye".
    if (inputX == null) {
        return "false";
    }

    // --- 2. Comprobación de Valor ---
    // Si llegamos aquí, sabemos que inputX no es nulo.
    // Ahora podemos usar .trim() de forma segura para eliminar
    // espacios en blanco y manejar el caso de <CWE.1></CWE.1> (vacío).
    
    // .trim() convertirá un string vacío "" en ""
    // .trim() convertirá " Q " en "Q"
    
    if (inputX.trim() == "Q") {
        return "true";
    } else {
        // Esto captura:
        // - El caso vacío: ""
        // - El caso "F" (del XML)
        // - Cualquier otro valor
        return "false";
    }
}