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
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.MarkupBuilder;

def Message processData(Message message) {
    //Body 
    def multiline = message.getBody(java.lang.String) as String;  
    
    def writer = new StringWriter();
    def xml = new MarkupBuilder(writer); 
    
    xml.ActivitiesCatSalut() {
        multiline.eachLine {line, count-> 
            if (count==0) {
                buildXMLHeader(xml,line);
            }
            else {
                buildXMLDetails(xml,line);
            }
        }
    }
    message.setBody(writer.toString());
    return message;
}

def buildXMLHeader(xml,line){
    xml.ActividadHeader(){
        TipusRegistre(line.substring(0,1));
        TipusOrganitzacio(line.substring(1,3));
        CodiUnitatProveidora(line.substring(3,8));
        Aplicacio(line.substring(18,21));
        
        TipusFitxer(line.substring(26,27));
        DataIniciPeriode(line.substring(27,35));
        DataFinalPeriode(line.substring(35,43));
        DataHoraCreacioFitxer(line.substring(43,57));
        NombreTotalRegistres(line.substring(57,65));
        
        CLSAnyLotEnviamentFitxer(line.substring(111,115));
        CLSCodiOrigenFitxer1(line.substring(115,120));
        CLSCodiOrigenFitxer2(line.substring(120,124));
        CLSCodiAplicacioOrigenFitxer(line.substring(124,128));
        CLSCodiProcesExecutarDestinacioFitxer(line.substring(128,140));
        CLSCodiDestinacioFitxer1(line.substring(140,145));
        CLSCodiDestinacioFitxer2(line.substring(145,149));
        CLSCodiAplicacioDestinacioFitxer(line.substring(149,153));
        CLSNumeroLotEnviamentFitxer(line.substring(153,159));
        CIFEntitatProveidora(line.substring(159,168));
        NumeroFactura(line.substring(168,178).replace(" ",""));
        TipusFactura(line.substring(178,179));
        NumeroFacturaOrigen(line.substring(179,189));
        CodiContracte(line.substring(189,200));
        
        IndicadorSigneImport(line.substring(232,233));
        ImportTotalFacturat(line.substring(233,247));
        DataEmissioFactura(line.substring(247,255));
    }
}

