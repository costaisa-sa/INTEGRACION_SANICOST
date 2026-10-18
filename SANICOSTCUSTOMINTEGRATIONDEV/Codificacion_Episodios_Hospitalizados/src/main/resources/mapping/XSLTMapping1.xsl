<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="Cama"/>
    <xsl:param name="ServicioRealizadorID"/>
    <xsl:param name="MotivoAltaID"/>
    <!--<xsl:param name="FechaHoraInicio"/>
    <xsl:param name="FechaHoraFin"/> -->
    <!--<xsl:param name="Cantidad"/> -->
    <!--<xsl:param name="MedicoAutofacturaID"/>
        <xsl:param name="MedicoResponsableID"/>  
        <xsl:param name="MedicoRealizadorID"/> -->
    <xsl:param name="MedicoResponsableBP"/> 
    <xsl:param name="DeudorIDGarante"/>
    <xsl:param name="DeudorExtID"/>
    <xsl:param name="TipoPrestacionID"/>
    <xsl:param name="PlanCoberturaID"/>
    <xsl:param name="DescPrestaExt"/>
    <xsl:param name="PrestacionExtID"/>
    <xsl:param name="Cirujano"/>
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
    <xsl:template match="//ActivitiesType/MotivoAltaID">
        <MotivoAltaID>
            <xsl:value-of select="$MotivoAltaID"/>
        </MotivoAltaID>
    </xsl:template>
    <!--<xsl:template match="//ActivitiesType/FechaHoraInicio">
        <FechaHoraInicio>
            <xsl:value-of select="$FechaHoraInicio"/>
        </FechaHoraInicio>
    </xsl:template>
    <xsl:template match="//ActivitiesType/FechaHoraFin">
        <FechaHoraFin>
            <xsl:value-of select="$FechaHoraFin"/>
        </FechaHoraFin>
    </xsl:template>  
    <xsl:template match="//ActivitiesType/Cantidad">
        <Cantidad>
            <xsl:value-of select="$Cantidad"/>
        </Cantidad>
    </xsl:template> -->
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
    <xsl:template match="//ActivitiesType/Status">
        <xsl:choose>
            <xsl:when test="$DeudorIDGarante!='S99'">
                <Status>P</Status>
            </xsl:when>
            <xsl:otherwise>
                <Status>N</Status>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="//ActivitiesType/PacienteExtID">
        <PacienteExtID>
            <xsl:value-of select="$DeudorExtID"/>
        </PacienteExtID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/DeudorID">
        <DeudorID>
            <xsl:value-of select="$DeudorIDGarante"/>
        </DeudorID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/TipoPrestacionID">
        <TipoPrestacionID>
            <xsl:value-of select="$TipoPrestacionID"/>
        </TipoPrestacionID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/PrestacionExtID">
        <PrestacionExtID>
            <xsl:value-of select="$PrestacionExtID"/>
        </PrestacionExtID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/DescPrestaExt">
        <DescPrestaExt>
            <xsl:value-of select="$DescPrestaExt"/>
        </DescPrestaExt>
    </xsl:template>  
    <xsl:template match="//ActivitiesType/PlanCoberturaID">
        <PlanCoberturaID>
            <xsl:value-of select="$PlanCoberturaID"/>
        </PlanCoberturaID>
    </xsl:template>
    <xsl:template match="//ActivitiesType/CirujanoJefeID">
        <CirujanoJefeID>
            <xsl:value-of select="$Cirujano"/>
        </CirujanoJefeID>
    </xsl:template>
</xsl:stylesheet>