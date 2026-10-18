import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    // ── Leer properties calculadas en pasos anteriores ───────────────
    def billingStatus     = message.getProperty("BillingStatus")     ?: "UNKNOWN"
    def tipoMensaje       = message.getProperty("TipoMensaje")       ?: "UNKNOWN"
    def referenceDocument = message.getProperty("ReferenceDocument") ?: ""

    // ── Construir el log descriptivo ─────────────────────────────────
    def billingStatusLog = "Billing R3 result: ${billingStatus} - Ref: ${referenceDocument}"

    // ── Setear los custom headers (visibles en el monitor de CPI) ────
    message.setHeader("BillingStatus",    billingStatus)
    message.setHeader("TipoMensaje",      tipoMensaje)
    message.setHeader("BillingStatusLog", billingStatusLog)

    return message
}
