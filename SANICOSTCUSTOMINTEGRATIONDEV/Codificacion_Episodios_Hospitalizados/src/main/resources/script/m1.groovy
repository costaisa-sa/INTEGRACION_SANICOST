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

//Determinar el diagnostico/procedimiento principal 
def void isPrincipal(String[] arg1, Output output, MappingContext context){
    String value = arg1[0];
    if(value == '1'){
     output.addValue('1');   
    }else{
     output.addValue(''); 
    }
}