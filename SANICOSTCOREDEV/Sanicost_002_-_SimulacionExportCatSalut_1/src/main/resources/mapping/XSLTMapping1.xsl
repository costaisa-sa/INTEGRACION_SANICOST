<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" encoding="iso-8859-1"/>
	
	<xsl:variable name="blank"      select="'                                                  '"/>
	<xsl:variable name="blank_zero" select="'0000000000000000000'"/>
	<!--<xsl:variable name="TipoFacturacID" select="'Z2'"/>-->
	<xsl:param name="TipoFacturacID"/>
	 
    <xsl:variable name='newline'><xsl:text>&#x0A;</xsl:text></xsl:variable> 

    <xsl:template match="/">
		<xsl:apply-templates select="//ActividadMessageSet/ActividadMessageSet"/>
		<xsl:value-of select="$newline"/>
		<xsl:for-each select="//ActividadMessageDetailsAfterSaveValidSet">
			<xsl:sort select="TipusRegistre"/>
			
			<xsl:if test = "TipusRegistre = 2" > <!--Linea sin identificacion-->
				<xsl:value-of select="substring(concat(TipusRegistre,$blank),
				0,
				2)"/>
				
				<xsl:value-of select="substring($blank,
				0,
				7)"/>
				
				<xsl:value-of select="substring(concat($blank_zero,NumeroLiniaFactura),
				string-length($blank_zero)+string-length(NumeroLiniaFactura)-5),
				string-length($blank_zero)+string-length(NumeroLiniaFactura)"/>
				
				<xsl:value-of select="substring(concat($blank_zero,NumeroClausula),
    				string-length($blank_zero)+string-length(NumeroClausula)-2),
    				string-length($blank_zero)+string-length(NumeroClausula)"/>
				
				<xsl:value-of select="substring(concat(LotClausula,$blank),
				0,
				6)"/>
				
				<xsl:value-of select="substring(concat($blank_zero,TipusRegimEconomic),
				string-length($blank_zero)+string-length(TipusRegimEconomic)-1),
				string-length($blank_zero)+string-length(TipusRegimEconomic)"/>	
				
				<xsl:value-of select="substring(concat($blank_zero,CodiServeiSanitari),
				string-length($blank_zero)+string-length(CodiServeiSanitari)-5),
				string-length($blank_zero)+string-length(CodiServeiSanitari)"/>	
				
			    <xsl:value-of select="substring(concat(DataInici,$blank),
				0,
				9)"/>	
				
    			<!--<xsl:value-of select="substring(concat(HoraInici,$blank),
    				0,
    				5)"/>	-->
    
				<xsl:value-of select="substring(concat(DataFinalitzacio,$blank),
				0,
				9)"/>	
				
    			<!--<xsl:value-of select="substring(concat(HoraFinalitzacio,$blank),
    				0,
    				5)"/>-->
				
				<xsl:value-of select="substring(concat($blank_zero,QuantitatActivitat),
				string-length($blank_zero)+string-length(QuantitatActivitat)-5),
				string-length($blank_zero)+string-length(QuantitatActivitat)"/>	
				
				<xsl:value-of select="substring(concat($blank_zero,Import),
				string-length($blank_zero)+string-length(Import)-13),
				string-length($blank_zero)+string-length(Import)"/>	
				
				<xsl:value-of select="substring($blank,
				0,
				9)"/>
				
				<xsl:value-of select="substring(concat(NumeroAssistencia,$blank),
				0,
				10)"/>
				
				<xsl:value-of select="substring(concat($blank_zero,CodiUPOrigen),
				string-length($blank_zero)+string-length(CodiUPOrigen)-4),
					string-length($blank_zero)+string-length(CodiUPOrigen)"/>
				
				<xsl:value-of select="substring(concat(NumeroHistoriaClinica,$blank),
				0,
				11)"/>	
				
				<!--<xsl:value-of select="substring($blank,
				0,
				402)"/>-->
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="$blank"/>
				<xsl:value-of select="substring($blank,
				0,
				4)"/>
				
				<xsl:value-of select="$newline"/>
				
			</xsl:if> 
			
			<!--linea de ActividadCatSalut-->
			<!-->>>PRG 19.02.2021 Hay más tipos de lineas. Ha de aplicar esta plantilla si la linea tiene identificacion personal-->
			<!--<xsl:if test = "TipusRegistre = 1" > -->
			<xsl:if test = "TipusRegistre != 2" >
			<!--<<<PRG 19.02.2021-->

    			<!-- TODO: Auto-generated template -->
    			 <xsl:value-of select="substring(concat(TipusRegistre,$blank),
    				0,
    				2)"/>
    				
    			<xsl:value-of select="substring($blank,
    				0,
    				7)"/>
    			
    			<!-->>>PRG 19.02.2021-->
    			<!--Si es abono o reasignacion, poner un contador
    			<xsl:value-of select="substring(concat($blank_zero,NumeroLiniaFactura),
    				string-length($blank_zero)+string-length(NumeroLiniaFactura)-5),
    				string-length($blank_zero)+string-length(NumeroLiniaFactura)"/>-->
    			
    			<xsl:choose>
    			  <!--ActividadCatSalut-->
				  <xsl:when test="TipusRegistre = '1'"><xsl:value-of select="substring(concat($blank_zero,NumeroLiniaFactura),
    				string-length($blank_zero)+string-length(NumeroLiniaFactura)-5),
    				string-length($blank_zero)+string-length(NumeroLiniaFactura)"/></xsl:when>
    				
    			  <!--Abono/Reasignacion-->	
				  <xsl:otherwise><xsl:value-of select="substring(concat($blank_zero,position()),
    				string-length($blank_zero)+string-length(position())-5),
    				string-length($blank_zero)+string-length(position())"/></xsl:otherwise>
				</xsl:choose>
				<!--<<<PRG 19.02.2021-->
    				
                <xsl:value-of select="substring(concat($blank_zero,NumeroClausula),
    				string-length($blank_zero)+string-length(NumeroClausula)-2),
    				string-length($blank_zero)+string-length(NumeroClausula)"/>
    			
    			<xsl:value-of select="substring(concat(LotClausula,$blank),
    				0,
    				6)"/>
    			
    			<xsl:value-of select="substring(concat($blank_zero,TipusRegimEconomic),
    				string-length($blank_zero)+string-length(TipusRegimEconomic)-1),
    				string-length($blank_zero)+string-length(TipusRegimEconomic)"/>	
    
    				
    			<xsl:value-of select="substring(concat($blank_zero,CodiUPOrigen),
    				string-length($blank_zero)+string-length(CodiUPOrigen)-4),
    				string-length($blank_zero)+string-length(CodiUPOrigen)"/>
    				
    			<xsl:value-of select="substring(concat(CodiComunitatConveniColectiu,$blank),
    				0,
    				4)"/>		
    			
    			<xsl:value-of select="substring(concat(CIPPacient,$blank),
    				0,
    				15)"/>		
    				
    			<!--<xsl:value-of select="substring(concat($blank_zero,TipusCodiIdentificacioSanitari),
    				string-length($blank_zero)+string-length(TipusCodiIdentificacioSanitari)-1),
    				string-length($blank_zero)+string-length(TipusCodiIdentificacioSanitari)"/>-->	
    			<xsl:value-of select="substring(concat(TipusCodiIdentificacioSanitari,$blank),
    				0,
    				3)"/>
    			
    			<xsl:value-of select="substring(concat(CodiIdentificacioSanitari,$blank),
    				0,
    				26)"/>	
    				
    			<xsl:value-of select="substring(concat(NumeroHistoriaClinica,$blank),
    				0,
    				11)"/>
    				
    			<xsl:value-of select="substring(concat(PrimerCognom,$blank),
    				0,
    				21)"/>
    			
    			<xsl:value-of select="substring(concat(SegonCognom,$blank),
    				0,
    				21)"/>		
    			
    			<xsl:value-of select="substring(concat(Sexe,$blank),
    				0,
    				2)"/>
    					
    			<xsl:value-of select="substring(concat(DataNaixement,$blank),
    				0,
    				9)"/>
    		
    		    <xsl:value-of select="substring($blank,0,9)"/>
    
    			<xsl:value-of select="substring(concat(NumeroAssistencia,$blank),
    				0,
    				10)"/>
    				
    			<xsl:value-of select="substring(concat($blank_zero,CodiServeiSanitari),
    				string-length($blank_zero)+string-length(CodiServeiSanitari)-5),
    				string-length($blank_zero)+string-length(CodiServeiSanitari)"/>	
    				
    			<xsl:value-of select="substring(concat($blank_zero,CodiServeiAssistencial),
    				string-length($blank_zero)+string-length(CodiServeiAssistencial)-4,
    				string-length($blank_zero)+string-length(CodiServeiAssistencial))"/>
    
    			<xsl:value-of select="substring(concat(CodificacioProducte,$blank),
    				0,
    				11)"/>		
    
    			<!--<xsl:value-of select="substring(concat($blank_zero,PerfilPacient),
    				string-length($blank_zero)+string-length(PerfilPacient)-0),
    				string-length($blank_zero)+string-length(PerfilPacient)"/>	-->
    			<xsl:value-of select="substring(concat(PerfilPacient,$blank),
    				0,
    				2)"/>	
    			
	            <xsl:choose>
    			  <!--ActividadCatSalut-->
				  <xsl:when test="TipusRegistre = '1'"><xsl:value-of select="substring($blank,0,7)"/></xsl:when>
    				
    			  <!--Abono/Reasignacion-->	
				  <xsl:otherwise><xsl:value-of select="substring(concat($blank_zero,NumeroLiniaFacturaReferencia),
    				string-length($blank_zero)+string-length(NumeroLiniaFacturaReferencia)-5),
    				string-length($blank_zero)+string-length(NumeroLiniaFacturaReferencia)"/></xsl:otherwise>
				</xsl:choose>
				      
	           
    			<xsl:value-of select="substring(concat(DataInici,$blank),
    				0,
    				9)"/>	
    
    			<xsl:value-of select="substring(concat(HoraInici,$blank),
    				0,
    				5)"/>	
    
    			<xsl:value-of select="substring(concat(DataFinalitzacio,$blank),
    				0,
    				9)"/>	
    
    			<xsl:value-of select="substring(concat(HoraFinalitzacio,$blank),
    				0,
    				5)"/>				
    				
    			<xsl:value-of select="substring(concat(DataIniciTractament,$blank),
    				0,
    				9)"/>	
    			
    			<xsl:value-of select="substring(concat(DataFiTractament,$blank),
    				0,
    				9)"/>	
    
    			<xsl:value-of select="substring(concat($blank_zero,QuantitatActivitat),
    				string-length($blank_zero)+string-length(QuantitatActivitat)-5),
    				string-length($blank_zero)+string-length(QuantitatActivitat)"/>	
    			
    			<xsl:value-of select="substring(concat($blank_zero,Import),
    				string-length($blank_zero)+string-length(Import)-13),
    				string-length($blank_zero)+string-length(Import)"/>	
    				
    			<xsl:value-of select="substring(concat(MotiuAlta,$blank),
    				0,
    				3)"/>	
    			
    			<xsl:value-of select="substring(concat(GrupTecnica,$blank),
    				0,
    				4)"/>
    				
    			<xsl:value-of select="substring(concat(MatriculaVehicle,$blank),
    				0,
    				9)"/>
    			 
    			<xsl:value-of select="substring(concat(Quilometratge,$blank),
    				0,
    				6)"/>
    
    			<xsl:value-of select="substring(concat(OrigenTrasllat,$blank),
    				0,
    				2)"/>
    			
    			<xsl:value-of select="substring(concat(CodiUPOrigenTrasllat,$blank),
    				0,
    				6)"/>
    			
    			<xsl:value-of select="substring(concat(DestiTrasllat,$blank),
    				0,
    				2)"/>
    			
    			<xsl:value-of select="substring(concat(CodiUPDestiTrasllat,$blank),
    				0,
    				6)"/>
    			
    			<xsl:value-of select="substring(concat(CodiLocalitatOrigenTrasllat,$blank),
    				0,
    				8)"/>
    				
    			<xsl:value-of select="substring(concat(CodiLocalitatDestiTrasllat,$blank),
    				0,
    				8)"/>
    			
    			<xsl:value-of select="substring(concat(TempsEspera,$blank),
    				0,
    				2)"/>
    				
    			<xsl:value-of select="substring($blank,
    				0,
    				5)"/>
    
    			<xsl:value-of select="substring(concat(CodiProcesRehabilitacio,$blank),
    				0,
    				5)"/>
    				
    			<xsl:value-of select="substring(concat(CodiAparellOrtoprotetic,$blank),
    				0,
    				7)"/>
    				
    			<xsl:value-of select="substring(concat(TipusCodiIdentificacioSanitariAct,$blank),
    				0,
    				3)"/>
    				
    			<xsl:value-of select="substring(concat(CodiIdentificacioSanitariAct,$blank),
    			   0,
    			   26)"/>
    				
                <xsl:value-of select="substring(concat(CMBDH,$blank),
    				0,
    				26)"/>
    				
    			<xsl:value-of select="$blank"/>
    			<xsl:value-of select="$blank"/>
    			<xsl:value-of select="$blank"/>
    			<xsl:value-of select="substring($blank,0,4)"/>
    				
    			<xsl:value-of select="$newline"/>
			</xsl:if>
		</xsl:for-each>
    </xsl:template>

      <xsl:template match="//ActividadMessageSet/ActividadMessageSet">
			
        <xsl:value-of select="substring(concat(TipusRegistre,$blank),
			0,
			2)"/>
		
        <xsl:value-of select="substring(concat($blank_zero,TipusOrganitzacio),
			string-length($blank_zero)+string-length(TipusOrganitzacio)-1),
			string-length($blank_zero)+string-length(TipusOrganitzacio)"/>
		
        <xsl:value-of select="substring(concat($blank_zero,CodiUnitatProveidora),
			string-length($blank_zero)+string-length(CodiUnitatProveidora)-4),
			string-length($blank_zero)+string-length(CodiUnitatProveidora)"/>
		
		<xsl:value-of select="substring($blank,
			0,
			11)"/>
			
		<xsl:value-of select="substring(concat(Aplicacio,$blank),
			0,
			4)"/>
		
		<xsl:value-of select="substring($blank,
			0,
			6)"/>
		
	        <xsl:value-of select="substring(concat(TipusFitxer,$blank),
			0,
                        2)"/>
		
		<xsl:value-of select="substring(concat(DataIniciPeriode,$blank),
			0,
                        9)"/>
		
		<xsl:value-of select="substring(concat(DataFinalPeriode,$blank),
			0,
			9)"/>
		
		<xsl:value-of select="substring(concat(DataHoraCreacioFitxer,$blank),
			0,
			15)"/>
		
		<xsl:value-of select="substring(concat($blank_zero,NombreTotalRegistres),
			string-length($blank_zero)+string-length(NombreTotalRegistres)-7),
			string-length($blank_zero)+string-length(NombreTotalRegistres)"/>
			
		<xsl:value-of select="substring($blank,
			0,
			47)"/>
		
		<xsl:value-of select="substring(concat($blank_zero,CLSAnyLotEnviamentFitxer),
			string-length($blank_zero)+string-length(CLSAnyLotEnviamentFitxer)-3),
			string-length($blank_zero)+string-length(CLSAnyLotEnviamentFitxer)"/>
			
		<xsl:value-of select="substring(concat(CLSCodiOrigenFitxer1,$blank),
			0,
			6)"/>
			
		<xsl:value-of select="substring(concat(CLSCodiOrigenFitxer2,$blank),
			0,
			5)"/>
		
		<xsl:value-of select="substring(concat(CLSCodiAplicacioOrigenFitxer,$blank),
			0,
			5)"/>
		
		
		<xsl:value-of select="substring(concat(CLSCodiAplicacioDestinacioFitxer,$blank),
			0,
			13)"/>
		
		<xsl:value-of select="substring(concat(CLSCodiDestinacioFitxer1,$blank),
			0,
			6)"/>
		
		<xsl:value-of select="substring(concat(CLSCodiDestinacioFitxer2,$blank),
			0,
			5)"/>
			
		<xsl:value-of select="substring(concat(CLSCodiProcesExecutarDestinacioFitxer,$blank),
			0,
			5)"/>	

		<xsl:value-of select="substring(concat($blank_zero,CLSNumeroLotEnviamentFitxer),
			string-length($blank_zero)+string-length(CLSNumeroLotEnviamentFitxer)-5),
			string-length($blank_zero)+string-length(CLSNumeroLotEnviamentFitxer)"/>
		
		<xsl:value-of select="substring(concat(CIFEntitatProveidora,$blank),
			0,
			10)"/>	
			
		<xsl:value-of select="substring(concat(NumeroFactura,$blank),
			0,
			11)"/>
			
		<xsl:value-of select="substring(concat(TipusFactura,$blank),
			0,
			2)"/>		
			
		<xsl:value-of select="substring(concat(NumeroFacturaOrigen,$blank),
			0,
			11)"/>
			
		<xsl:value-of select="substring(concat(CodiContracte,$blank),
			0,
			12)"/>
			
		<xsl:value-of select="substring($blank,
			0,
			33)"/>
			
		<xsl:value-of select="substring(concat($blank,IndicadorSigneImport),
			string-length($blank)+string-length(IndicadorSigneImport)),
			string-length($blank)+string-length(IndicadorSigneImport)"/>
		
		<xsl:value-of select="substring(concat($blank_zero,ImportTotalFacturat),
			string-length($blank_zero)+string-length(ImportTotalFacturat)-13),
			string-length($blank_zero)+string-length(ImportTotalFacturat)"/>
			
		<xsl:value-of select="substring(concat(DataEmissioFactura,$blank),
			0,
			9)"/>
				
		<xsl:value-of select="$blank"/>
		<xsl:value-of select="$blank"/>
		<xsl:value-of select="$blank"/>
		<xsl:value-of select="$blank"/>
		<xsl:value-of select="substring($blank,0,44)"/>
			
    </xsl:template>

  </xsl:stylesheet>