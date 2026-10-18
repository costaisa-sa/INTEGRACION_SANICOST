import com.sap.it.api.mapping.*
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;


def Message processData(Message message) {

//leer Propiedad
	def map = message.getProperties();
	def NHCValue = map.get('NHC');
	def var = NHCValue;
    def iVar = var.toInteger();
    
//  Primero Check material.
    def MatType = map.get('MatType');
    if (MatType !='001')
    { 
        message.setProperty("NoCrearActividad", 'X');
    }
    else
    {
        if(iVar == 0) 
        {
            message.setProperty("NoCrearActividad", 'X');
        }
        else
        {
           
            def CentroCosteExcluidoValue= map.get('CentroCosteExcluido');      
            if (CentroCosteExcluidoValue=='X')
            {
              //message.setProperty("StatusNoFacturable", 'X'); //De momento no creamos la actividad en este supuesto.
                message.setProperty("NoCrearActividad", 'X');
            }
            else
            {
                def CheckDeudorPrivateValue= map.get('CheckDeudorPrivate');
                if (CheckDeudorPrivateValue=='')
                {
                    def IsAmbulatorioValue= map.get('IsAmbulatorio');
                    if(IsAmbulatorioValue=='')
                    {
                        message.setProperty("NoCrearActividad", 'X');
                    }
                    else
                    {
                        def TieneConvenioValue= map.get('TieneConvenio');
                        if(TieneConvenioValue=='') 
                        {
                            message.setProperty("NoCrearActividad", 'X');
                        
                        }       
                    }
                }
// INI INSR JZA 28.09.2020 - Check hospitalario. (la variable mira si P10 y Hospital                   
// Es privado, miramos si es hospitalizado
                else
                {

                   def IsHospitalarioValue= map.get('IsHospitalario');
                   if(IsHospitalarioValue=='X')
                   {
                       message.setProperty("NoCrearActividad", 'X'); 
                   }
//FIN INSR JZA    
                }
            }
        }
    }
    return message;


}