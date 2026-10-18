import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.json.*


def Message processData(Message message) {
    /*def body = message.getBody(java.lang.String) as String
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)
    int counter = 0;
    
    for (counter = 0; counter <jsonObject.RequestedMaterialsType.size(); counter++) {
        jsonObject.RequestedMaterialsType[counter]["Traslado"] = jsonObject.RequestedMaterialsType[counter]["Traslado"].replaceAll("\"(\\d+)\"", "\$1");
   } 
   
    message.setBody(JsonOutput.toJson(jsonObject["RequestedMaterialsType"]))*/
    
    def body = message.getBody(java.lang.String) as String;
    def jsonSlurper = new JsonSlurper();
    def jsonObject = jsonSlurper.parseText(body);
    def reqMatArray = [];
    
    if (!isCollectionOrArray(jsonObject.RequestedMaterialsType)){
        reqMatArray.push(jsonObject.RequestedMaterialsType);
        jsonObject.RequestedMaterialsType = reqMatArray;
        jsonObject.RequestedMaterialsType = reqMatArray;
    }

    for (counter = 0; counter <jsonObject.RequestedMaterialsType.size(); counter++) {
        def record = jsonObject.RequestedMaterialsType[counter];
        
        record.Traslado = convertToInt(record.Traslado);
        record.Quan = convertToInt(record.Quan);
        record.QuanPend = convertToInt(record.QuanPend);
        record.InfoOriginType = convertToInt(record.InfoOriginType);
        record.RequestedState = convertToInt(record.RequestedState);
    }
    
    message.setBody(new JsonBuilder(jsonObject.RequestedMaterialsType).toPrettyString())
    return message;
}

static Object convertToInt(Object inputValue){
    if(inputValue.isNumber()){
      return inputValue.toInteger();
    }else{
      return inputValue;
    }
}

boolean isCollectionOrArray(object) {         
    [Collection, Object[]].any { 
        it.isAssignableFrom(object.getClass()) 
    } 
}