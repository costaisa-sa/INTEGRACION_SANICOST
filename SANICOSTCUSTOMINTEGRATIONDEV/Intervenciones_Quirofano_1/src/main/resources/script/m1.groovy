import com.sap.it.api.mapping.*

def String getProperty(String propertyName,MappingContext context)

{
  String PropertyValue= context.getProperty(propertyName);

  PropertyValue= PropertyValue.toString();

  return PropertyValue;

}

//Es deudor privado?
def void DeudorPrivate(String[] arg1, Output output, MappingContext context){
    String value = arg1[0];
    //if(value == 'P00' || value == 'P10'){
    //if(value == 'P00' || value == 'P10' || value == 'P20' || value == 'P21'){
    if (value ==~ /^P\d+$/) {
      output.addValue('X');  
    }else{
      output.addValue('');   
    }
    
}