def buildXMLDetails(xml,line){
    xml.ActividadDetails(){
        /*TipusRegistre(line.substring(0,1));
        
        NumeroLiniaFactura(line.substring(7,13).replace(" ",""));
        NumeroClausula(line.substring(13,16));
        
        TipusRegimEconomic(line.substring(21,23));
        CodiUPOrigen(line.substring(23,28));
        CodiComunitatConveniColectiu(line.substring(28,31));
        CIPPacient(line.substring(31,45));
        TipusCodiIdentificacioSanitari(line.substring(45,47));
        CodiIdentificacioSanitari(line.substring(47,72));
        
        PrimerCognom(line.substring(82,102));
        SegonCognom(line.substring(102,122));
        Sexe(line.substring(122,123));
        DataNaixement(line.substring(123,131));
        NumeroHistoriaClinica(line.substring(131,139));
        NumeroAssistencia(line.substring(139,148));
        LiniaServei(line.substring(148,149));
        CodiServeiSanitari(line.substring(149,154));
        CodiServeiAssistencial(line.substring(154,159));
        CodificacioProducte(line.substring(159,169));
        PerfilPacient(line.substring(169,170));
        NumeroLiniaActivitatOrigen(line.substring(170,176));
        DataInici(line.substring(176,184));
        HoraInici(line.substring(184,188));
        DataFinalitzacio(line.substring(188,196));
        HoraFinalitzacio(line.substring(196,200));
        
        QuantitatActivitat(line.substring(216,222));
        Import(line.substring(222,236));
        MotiuAlta(line.substring(236,238));
        */
        
  
        TipusRegistre(line.substring(0,1));

        NumeroLiniaFactura(line.substring(7,13).replace(" ",""));
        NumeroClausula(line.substring(13,16));
        LotClausula(line.substring(16,21));
        TipusRegimEconomic(line.substring(21,23));
        CodiUPOrigen(line.substring(23,28));
        CodiComunitatConveniColectiu(line.substring(28,31));
        CIPPacient(line.substring(31,45));
        TipusCodiIdentificacioSanitari(line.substring(45,47));
        CodiIdentificacioSanitari(line.substring(47,72));
        NumeroHistoriaClinica(line.substring(72,82));
        PrimerCognom(line.substring(82,102));
        SegonCognom(line.substring(102,122));
        Sexe(line.substring(122,123));
        DataNaixement(line.substring(123,131));

        NumeroAssistencia(line.substring(139,148));
        CodiServeiSanitari(line.substring(148,154));
        CodiServeiAssistencial(line.substring(154,159));
        CodificacioProducte(line.substring(159,169));
        PerfilPacient(line.substring(169,170));

        DataInici(line.substring(176,184));
        HoraInici(line.substring(184,188));
        DataFinalitzacio(line.substring(188,196));
        HoraFinalitzacio(line.substring(196,200));
        DataIniciTractament(line.substring(200,208));
        DataFiTractament(line.substring(208,216));
        QuantitatActivitat(line.substring(216,222));
        Import(line.substring(222,236));
        MotiuAlta(line.substring(236,238));
        GrupTecnica(line.substring(238,241));
        MatriculaVehicle(line.substring(241,249));
        Quilometratge(line.substring(249,254));
        OrigenTrasllat(line.substring(254,255));
        CodiUPOrigenTrasllat(line.substring(255,260));
        DestiTrasllat(line.substring(260,261));
        CodiUPDestiTrasllat(line.substring(261,266));
        CodiLocalitatOrigenTrasllat(line.substring(266,273));
        CodiLocalitatDestiTrasllat(line.substring(273,280));
        TempsEspera(line.substring(280,281));
        CodiProcesRehabilitacio(line.substring(281,285));
        CodiAparellOrtoprotetic(line.substring(285,295));
        TipusCodiIdentificacioSanitariAct(line.substring(295,297));
        CodiIdentificacioSanitariAct(line.substring(297,322));
        CMBDH(line.substring(322,347));

        Errors(line.substring(399).replace(" ",""));
    }
}

