/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/es-ES/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/es-ES/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
def Message processData(Message message) {
    def body = message.getBody(String)
    def root = new XmlSlurper().parseText(body)

    def PatientN = null
    def ActivityID = null
    def MessageID = null
    
    //Buscamos Paciente en PID.2 o PID.3    
    PatientN = root.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.PID.'PID.2'.'CX.1'.text();
    if(!PatientN){
        PatientN = root.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.PID.'PID.3'.'CX.1'.text();
    }

    // Buscamos el vaor de MessageID
    MessageID = root.MSH.'MSH.10'.text();
   
    // Buscamos el valor de ActivityID
    ActivityID = root.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.PV1.'PV1.52'.'XCN.1'.text()
    
    //añadimos las variables a propiedades
    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null) {
        messageLog.addCustomHeaderProperty("PatientN", PatientN ?: "N/A")
        messageLog.addCustomHeaderProperty("MessageID", MessageID ?: "N/A")
        messageLog.addCustomHeaderProperty("ActivityID", ActivityID ?: "N/A")
    }
    return message;
}