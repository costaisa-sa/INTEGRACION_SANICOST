import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    // Leer el body actual (donde viene el error)
    def errorBody = message.getBody(String) as String

    // Setear headers
    message.setHeader("ErrorHISDesc", errorBody)
    message.setHeader("ErrorHIS", "X")

    return message
}
