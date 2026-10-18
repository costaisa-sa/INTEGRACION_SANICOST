/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;
def Message processData(Message message) {
    def xml = message.getBody(String.class);
    def completeXml= new XmlSlurper().parseText(xml);
       
    /*def oNodeProductID = completeXml.'**'.findAll{ node-> node.name() == 'ZX2.1' }*.text();
    def oNodeMessageID = completeXml.'**'.findAll{ node-> node.name() == 'MSH.10' }*.text();
    def sTime = oNodeMessageID[0].substring(8,oNodeMessageID[0].size()-1)+"00";
    def sMessageID = oNodeMessageID[0]+oNodeProductID[0];
    def iLimit = sMessageID.size()-20;
    if (iLimit>0) {
        sMessageID = sMessageID.substring(iLimit,sMessageID.size());
    }*/
    def sMessageID = new Date().getTime();
    message.setProperty("ActivityCodExt", sMessageID);
   
       //>>>PRG 22.06.2020
    def oNodeDevol = completeXml.'**'.findAll{ node-> node.name() == 'OBR.13' }*.text(); 
    def sNodeDevol = oNodeDevol[0];
    if (sNodeDevol == 'A') message.setProperty("isReturn","X");
    else message.setProperty("isReturn",""); 
    //<<<PRG 22.06.2020
   
    def oNodeGarante = completeXml.'**'.findAll{ node-> node.name() == 'PV1.21' }*.text(); 
    def  sGarante = oNodeGarante[0];
    //>>>PRG 12.05.2020 ERR-3 Si viene vacío, poner catsalut como garante
    if (sGarante=='') sGarante = 'S99';
    //if (sGarante=='S99') sGarante = 'S99F';
    //<<<PRG 12.05.2020 ERR-3
    message.setProperty("DeudorID",sGarante);
   // if (sGarante=='P00' || sGarante=='P10') message.setProperty("CheckDeudorPrivate","X");  VERSION ANTERIOR JOAN ZENON
   //    else message.setProperty("CheckDeudorPrivate","");
   
   // >>> 14.10.2020 LOGICA NUEVA PARA QUE SE TENGAN EN CUENTA LAS MUTUAS. LO QUE NO SEA S99 SE VA AL CIRCUITO PRIVADO Y MUTUAS
   if (sGarante =='S99')  message.setProperty("CheckDeudorPrivate","");
   else message.setProperty("CheckDeudorPrivate","X");

  //<<< 14.10.2020 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
   
    def oNodeTipoPrestacionID = completeXml.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
    def sTipoPrestacionID = oNodeTipoPrestacionID[0];
    message.setProperty("TipoPrestacionID",sTipoPrestacionID);
    
    def oNodeEpisodioID = completeXml.'**'.findAll{ node-> node.name() == 'PV1.19' }*.text();
    def sEpisodioID = oNodeEpisodioID[0];
    message.setProperty("EpisodioID",sEpisodioID);
    message.setProperty("ProcesoID",sEpisodioID);
   
    def oNodePrestacionID = completeXml.'**'.findAll{ node-> node.name() == 'ZX2.1' }*.text();
    def sPrestacionID = oNodePrestacionID[0];
    message.setProperty("PrestacionID",sPrestacionID);
    
    def oNodeNHC = completeXml.'**'.findAll{ node-> node.name() == 'PID.2' }*.text();
    def sNHC = oNodeNHC[0];
    message.setProperty("NHC",sNHC);
    
    message.setProperty("Center","2410");
    
    def oNodePV144 = completeXml.'**'.findAll{ node-> node.name() == 'PV1.44' }*.text();    
    def oNodeFecha = oNodePV144[0]; 
    if (oNodeFecha) {
        
        def sFecha = oNodeFecha.substring(0,8);
        if (oNodeFecha.size() > 8) {
            sFecha = oNodeFecha.substring(0,oNodeFecha.size()-2);
        }
        else  sFecha = sFecha + "000001";
        
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyyMMddHHmmss"); 
        Date oDateInput = dateFormatInput.parse(sFecha);
        SimpleDateFormat dateFormatOuput = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss"); 
        String sFechaFormatted = dateFormatOuput.format(oDateInput);
        //String sFechaFormatted = Date.parse('yyyyMMdd', sFecha).format("yyyy-MM-dd'T'HH:mm:ss");
        
        message.setProperty("Fecha", sFechaFormatted);
    }

    def oNodeCantidad = completeXml.'**'.findAll{ node-> node.name() == 'ZX2.2' }*.text();
    def sCantidad = oNodeCantidad[0];
    def iCantidad = 0;
    if (sCantidad.isInteger()) {
        iCantidad= sCantidad.toInteger();
    }
    message.setProperty("Cantidad",iCantidad+"");
    
  // propiedades para actividades
    def oNodeAmbu = completeXml.'**'.findAll{ node-> node.name() == 'PV1.2' }*.text();
    def sNodeAmbu = oNodeAmbu[0];
    if (sNodeAmbu=='O')
    {
        message.setProperty("IsAmbulatorio",'X');
    }
    else
    {
        message.setProperty("IsAmbulatorio",'');
    }
   
//INSR JZA 28.09.2020 - Es hospitalizado?
    if (sNodeAmbu=='I')
    {
        if (sGarante=='P10') 
        {
            message.setProperty("IsHospitalario",'X');
        }
        else{message.setProperty("IsHospitalario",'');}
    }
    else
    {
        message.setProperty("IsHospitalario",'');
    }
//FIN INSR JZA
    
    def oNodeCeCo = completeXml.'**'.findAll{ node-> node.name() == 'ZX1.1' }*.text();
    def sCeco = oNodeCeCo[0];
    message.setProperty("CentroCoste",sCeco);
   
   return message;
}