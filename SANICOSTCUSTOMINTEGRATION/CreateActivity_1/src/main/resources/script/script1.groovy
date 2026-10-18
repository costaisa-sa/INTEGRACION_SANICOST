import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;

// Función principal que procesa el mensaje. Es el punto de entrada del script en el iFlow.
def Message processData(Message message) {

    // Obtiene el cuerpo (payload) del mensaje y lo convierte a un objeto String de Groovy.
    def body = message.getBody(java.lang.String) as String;

    // Reemplaza todas las ocurrencias del salto de línea de Windows (\r\n) por una cadena vacía.
    // El objetivo es eliminar todos los saltos de línea del cuerpo del mensaje.
	body = body.replace('\r\n', '');

    // Establece el cuerpo modificado (ya sin saltos de línea) de nuevo en el mensaje.
    message.setBody(body);

    // Devuelve el mensaje modificado para que el iFlow continúe su procesamiento.
	return message;
}