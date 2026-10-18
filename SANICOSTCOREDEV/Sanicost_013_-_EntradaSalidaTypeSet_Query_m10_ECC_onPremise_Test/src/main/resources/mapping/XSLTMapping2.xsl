<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="Balance_inicial"/>
	<xsl:param name="Fecha_clave" />
	<xsl:param name="Fecha_inicial" />

	<xsl:template match="/YY1_Entradas_Estupes">

		<EntradaSalidaTypeSet>
			<EntradaSalidaType>
				<GoodsIssueQtyInBaseUnit></GoodsIssueQtyInBaseUnit>
				<YY1_Vale_PDI/>
				<Material/>
				<NHC/>
				<Orden_Medica/>
				<Medico_Prescriptor/>
				<MaterialBaseUnit/>
				<GoodsReceiptQtyInBaseUnit></GoodsReceiptQtyInBaseUnit>
				<SupplierName/>
				<ID>1</ID>
				<ProductDescription><xsl:value-of select="//YY1_Entradas_EstupesType[1]/ProductDescription/text()"/></ProductDescription>
				<DocumentDate>
					<xsl:value-of select="$Fecha_inicial"/>
				</DocumentDate>
				<Balance>
					<xsl:value-of select="$Balance_inicial"/>
				</Balance>
			</EntradaSalidaType>

			<xsl:iterate select="YY1_Entradas_EstupesType">

				<xsl:param name="Balance" select="$Balance_inicial" />
				<xsl:variable name="GoodsIssueQtyInBaseUnit" select="GoodsIssueQtyInBaseUnit"/>
				<xsl:variable name="GoodsReceiptQtyInBaseUnit" select="GoodsReceiptQtyInBaseUnit"/>
				<xsl:variable name="newBalance" select="sum($Balance - $GoodsIssueQtyInBaseUnit + GoodsReceiptQtyInBaseUnit)"/>

				<EntradaSalidaType>
					<GoodsIssueQtyInBaseUnit>
						<xsl:value-of select="$GoodsIssueQtyInBaseUnit"/>
					</GoodsIssueQtyInBaseUnit>
					<YY1_Vale_PDI>
						<xsl:value-of select="YY1_Vale_PDI"/>
					</YY1_Vale_PDI>
					<Material>
						<xsl:value-of select="Material"/>
					</Material>
					<NHC>
						<xsl:value-of select="substring-before(MaterialDocumentHeaderText,'/')"/>
					</NHC>
					<Orden_Medica>
						<xsl:value-of select="substring-after(substring-after(MaterialDocumentHeaderText, '/'),'/')"/>
					</Orden_Medica>
					<Medico_Prescriptor>
						<xsl:value-of select="substring-before(substring-after(MaterialDocumentHeaderText, '/'),'/')"/>
					</Medico_Prescriptor>
					<MaterialBaseUnit>
						<xsl:value-of select="MaterialBaseUnit"/>
					</MaterialBaseUnit>
					<GoodsReceiptQtyInBaseUnit>
						<xsl:value-of select="$GoodsReceiptQtyInBaseUnit"/>
					</GoodsReceiptQtyInBaseUnit>
					<SupplierName>
						<xsl:value-of select="SupplierName"/>
					</SupplierName>
					<ID>
						<xsl:value-of select="ID"/>
					</ID>
					<ProductDescription>
						<xsl:value-of select="ProductDescription"/>
					</ProductDescription>
					<DocumentDate>
						<xsl:value-of select="DocumentDate"/>
					</DocumentDate>
					<Balance>
						<xsl:value-of select="$newBalance"/>
					</Balance>
				</EntradaSalidaType>	  
				<xsl:next-iteration>
					<xsl:with-param name="Balance" select="$newBalance"/>
				</xsl:next-iteration>

			</xsl:iterate>

		</EntradaSalidaTypeSet>

	</xsl:template>
</xsl:stylesheet>
