import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    
    //Por defecto es un plan de cobertura de privados/mutuas
    message.setProperty("ClasifEconomicaID", 'X');
    message.setProperty("EsCatSalut", "");
    
    // 1. Obtenemos el valor de la property 'PlanCoberturaID'
    def sPlanCoberturaID = message.getProperty("PlanCoberturaID");
    
    // 2. Marcamos la property EsCatSalut si el plan de cobertura es SCS
    if (sPlanCoberturaID == 'SCS') {
        message.setProperty("EsCatSalut", "X");
        message.setProperty("ClasifEconomicaID", 'S');
    }

    return message
}