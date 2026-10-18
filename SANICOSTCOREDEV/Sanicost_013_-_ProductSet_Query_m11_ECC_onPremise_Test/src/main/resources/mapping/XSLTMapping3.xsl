<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<!-- TODO: Auto-generated template -->
		<ProductSet>
		    <xsl:apply-templates/>
		</ProductSet>    
	</xsl:template>
	
	<xsl:template match="A_ProductDescriptionType">
	    <ProductType>
            <Product><xsl:value-of select="Product/."/></Product>
		    <ProductDescription><xsl:value-of select="ProductDescription/."/></ProductDescription>
		</ProductType>
    </xsl:template>

<!--<ProductSet>
	<ProductType>
		<to_Description>
			<A_ProductDescriptionType>
				<Product>100135</Product>
				<ProductDescription>OXICODONA/NALOXONA 5/2,5 MG COMP</ProductDescription>
			</A_ProductDescriptionType>
		</to_Description>
	</ProductType>
</ProductSet>-->


</xsl:stylesheet>
