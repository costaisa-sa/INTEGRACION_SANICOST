import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import org.json.simple.*
import groovy.json.*

def Message processData(Message message) {
    //Get Body 
    def body = message.getBody(String.class);
    def xml  = new XmlSlurper().parseText(body);

    // Crear la estructura inicial del JSON
    def jsonData = [
        inputconsumrecords: [
            Material : []
        ]
    ]

    // Array Materiales
    def MaterialID = ""
    def Quan = ""
    def ItemText = ""

    if(xml.size() > 0){
        // Montamos los campos de InputConsumRecords
        xml.children().each{ child ->
            if(child.name() != 'Material'){
                jsonData.inputconsumrecords[child.name()] = child.text()
            }
        }
        // Montamos los diferentes materiales como arrays
        if(xml.Material.size() > 0){
            for( MAT in xml.Material){
                MaterialID = ""
                Quan = ""
                ItemText = ""
                MAT.children().each{ Mchild ->
                    if( Mchild.name()  == "MaterialID"){
                        MaterialID = Mchild.text() 
                    }
                    if( Mchild.name()  == "Quan"){
                        Quan = Mchild.text() 
                    }
                    if( Mchild.name()  == "ItemText"){
                        ItemText = Mchild.text() 
                    }

                }
                jsonData.inputconsumrecords.Material << [MaterialID: MaterialID, Quan:Quan,ItemText:ItemText]
            }
        }

        // Convertir el mapa a JSON
        def jsonOutput = JsonOutput.prettyPrint(JsonOutput.toJson(jsonData))
        def jsonSlurper = new JsonSlurper()
        def jsonObject = jsonSlurper.parseText(jsonOutput)
        if(jsonObject.inputconsumrecords.isReturn == "true"){
            jsonObject.inputconsumrecords.isReturn = true
        }else{
            jsonObject.inputconsumrecords.isReturn = false
        }
        def jsonString = JsonOutput.toJson(jsonObject)
        message.setBody(jsonString)
    }else{
        message.setBody("Error: No data");
    }
    
    return message;
}