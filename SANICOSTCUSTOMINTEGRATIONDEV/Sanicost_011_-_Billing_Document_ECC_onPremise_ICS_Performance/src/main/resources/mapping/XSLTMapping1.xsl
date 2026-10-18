<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:yy="http://SAPCustomFields.com/YY1_">
    <xsl:param name="NHC"/>
    <xsl:param name="FAct"/>
    <xsl:param name="Paciente"/>
    <xsl:mode on-no-match="shallow-copy"/>
    <xsl:template match="YY1_SDHISTORIA">
        <yy:YY1_SDHISTORIA>
         <xsl:value-of select="$NHC"/>
       </yy:YY1_SDHISTORIA>
    </xsl:template>
    <xsl:template match="YY1_SDPACIENTE">
        <yy:YY1_SDPACIENTE>
             <xsl:value-of select="$Paciente"/>
        </yy:YY1_SDPACIENTE>
    </xsl:template>
    <xsl:template match="YY1_SDFECHAACTIVIDAD">
        <yy:YY1_SDFECHAACTIVIDAD>
             <xsl:value-of select="$FAct"/>
        </yy:YY1_SDFECHAACTIVIDAD>
    </xsl:template>
</xsl:stylesheet>