import com.sap.it.api.mapping.*

def String getProperty(String propertyName,MappingContext context){
  String PropertyValue= context.getProperty(propertyName);
  PropertyValue= PropertyValue.toString();
  return PropertyValue;
}

//Mapeo fecha - timestamp (odata)
def void dateToTimestamp(String[] arg1, Output output, MappingContext context){
 
        String value = arg1[0];
        String timestamp = Date.parse('yyyyMMddHHmmss', value).format("yyyy-MM-dd'T'HH:mm:ss")
        output.addValue(timestamp);

}


//Mapeo Cama
def void MappingCama(String[] arg1, String[] arg2, Output output, MappingContext context){

        
        String tipo = arg1[0];
        String cama = arg2[0];
        //Solo informaremos la cama cuando no sea Urgencia
        if(tipo != 'URG'){
         output.addValue(cama);   
        }else{
         output.addValue('');   
        }

}

//Mapeo Status
def void MappingStatus(String[] arg1, Output output, MappingContext context){

    String tipo = arg1[0];
        //Solo informaremos la cama cuando no sea Urgencia
        if(tipo != 'URG'){
         output.addValue('L');   
        }else{
         output.addValue('');     
        }

}


//Mapeo Motivo Alta
def void MappingMotivoAltaId(String[] arg1, String[] arg2, Output output, MappingContext context){
    

        String tipo = arg1[0];
        String motv = arg2[0];
        //Solo informaremos la cama cuando no sea Urgencia
        if(tipo != 'URG'){
         output.addValue('');   
        }else{
         output.addValue(motv);   
        }
  
}

//Mapeo FechaFin
def void MappingFechaFin(String[] arg1, String[] arg2, Output output, MappingContext context){
    

        String tipo = arg1[0];
        String fecha = arg2[0];
        //Solo informaremos la fecha fin en urgencia
        if(tipo == 'URG'){
         String timestamp = Date.parse('yyyyMMddHHmmssSSS', fecha).format("yyyy-MM-dd'T'HH:mm:ss")
         output.addValue(timestamp);     
        }
}

//Es deudor privado?
def void DeudorPrivate(String[] arg1, Output output, MappingContext context){
    
        String value = arg1[0];
        //>>>PRG 08.02.2021
        //if(value == 'P00' || value == 'P10'){
        //if(value == 'P00' || value == 'P10' || value == 'P20' || value == 'P21' || value == 'P29' || value == 'P40'){
        if (value ==~ /^P\d+$/) {
        //<<<PRG 08.02.2021
          output.addValue('X');  
        }else{
          output.addValue('');   
        }

}