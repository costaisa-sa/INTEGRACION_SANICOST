import com.sap.it.api.mapping.*;
import java.text.Normalizer;

/*Add MappingContext parameter to read or set headers and properties
def String customFunc1(String P1,String P2,MappingContext context) {
         String value1 = context.getHeader(P1);
         String value2 = context.getProperty(P2);
         return value1+value2;
}

Add Output parameter to assign the output value.
def void custFunc2(String[] is,String[] ps, Output output, MappingContext context) {
        String value1 = context.getHeader(is[0]);
        String value2 = context.getProperty(ps[0]);
        output.addValue(value1);
        output.addValue(value2);
}*/

//Mapeo de documentos deudor 
def void mapDeudorDocType(String[] arg1, String[] arg2, String[] arg3, Output output, MappingContext context){
	if(arg1){
	    output.addValue("2");  //Numero pasaporte
	}else if(arg2){
	    output.addValue("C"); //Documento nacional de identidad
	}else{
	   output.addValue("8");  //Número de identificación extranjeros
	}
}


//Funcion para determinar que valor no es nulo
def void mapValuesNull(String[] arg1, String[] arg2, String[] arg3, Output output, MappingContext context){
	if(arg1){
	    String value1=arg1[0];
        output.addValue(value1);
	}else if(arg2){
	    String value2=arg2[0];
        output.addValue(value2);
	}else{
	    String value3=arg3[0];
        output.addValue(value3);
	}
}

//Mapeo fecha - timestamp (odata)
def void dateToTimestamp(String[] arg1, Output output, MappingContext context){
    String value = arg1[0];

    String year  = value.take(4); //Cogemos los 4 primeros (el año)
    String month = value.substring(4,6) //Saltamos los 4 primeros y cogemos los dos siguientes (el mes)
    String day   = value.reverse().take(2).reverse(); //Cogemos los 2 ultimos (el día)
    
    value = year+ "-" +month+ "-" +day+ "T00:00:00";
    
    output.addValue(value);
}


//Obtener valor de una variable global
def String getProperty(String propertyName,MappingContext context){
  String PropertyValue= context.getProperty(propertyName);
  PropertyValue= PropertyValue.toString();
  return PropertyValue;
}

//Elimina tildes
def String normalizeString(String str,MappingContext context){
    String strNormalizced = str;
    strNormalizced = Normalizer.normalize(strNormalizced, Normalizer.Form.NFD);     
    strNormalizced = strNormalizced.replaceAll("[\\p{InCombiningDiacriticalMarks}]", ""); 
    return strNormalizced;
}

//Mapeo RegimenEconomico
/*
def void mapRegimenEconomico(String[] arg1, String[] arg2, Output output, MappingContext context){
    String provCodes = ["43","17","25","8","08"]; //Provincias de Cataluña
    String pais = arg1[0]; //Pais 
    String prov = arg2[0]; //Provincia
    
    if(pais == "ES"  && provCodes.includes(prov) == true ){
      //Es España y provincia de Cataluña 
      output.addValue("01"); //RegimenEconomico = 01
    }else if(pais == "ES"  && provCodes.includes(prov) == false){
      //Es España y no es Cataluña
      output.addValue("02"); //RegimenEconomico = 02
    }else{
      output.addValue("07"); //RegimenEconomico = 07 
    }
}
*/