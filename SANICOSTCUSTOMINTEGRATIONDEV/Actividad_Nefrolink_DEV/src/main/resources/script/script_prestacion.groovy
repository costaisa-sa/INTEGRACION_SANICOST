import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message processData(Message message) {

//Variables 
def prestacion;
def tratamiento;
def tipo_prog;
def tecnica;
//>>>PRG 29.06.2020
def ambito;
def entidad = 'S99';
//<<<PRG 29.06.2020

//Body 
  def xml = message.getBody(String.class);
  def completeXml= new XmlSlurper().parseText(xml);


def EtiquetaPrestacion = 
    completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.ORDER_OBSERVATION'.'ORU_R01.OBSERVATION'.'**'.
findAll{ node-> node.name() == 'OBX' };

EtiquetaPrestacion.each { 
  if ( it.'OBX.1' == 1 ) { tratamiento = it.'OBX.5' } 
  if ( it.'OBX.1' == 2 ) { tipo_prog   = it.'OBX.5' }
  if ( it.'OBX.1' == 5 ) { tecnica     = it.'OBX.5' }
//>>>PRG 29.06.2020  
  if ( it.'OBX.1' == 6 ) { ambito     = it.'OBX.5' }  
//<<<PRG 29.06.2020  
} 

if (tratamiento == "DI")
{
    if (tecnica == "HD_2") {prestacion = "60207" }
    if (tecnica == "HD_1" || tecnica == "HD_7" ) 
    {
      if (tipo_prog == "CR" || tipo_prog == "CRAG" || tipo_prog == "TR") {prestacion = "60201"}
      if (tipo_prog == "AG" || tipo_prog == "TEMP" || tipo_prog == "TR") {prestacion = "60202"
//>>>PRG 29.06.2020          
        if (ambito == "HSP") { entidad = "E05";}
//<<<PRG 29.06.2020
      }
    }
}

if (tratamiento == "DP") 
{
//    if (tecnica == "DP_2") {prestacion = "60210" }
    if (tecnica == "DP_6" || tecnica == "DP_7" ) 
    {
        prestacion =  "60211"
    }else{ // OTROS 60210
        prestacion =  "60210"
    }
}

if ( prestacion != null ) {
    message.setProperty("Prestacion", prestacion);
}

//>>JZA 25.09.2020 --> Si viene informado Garante PV1.21 se informa este, si no E05 o S99
def EtiquetaGarante = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text();
def valor4 = EtiquetaGarante[0];
    if(valor4){
        entidad = valor4; 
    }
//FIN JZA
//>>>06.10.2020 JZA. Nueva Logica para calcular garante
if ( prestacion == '60201'|| prestacion == '60207'||  prestacion == '60210'  || prestacion == '60211' )
{ 
    entidad = 'S99'
}
else
{ 
    if ( prestacion == '60202' )
    {   
        if (ambito == "HSP") 
        {
            entidad='E05'
        }
        else
        {
            if (valor4)
            {
                entidad = valor4;
            }
            else
            { // ACtividad_exploraciones se encarga de gestionar el error.
                //throw new Exception('Garante no informado'); 
                entidad = 'No informado';
               // prestacion = '999999';
               // message.setProperty("Prestacion", prestacion);
                
            }
             
        }
    }    
   else 
   { 
//       throw new Exception('Excepcion Prestacion no calculada'); 
          //entidad = 'ERROR ';
          prestacion = 'No calculada';
          message.setProperty("Prestacion", prestacion);
   }
}

//>>>PRG 29.06.2020
message.setProperty("Entidad", entidad);
//<<<PRG 29.06.2020

return message;
}
