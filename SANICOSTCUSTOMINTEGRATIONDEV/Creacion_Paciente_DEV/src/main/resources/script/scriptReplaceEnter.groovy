import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    def observaciones = message.getProperty("Observaciones");
    
    if (observaciones != null) {
        // En lugar de "\n" ponemos "\\n" para que en el JSON quede escrito literalmente \n
        observaciones = observaciones.replace("\\.brC", "\\n");
        observaciones = observaciones.replace("\\.br\\", "\\n"); 
		
		// 2. Limpiar etiquetas de resaltado (H y N)
        observaciones = observaciones.replace("\\H\\", "");
        observaciones = observaciones.replace("\\N\\", "");
        
        // 3. Traducir separadores HL7 a sus caracteres reales
        observaciones = observaciones.replace("\\F\\", "|");
        observaciones = observaciones.replace("\\S\\", "^");
        observaciones = observaciones.replace("\\T\\", "&");
        observaciones = observaciones.replace("\\R\\", "~");
        
        // 4. Proteger JSON: Escapar cualquier barra invertida literal (HL7 \E\) o suelta
        observaciones = observaciones.replace("\\E\\", "\\\\");
        
        // 5. Proteger JSON: Escapar comillas dobles que vengan en el texto libre
        observaciones = observaciones.replace("\"", "\\\"");
        
        message.setProperty("Observaciones", observaciones);
    }
    
    return message;
}