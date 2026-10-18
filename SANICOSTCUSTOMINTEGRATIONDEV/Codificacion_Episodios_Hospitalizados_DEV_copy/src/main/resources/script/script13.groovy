/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/es-ES/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/es-ES/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.Base64;
import org.json.simple.*
import groovy.json.*
import java.text.SimpleDateFormat
import groovy.xml.*
def Message processData(Message message) {
       //Get Body 
    def body = message.getBody(String.class);
    def xml  = new XmlSlurper().parseText(body);

    def formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    def formatoSalida = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    if(!xml.Activities.FechaAutorizacion.text().trim().isEmpty()){
        def fechaAutorizacion = xml.Activities.FechaAutorizacion.text();
        def fecha = formatoEntrada.parse(fechaAutorizacion)
        def fechaFormateada = formatoSalida.format(fecha)
        message.setProperty("fechaautorizacion",fechaFormateada)
    }

    if(!xml.Activities.FechaHoraInicio.text().trim().isEmpty()){
        def fechaHoraInicio = xml.Activities.FechaHoraInicio.text();
        def fecha2 = formatoEntrada.parse(fechaHoraInicio)
        def fechaFormateada2 = formatoSalida.format(fecha2)
        message.setProperty("fechahorainicio",fechaFormateada2)        
    }

    if(!xml.Activities.FechaHoraFin.text().trim().isEmpty()){
        def fechaHoraFin = xml.Activities.FechaHoraFin.text();
        def fecha3 = formatoEntrada.parse(fechaHoraFin)
        def fechaFormateada3 = formatoSalida.format(fecha3)
        message.setProperty("fechahorafin",fechaFormateada3)
 
    }

     if(!xml.Activities.FechaAccidente.text().trim().isEmpty()){
        def fechaAccidente = xml.Activities.FechaAccidente.text();
        def fecha4 = formatoEntrada.parse(fechaAccidente)
        def fechaFormateada4 = formatoSalida.format(fecha4)
        message.setProperty("fechaaccidente",fechaFormateada4)
 
    }                
    
    return message;
}