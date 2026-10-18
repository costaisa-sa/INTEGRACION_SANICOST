import com.sap.it.api.mapping.MappingContext

def String getAndTruncateError(MappingContext context) {
    // 1. Intentar obtener la propiedad
    def propValue = context.getProperty("ErrorHISDescPortal")
    
    // 2. Si es null, o el valor es literalmente el nombre de la propiedad (fallback de CPI)
    // devolvemos cadena vacía
    if (propValue == null || propValue.toString() == "ErrorHISDescPortal") {
        return ""
    }
    
    String text = propValue.toString().trim()
    
    // 3. Si está vacía después del trim
    if (text.isEmpty()) {
        return ""
    }
    
    // 4. Truncar a 35 si es necesario
    return (text.length() > 35) ? text.substring(0, 35) : text
}