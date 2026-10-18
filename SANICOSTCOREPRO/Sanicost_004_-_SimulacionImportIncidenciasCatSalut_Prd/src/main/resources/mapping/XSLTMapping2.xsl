<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes"/>
	<xsl:template match="/file/line[1]">
	    <ActividadHeader>
		<!-- TODO: Auto-generated template -->
		    <TipusRegistre><xsl:value-of select="substring(text(),1,1)"/></TipusRegistre>
		    <TipusOrganitzacio><xsl:value-of select="substring(text(),2,2)"/></TipusOrganitzacio>
		    <CodiUnitatProveidora><xsl:value-of select="substring(text(),4,5)"/></CodiUnitatProveidora>
		    <Aplicacio><xsl:value-of select="substring(text(),19,3)"/></Aplicacio>
		    <TipusFitxer><xsl:value-of select="substring(text(),27,1)"/></TipusFitxer>
		    <DataIniciPeriode><xsl:value-of select="substring(text(),28,8)"/></DataIniciPeriode>
		    <DataFinalPeriode><xsl:value-of select="substring(text(),36,8)"/></DataFinalPeriode>
		    <DataHoraCreacioFitxer><xsl:value-of select="substring(text(),44,14)"/></DataHoraCreacioFitxer>
		    <NombreTotalRegistres><xsl:value-of select="substring(text(),58,8)"/></NombreTotalRegistres>
		    <CLSAnyLotEnviamentFitxer><xsl:value-of select="substring(text(),112,4)"/></CLSAnyLotEnviamentFitxer>
		    <CLSCodiOrigenFitxer1><xsl:value-of select="substring(text(),116,5)"/></CLSCodiOrigenFitxer1>
		    <CLSCodiOrigenFitxer2><xsl:value-of select="substring(text(),121,4)"/></CLSCodiOrigenFitxer2>
		    <CLSCodiAplicacioOrigenFitxer><xsl:value-of select="substring(text(),125,4)"/></CLSCodiAplicacioOrigenFitxer>
		    <CLSCodiProcesExecutarDestinacioFitxer><xsl:value-of select="substring(text(),129,12)"/></CLSCodiProcesExecutarDestinacioFitxer>
		    <CLSCodiDestinacioFitxer1><xsl:value-of select="substring(text(),141,5)"/></CLSCodiDestinacioFitxer1>
		    <CLSCodiDestinacioFitxer2><xsl:value-of select="substring(text(),146,4)"/></CLSCodiDestinacioFitxer2>
		    <CLSCodiAplicacioDestinacioFitxer><xsl:value-of select="substring(text(),150,4)"/></CLSCodiAplicacioDestinacioFitxer>
		    <CLSNumeroLotEnviamentFitxer><xsl:value-of select="substring(text(),154,6)"/></CLSNumeroLotEnviamentFitxer>
		    <CIFEntitatProveidora><xsl:value-of select="substring(text(),160,9)"/></CIFEntitatProveidora>
		    <NumeroFactura><xsl:value-of select="substring(text(),169,10)"/></NumeroFactura>
		    <TipusFactura><xsl:value-of select="substring(text(),179,1)"/></TipusFactura>
		    <NumeroFacturaOrigen><xsl:value-of select="substring(text(),180,10)"/></NumeroFacturaOrigen>
		    <CodiContracte><xsl:value-of select="substring(text(),190,11)"/></CodiContracte>
		    <IndicadorSigneImport><xsl:value-of select="substring(text(),233,1)"/></IndicadorSigneImport>
		    <ImportTotalFacturat><xsl:value-of select="substring(text(),234,14)"/></ImportTotalFacturat>
		    <DataEmissioFactura><xsl:value-of select="substring(text(),248,8)"/></DataEmissioFactura>
		</ActividadHeader>
	</xsl:template>
	
	<xsl:template match="/file/line[position()>1]">
	    <ActividadDetails>
		<!-- TODO: Auto-generated template -->
            <TipusRegistre><xsl:value-of select="substring(text(),1,1)"/></TipusRegistre>
            <NumeroLiniaFactura><xsl:value-of select="substring(text(),8,6)"/></NumeroLiniaFactura>
            <NumeroClausula><xsl:value-of select="substring(text(),14,3)"/></NumeroClausula>
            <TipusRegimEconomic><xsl:value-of select="substring(text(),22,2)"/></TipusRegimEconomic>
            <CodiUPOrigen><xsl:value-of select="substring(text(),24,5)"/></CodiUPOrigen>
            <CodiComunitatConveniColectiu><xsl:value-of select="substring(text(),29,3)"/></CodiComunitatConveniColectiu>
            <CIPPacient><xsl:value-of select="substring(text(),32,14)"/></CIPPacient>
            <TipusCodiIdentificacioSanitari><xsl:value-of select="substring(text(),46,2)"/></TipusCodiIdentificacioSanitari>
            <CodiIdentificacioSanitari><xsl:value-of select="substring(text(),48,25)"/></CodiIdentificacioSanitari>
            <PrimerCognom><xsl:value-of select="substring(text(),83,20)"/></PrimerCognom>
            <SegonCognom><xsl:value-of select="substring(text(),103,20)"/></SegonCognom>
            <Sexe><xsl:value-of select="substring(text(),123,1)"/></Sexe>
            <DataNaixement><xsl:value-of select="substring(text(),124,8)"/></DataNaixement>
            <NumeroHistoriaClinica><xsl:value-of select="substring(text(),132,8)"/></NumeroHistoriaClinica>
            <NumeroAssistencia><xsl:value-of select="substring(text(),140,9)"/></NumeroAssistencia>
            <LiniaServei><xsl:value-of select="substring(text(),149,1)"/></LiniaServei>
            <CodiServeiSanitari><xsl:value-of select="substring(text(),150,5)"/></CodiServeiSanitari>
            <CodiServeiAssistencial><xsl:value-of select="substring(text(),155,5)"/></CodiServeiAssistencial>
            <CodificacioProducte><xsl:value-of select="substring(text(),160,10)"/></CodificacioProducte>
            <PerfilPacient><xsl:value-of select="substring(text(),170,1)"/></PerfilPacient>
            <NumeroLíniaActivitatOrigen><xsl:value-of select="substring(text(),171,6)"/></NumeroLíniaActivitatOrigen>
            <DataInici><xsl:value-of select="substring(text(),177,8)"/></DataInici>
            <HoraInici><xsl:value-of select="substring(text(),185,4)"/></HoraInici>
            <DataFinalitzacio><xsl:value-of select="substring(text(),189,8)"/></DataFinalitzacio>
            <HoraFinalitzacio><xsl:value-of select="substring(text(),197,4)"/></HoraFinalitzacio>
            <QuantitatActivitat><xsl:value-of select="substring(text(),217,6)"/></QuantitatActivitat>
            <Import><xsl:value-of select="substring(text(),223,14)"/></Import>
            <MotiuAlta><xsl:value-of select="substring(text(),237,2)"/></MotiuAlta>
		</ActividadDetails>
	</xsl:template>

</xsl:stylesheet>
