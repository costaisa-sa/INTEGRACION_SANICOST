import com.sap.gateway.ip.core.customdev.util.Message

import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

def Message processData(Message message) {
    def body = message.getBody(java.lang.String) as String
    body = body.replace("[\"\"]","[]")
    message.setBody(body)
    
    def body2 = message.getBody(String.class)
    def jsonSlurper = new JsonSlurper()
    def jsonObject = jsonSlurper.parseText(body2)
    def cleanedObject = removeEmptyFields(jsonObject)
    def jsonBuilder = new JsonBuilder(cleanedObject)
    message.setBody(jsonBuilder.toPrettyString()) 
    
    
    return message
}



/*
def Message processData(Message message) {
    def body = message.getBody(String.class)
    def jsonSlurper = new JsonSlurper()
    def jsonObject = jsonSlurper.parseText(body)
    def cleanedObject = removeEmptyFields(jsonObject)
    def jsonBuilder = new JsonBuilder(cleanedObject)
    message.setBody(jsonBuilder.toPrettyString())
    return message
}
*/
def removeEmptyFields(def object) {
    if (object instanceof Map) {
        object.entrySet().removeAll { it.value == null || (it.value instanceof String && it.value.trim() == "") }
        object.collectEntries { [it.key, removeEmptyFields(it.value)] }
    } else if (object instanceof List) {
        object.collect { removeEmptyFields(it) }.findAll { it != null }
    } else {
        object
    }
}