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

//arg1 => header context
//arg2 -> search field
def String customFunc(String arg1,String arg2){
    
    def result = (arg1 =~ /[^,]+/).findAll();
    
    if (result[0]) {
        if (arg2 == 'NHC' && result[0][0]) {
            return result[0][0];
        }
        
        if (arg2 == 'Orden_Medica' && result[0][4]) {
            return result[0][4];
        }
        
        
        if (arg2 == 'Medico_Prescriptor' && result[0][2]) {
            return result[0][2];
        }
    }


}