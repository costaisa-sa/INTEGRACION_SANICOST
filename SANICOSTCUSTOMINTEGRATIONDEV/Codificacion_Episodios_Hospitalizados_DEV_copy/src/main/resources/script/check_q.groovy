import com.sap.it.api.mapping.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

// --- Copia y pega esta función ---
def checkQ(String inputX) {

    // Groovy maneja 'null' de forma segura.
    // Si inputX es null, (inputX == "Q") será 'false'.
    // Si inputX es "A", (inputX == "Q") será 'false'.
    // Si inputX es "Q", (inputX == "Q") será 'true'.
    
    if (inputX == "Q") {
        return "true";
    } else {
        return "false";
    }
}
// --- Fin de la función ---