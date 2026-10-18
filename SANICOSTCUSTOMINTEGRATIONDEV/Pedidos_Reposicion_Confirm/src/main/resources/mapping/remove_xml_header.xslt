<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- this is useful for xml files like partial html code -->
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

    <!-- add the rest of your transformation here... -->
    <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  
</xsl:stylesheet>