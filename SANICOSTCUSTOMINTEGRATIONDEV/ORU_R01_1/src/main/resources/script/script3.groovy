import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
       def xml = message.getBody(String.class);
       def completeXml= new XmlSlurper().parseText(xml);
       def valor;
       //MSH.3
       def MSH3 = completeXml.MSH.'MSH.3'.'**'.findAll{ node-> node.name() == 'HD.1' }*.text();
       if(MSH3){
           valor = MSH3[0];
           message.setProperty("MSH3", valor);
       }

// Get deudor 
       def Garante = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.PV1.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text();
       if(Garante){
         def valor2 = Garante[0];
         // Si viene vacío, poner catsalut como garante
         valor2 = valor2.trim();
         if (valor2=='') valor2 = 'S99';
         //<<<PRG 17.04.2020 ERR-3
         message.setProperty("Garante", valor2);
       }       
       
       //>>>PRG 12.02.2021 Evento E03,E04 descarta el mensaje de fertilidad
       def evento = completeXml.ZTV.'**'.findAll{ node-> node.name() == 'ZTV.10' }*.text();
       if (evento) {
            def valor3 = evento[0];
            valor3 = valor3.trim();
            if (valor == 'TV' && (valor3 =='E02' || valor3 =='E03' || valor3 == 'E04')) {
                message.setProperty("MSH3", "IGNORE");   
            }
       }
       //<<<PRG 12.02.2021
       
       return message;
}
