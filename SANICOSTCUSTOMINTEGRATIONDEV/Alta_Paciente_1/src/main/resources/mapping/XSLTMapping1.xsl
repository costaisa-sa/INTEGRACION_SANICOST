<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="Cama"/>
    <xsl:param name="ServicioRealizadorID"/>
    <xsl:param name="MotivoAltaID"/>
    <xsl:param name="FechaHoraInicio"/>
    <xsl:param name="FechaHoraFin"/>
    <xsl:param name="Cantidad"/>
    <!--<xsl:param name="MedicoAutofacturaID"/>
        <xsl:param name="MedicoResponsableID"/> 
        <xsl:param name="MedicoRealizadorID"/>-->
    <xsl:param name="MedicoResponsableBP"/>    
    <xsl:param name="DeudorIDGarante"/>
    <xsl:param name="DeudorExtID"/>
    <xsl:param name="TipoPrestacionID"/>
    <xsl:param name="PlanCoberturaID"/>
    <xsl:param name="DescPrestaExt"/>
    <xsl:param name="PrestacionExtID"/>
    <xsl:param name="CodigoSanitario"/>
	
    <xsl:mode on-no-match="shallow-copy"/>
	<xsl:template match="//Activities/Cama">
	     <Cama>
            <xsl:value-of select="$Cama"/>
        </Cama>
    </xsl:template>
    <xsl:template match="//Activities/ServicioRealizadorID">
        <ServicioRealizadorID>
            <xsl:value-of select="$ServicioRealizadorID"/>
        </ServicioRealizadorID>
    </xsl:template>
    <xsl:template match="//Activities/MedicoRealizadorID">
        <MedicoRealizadorID>
            <xsl:value-of select="$MedicoResponsableBP"/>
        </MedicoRealizadorID>
    </xsl:template>
    <xsl:template match="//Activities/MotivoAltaID">
        <MotivoAltaID>
            <xsl:value-of select="$MotivoAltaID"/>
        </MotivoAltaID>
    </xsl:template>
    <xsl:template match="//Activities/FechaHoraInicio">
        <FechaHoraInicio>
            <xsl:value-of select="$FechaHoraInicio"/>
        </FechaHoraInicio>
    </xsl:template>
    <xsl:template match="//Activities/FechaHoraFin">
        <FechaHoraFin>
            <xsl:value-of select="$FechaHoraFin"/>
        </FechaHoraFin>
    </xsl:template>
    <xsl:template match="//Activities/Cantidad">
        <!--<xsl:value-of select="$Cantidad"/>-->
        <Cantidad>1</Cantidad>
    </xsl:template>
    <xsl:template match="//Activities/MedicoAutofacturaID">
        <MedicoAutofacturaID>
            <xsl:value-of select="$MedicoResponsableBP"/>
        </MedicoAutofacturaID>
    </xsl:template>
    <xsl:template match="//Activities/MedicoResponsableID">
        <MedicoResponsableID>
            <xsl:value-of select="$MedicoResponsableBP"/>
        </MedicoResponsableID>
    </xsl:template>
    <xsl:template match="//Activities/Status">
        <!-->>>PRG 18.12.2020-->
	     <!--<Status>W</Status>-->
	     <Status><xsl:value-of select="."/></Status>
	    <!--<<<PRG 18.12.2020--> 
    </xsl:template>
    <xsl:template match="//Activities/PacienteExtID">
        <PacienteExtID>
            <xsl:value-of select="$DeudorExtID"/>
        </PacienteExtID>
    </xsl:template>
    <xsl:template match="//Activities/DeudorID">
        <DeudorID>
            <xsl:value-of select="$DeudorIDGarante"/>
        </DeudorID>
    </xsl:template>
    <xsl:template match="//Activities/TipoPrestacionID">
        <TipoPrestacionID>
            <xsl:value-of select="$TipoPrestacionID"/>
        </TipoPrestacionID>
    </xsl:template>
    <xsl:template match="//Activities/PrestacionExtID">
        <PrestacionExtID>
            <xsl:value-of select="$PrestacionExtID"/>
        </PrestacionExtID>
    </xsl:template>
    <xsl:template match="//Activities/DescPrestaExt">
        <DescPrestaExt>
            <xsl:value-of select="$DescPrestaExt"/>
        </DescPrestaExt>
    </xsl:template>  
    <xsl:template match="//Activities/PlanCoberturaID">
        <PlanCoberturaID>
            <xsl:value-of select="$PlanCoberturaID"/>
        </PlanCoberturaID>
    </xsl:template>
	<xsl:template match="//Activities/CodigoSanitario">
        <CodigoSanitario>
            <xsl:value-of select="$CodigoSanitario"/>
        </CodigoSanitario>
    </xsl:template>
</xsl:stylesheet>