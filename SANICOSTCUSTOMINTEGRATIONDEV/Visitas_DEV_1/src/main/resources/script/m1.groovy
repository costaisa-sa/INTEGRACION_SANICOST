import com.sap.it.api.mapping.*

def String getProperty(String propertyName,MappingContext context){
  String PropertyValue= context.getProperty(propertyName);
  PropertyValue= PropertyValue.toString();
  return PropertyValue;
}

//Es deudor privado?
def void DeudorPrivate(String[] arg1, Output output, MappingContext context){
    String value = arg1[0];
    //>>>PRG 08.02.2021
    //if(value == 'P00' || value == 'P10'){
    //>>>PRG 25.11.2024    
    //if(value == 'P00' || value == 'P10' || value == 'P20' || value == 'P21'){
    //JHP 04.12.2024
    //if(value == 'P00' || value == 'P10' || value == 'P20' || value == 'P21' || value == 'P29'){ 
    //if(value == 'P00' || value == 'P10' || value == 'P20' || value == 'P21' || value == 'P29' || value == 'P24' || value == 'P22' || value == 'P23' || value == 'P25' || value == 'P26' || value == 'P27' || value == 'P28' || value == 'P30'){ 
    //JHP 04.12.2024
    //<<<PRG 25.11.2024        
    //<<<PRG 08.02.2021
    if (value ==~ /^P\d+$/) {
      output.addValue('X');  
    }else{
      output.addValue('');   
    }
    
}