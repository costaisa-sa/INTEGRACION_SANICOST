import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.XmlUtil;

def Message processData(Message message) {

//Body 
  def xml = message.getBody(String.class);
  def completeXml= new XmlSlurper().parseText(xml);
  
//VAriable
String Prestacion;

//Obtener valor nodos
def evento  = completeXml.'ZTV'.'ZTV.10'.text();
def tecnica = completeXml.'ORU_R01.PATIENT_RESULT'.'ORU_R01.PATIENT'.'ORU_R01.VISIT'.'PV2'.'PV2.24'.text();
def tipo_ia = completeXml.'ZTV'.'ZTV.9'.text();
def embriones = completeXml.'ZTV'.'ZTV.13'.text();
def biopsia   = completeXml.'ZTV'.'ZTV.14'.text();

    if (embriones != ""){
        def num_embriones = embriones.toInteger();
    }
    else
    {
        num_embriones = 0;
    }

     if (evento == "E12")
    {
        Prestacion = "33000";
    }

    if (evento == "E01")
    {
    //  if( (tecnica == "IAC")                    || 
    //     (tecnica == "IAD" && tipo_ia == "I" ) ||
    //     (tecnica == "IAD" && tipo_ia == "P") )
    //  {
        Prestacion = "33105"; 
    //  }
    }
        
    if (evento == "E11")
    {
      if (tecnica == "IAC")                    {Prestacion="33113";} 
      if (tecnica == "IAD" && tipo_ia == "I" ) {Prestacion="33112";}
      if (tecnica == "IAD" && tipo_ia == "P" ) {Prestacion="33111";}
    }

    if (evento == "E06")
    {
        Prestacion = "45201";  
    }
    
    if (evento == "E07")
    {
        Prestacion = "48030";  
    }
    
    if (evento == "E08")
    {
        Prestacion = "48020";          
    }
    
    if (evento == "E05")
    {
        if (tecnica == "FIV")
        {
        if (num_embriones <= 8 ) {Prestacion="33140";}     
        if (num_embriones > 8  ) {Prestacion="33141";}         
        if (biopsia == "BIOP1") {Prestacion="33150";}
        if (biopsia == "BIOP2") {Prestacion="33151";}
        if (biopsia == "BIOP3") {Prestacion="33152";}
        if (biopsia == "BIOP4") {Prestacion="33153";}
        }
    }

  message.setProperty("Prestacion", Prestacion);

return message;
}







