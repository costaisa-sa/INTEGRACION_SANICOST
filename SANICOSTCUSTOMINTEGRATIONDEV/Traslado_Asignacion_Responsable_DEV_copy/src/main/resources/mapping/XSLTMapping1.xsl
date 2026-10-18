<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="Cama"/>
    <xsl:param name="ServicioRealizadorID"/>
    <xsl:param name="FechaHoraInicio"/>
     <!--<xsl:param name="MedicoAutofacturaID"/>
         <xsl:param name="MedicoResponsableID"/>
         <xsl:param name="MedicoRealizadorID"/>-->
    <xsl:param name="MedicoResponsableBP"/>    
    <!--<xsl:param name="DeudorExtID"/>-->
    <xsl:param name="TipoPrestacionID"/>
    <xsl:mode on-no-match="shallow-copy"/>
	<xsl:template match="//ActivitiesType/Cama">
	     <Cama>
            <xsl:value-of select="$Cama"/>
        </Cama>
    </xsl:template>
    <xsl:template match="//ActivitiesType/ServicioRealizadorID">
        <ServicioRealizadorID>
            <xsl:value-of select="$ServicioRealizadorID"/>
        </ServicioRealizadorID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/MedicoRealizadorID">
        <MedicoRealizadorID>
            <xsl:value-of select="$MedicoResponsableBP"/>
        </MedicoRealizadorID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/FechaHoraInicio">
        <FechaHoraInicio>
            <xsl:value-of select="$FechaHoraInicio"/>
        </FechaHoraInicio>
    </xsl:template>
    <xsl:template match="//ActivitiesType/MedicoAutofacturaID">
        <MedicoAutofacturaID>
            <xsl:value-of select="$MedicoResponsableBP"/>
        </MedicoAutofacturaID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/MedicoResponsableID">
        <MedicoResponsableID>
            <xsl:value-of select="$MedicoResponsableBP"/>
        </MedicoResponsableID>
    </xsl:template>
    <!--<xsl:template match="//ActivitiesType/PacienteExtID">
        <PacienteExtID>
            <xsl:value-of select="$DeudorExtID"/>
        </PacienteExtID>
    </xsl:template>-->
    <xsl:template match="//ActivitiesType/TipoPrestacionID">
        <TipoPrestacionID>
            <xsl:value-of select="$TipoPrestacionID"/>
        </TipoPrestacionID>
    </xsl:template>
</xsl:stylesheet>