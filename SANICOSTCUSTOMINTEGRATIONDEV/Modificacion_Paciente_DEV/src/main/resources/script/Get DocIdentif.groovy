import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.XmlUtil;

def Message processData(Message message) {

//Body 
  def xml = message.getBody(String.class);
  def completeXml= new XmlSlurper().parseText(xml);
  
def cx_str;
def dln_str;
def zpi_str;
def docIdentif;

//guardar todas las variables

//def cx = completeXml.'**'.find{ node-> node.name() == 'CX.1' }*.text();

def dln = completeXml.'**'.find{ node-> node.name() == 'DLN.1' }*.text();

def zpi = completeXml.'**'.find{ node-> node.name() == 'ZPID.15' }*.text();


//Si hay nulos los convertimos a blanco

//if (cx){ cx_str  = cx[0] } else {cx_str  = ""}

if(dln){ dln_str = dln[0]} else {dln_str = ""}

if(zpi){ zpi_str = zpi[0]} else {zpi_str = ""}



//El primero que no este blanco se asigna
//if     (cx_str != "")  {docIdentif = cx_str}
//else 
if(dln_str != "") {docIdentif = dln_str}
else if(zpi_str != "") {docIdentif = zpi_str}
else                   {docIdentif = ""}

message.setProperty("DocIdentif", docIdentif)  

return message

}