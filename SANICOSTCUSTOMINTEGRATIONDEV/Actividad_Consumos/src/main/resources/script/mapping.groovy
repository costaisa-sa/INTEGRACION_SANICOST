import com.sap.it.api.mapping.*

def String getProperty(String propertyName,MappingContext context){
  String PropertyValue= context.getProperty(propertyName);
  PropertyValue= PropertyValue.toString();
  return PropertyValue;
}

//Es deudor privado?
def void DeudorPrivate(String[] arg1, Output output, MappingContext context){
    String value = arg1[0];
    if(value == 'P00' || value == 'P10'){
      output.addValue('X');  
    }else{
      output.addValue('');   
    }
    
}