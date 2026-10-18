import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def nhc = message.getProperty("NHC") as String
    def pccFlag = message.getProperty("PCCFlag") as String
    def pccMaca = message.getProperty("PCCMACA") as String
    
    if (!nhc || !pccFlag || !pccMaca) {
        return message
    }

    def booleanValue = (pccFlag.toUpperCase() == "AD") ? "true" : "false"
    def xmlPayload = ""
    
    // Cambiamos <NHC> por <DeudorID> para cumplir con la Primary Key de OData
    if (pccMaca.toUpperCase() == "PCC") {
        xmlPayload = """<DeudorSet>
            <DeudorSet>
                <DeudorID>${nhc}</DeudorID>
                <PCC>${booleanValue}</PCC>
            </DeudorSet>
        </DeudorSet>"""
    } else if (pccMaca.toUpperCase() == "MACA") {
        xmlPayload = """<DeudorSet>
            <DeudorSet>
                <DeudorID>${nhc}</DeudorID>
                <MACA>${booleanValue}</MACA>
            </DeudorSet>
        </DeudorSet>"""
    }
    
    if (xmlPayload != "") {
        message.setBody(xmlPayload.toString())
    }
    
    return message
}