/*
def buildXMLHeader(sb,line){
    sb.append("<TipusRegistre>"+line.substring(0,1)+"</TipusRegistre>");
    sb.append("<TipusOrganitzacio>"+line.substring(1,3)+"</TipusOrganitzacio>");
    sb.append("<CodiUnitatProveidora>"+line.substring(3,8)+"</CodiUnitatProveidora>");
    sb.append("<Aplicacio>"+line.substring(18,21)+"</Aplicacio>");
    
    sb.append("<TipusFitxer>"+line.substring(26,27)+"</TipusFitxer>");
    sb.append("<DataIniciPeriode>"+line.substring(27,35)+"</DataIniciPeriode>");
    sb.append("<DataFinalPeriode>"+line.substring(35,43)+"</DataFinalPeriode>");
    sb.append("<DataHoraCreacioFitxer>"+line.substring(43,57)+"</DataHoraCreacioFitxer>");
    sb.append("<NombreTotalRegistres>"+line.substring(57,65)+"</NombreTotalRegistres>");
    
    sb.append("<CLSAnyLotEnviamentFitxer>"+line.substring(111,115)+"</CLSAnyLotEnviamentFitxer>");
    sb.append("<CLSCodiOrigenFitxer1>"+line.substring(115,120)+"</CLSCodiOrigenFitxer1>");
    sb.append("<CLSCodiOrigenFitxer2>"+line.substring(120,124)+"</CLSCodiOrigenFitxer2>");
    sb.append("<CLSCodiAplicacioOrigenFitxer>"+line.substring(124,128)+"</CLSCodiAplicacioOrigenFitxer>");
    sb.append("<CLSCodiProcesExecutarDestinacioFitxer>"+line.substring(128,140)+"</CLSCodiProcesExecutarDestinacioFitxer>");
    sb.append("<CLSCodiDestinacioFitxer1>"+line.substring(140,145)+"</CLSCodiDestinacioFitxer1>");
    sb.append("<CLSCodiDestinacioFitxer2>"+line.substring(145,149)+"</CLSCodiDestinacioFitxer2>");
    sb.append("<CLSCodiAplicacioDestinacioFitxer>"+line.substring(149,153)+"</CLSCodiAplicacioDestinacioFitxer>");
    sb.append("<CLSNumeroLotEnviamentFitxer>"+line.substring(153,159)+"</CLSNumeroLotEnviamentFitxer>");
    sb.append("<CIFEntitatProveidora>"+line.substring(159,168)+"</CIFEntitatProveidora>");
    sb.append("<NumeroFactura>"+line.substring(168,178)+"</NumeroFactura>");
    sb.append("<TipusFactura>"+line.substring(178,179)+"</TipusFactura>");
    sb.append("<NumeroFacturaOrigen>"+line.substring(179,189)+"</NumeroFacturaOrigen>");
    sb.append("<CodiContracte>"+line.substring(189,200)+"</CodiContracte>");
    
    sb.append("<IndicadorSigneImport>"+line.substring(232,233)+"</IndicadorSigneImport>");
    sb.append("<ImportTotalFacturat>"+line.substring(233,247)+"</ImportTotalFacturat>");
    sb.append("<DataEmissioFactura>"+line.substring(247,255)+"</DataEmissioFactura>");
}

def buildXMLDetails(sb,line){
    sb.append("<TipusRegistre>"+line.substring(0,1)+"</TipusRegistre>");
    
    sb.append("<NumeroLiniaFactura>"+line.substring(7,13)+"</NumeroLiniaFactura>");
    sb.append("<NumeroClausula>"+line.substring(13,16)+"</NumeroClausula>");
    
    sb.append("<TipusRegimEconomic>"+line.substring(21,23)+"</TipusRegimEconomic>");
    sb.append("<CodiUPOrigen>"+line.substring(23,28)+"</CodiUPOrigen>");
    sb.append("<CodiComunitatConveniColectiu>"+line.substring(28,31)+"</CodiComunitatConveniColectiu>");
    sb.append("<CIPPacient>"+line.substring(31,45)+"</CIPPacient>");
    sb.append("<TipusCodiIdentificacioSanitari>"+line.substring(45,47)+"</TipusCodiIdentificacioSanitari>");
    sb.append("<CodiIdentificacioSanitari>");sb.append(line.substring(47,72));sb.append("</CodiIdentificacioSanitari>");
    
    sb.append("<PrimerCognom>"+line.substring(82,102)+"</PrimerCognom>");
    sb.append("<SegonCognom>"+line.substring(102,122)+"</SegonCognom>");
    sb.append("<Sexe>"+line.substring(122,123)+"</Sexe>");
    sb.append("<DataNaixement>"+line.substring(123,131)+"</DataNaixement>");
    sb.append("<NumeroHistoriaClinica>"+line.substring(131,139)+"</NumeroHistoriaClinica>");
    sb.append("<NumeroAssistencia>"+line.substring(139,148)+"</NumeroAssistencia>");
    sb.append("<LiniaServei>"+line.substring(148,149)+"</LiniaServei>");
    sb.append("<CodiServeiSanitari>"+line.substring(149,154)+"</CodiServeiSanitari>");
    sb.append("<CodiServeiAssistencial>"+line.substring(154,159)+"</CodiServeiAssistencial>");
    sb.append("<CodificacioProducte>"+line.substring(159,169)+"</CodificacioProducte>");
    sb.append("<PerfilPacient>"+line.substring(169,170)+"</PerfilPacient>");
    sb.append("<NumeroLiniaActivitatOrigen>"+line.substring(170,176)+"</NumeroLiniaActivitatOrigen>");
    sb.append("<DataInici>"+line.substring(176,184)+"</DataInici>");
    sb.append("<HoraInici>"+line.substring(184,188)+"</HoraInici>");
    sb.append("<DataFinalitzacio>"+line.substring(188,196)+"</DataFinalitzacio>");
    sb.append("<HoraFinalitzacio>"+line.substring(196,200)+"</HoraFinalitzacio>");
    
    sb.append("<QuantitatActivitat>"+line.substring(216,222)+"</QuantitatActivitat>");
    sb.append("<Import>"+line.substring(222,236)+"</Import>");
    sb.append("<MotiuAlta>"+line.substring(236,238)+"</MotiuAlta>");
}*/