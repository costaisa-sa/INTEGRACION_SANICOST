import com.sap.it.api.mapping.MappingContext

def String getAndTruncateError(String input, MappingContext context) {
    // 1. Intentar obtener la propiedad
    def propValue = context.getProperty("ErrorHISDescPortal")
    
    // 2. Si es nula o contiene el nombre de la variable (comportamiento de CPI)
    if (propValue == null || propValue.toString().trim() == "ErrorHISDescPortal") {
        return ""
    }
    
    String text = propValue.toString().trim()
    
    // 3. Si está vacía
    if (text.isEmpty()) {
        return ""
    }
    
    // 4. Truncar a 35 caracteres
    return (text.length() > 35) ? text.substring(0, 35) : text
}