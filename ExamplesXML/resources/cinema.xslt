<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/cinema">
		<root>
			<xsl:apply-templates select="movie" />
		</root>
	</xsl:template>
	<xsl:template match="movie">
		<movie rated="{@rating}">
			<xsl:value-of select="title" />
		</movie>
	</xsl:template>
</xsl:stylesheet>