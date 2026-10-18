import com.sap.it.api.mapping.*;

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

def void mapTipoDocSanitario(String[] arg1, String[] arg2, String[] arg3, String[] arg4, String[] arg5, Output output, MappingContext context){
	if(arg1){
	   output.addValue("6"); //Numero afiliación seguridad social
	}else if(arg2){
	   output.addValue("1"); //C.I.P. – Código identificación personal
	}else if(arg3){
	    output.addValue("3"); //Codigo Identificación SNS
	}else if(arg4){
	    output.addValue("4"); //Tarjeta Sanitaria Europea
	}else{
	    output.addValue("5"); //Tajeta Sanitaria Otras CA (Comunidades Autonomas)
	}
}

def void mapValuesNull(String[] arg1, String[] arg2, String[] arg3, String[] arg4, String[] arg5, Output output, MappingContext context){
	if(arg1){
	   String value1=arg1[0];
        output.addValue(value1);
	}else if(arg2){
	    String value2=arg2[0];
        output.addValue(value2);
	}else if(arg3){
	    String value3=arg3[0];
        output.addValue(value3);
	}else if(arg4){
	    String value4=arg4[0];
        output.addValue(value4);
	}else{
	    String value5=arg5[0];
        output.addValue(value5);
	}
}


