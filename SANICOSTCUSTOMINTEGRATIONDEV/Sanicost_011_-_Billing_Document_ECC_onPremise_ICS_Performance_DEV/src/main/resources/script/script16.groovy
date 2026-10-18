import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def refDoc = message.getProperty("P_RefDocNum")

    // Lanzamos una excepción controlada. Esto detiene el flujo
    // y muestra exactamente este texto en el log de errores de CPI.
    throw new Exception("ERROR DE NEGOCIO: La factura/albarán ${refDoc} no tiene ninguna simulación asociada en la tabla GeneralInvoicesEntity. El flujo se ha detenido.")

    return message
}