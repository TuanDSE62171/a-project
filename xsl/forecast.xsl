<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : capital.xsl
    Created on : June 13, 2018, 7:32 PM
    Author     : daotuan
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns="https://www.forecast.com" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" version="1.0"/>
    <xsl:variable name="icon_mapping" select="document('../icon_mapping.xml')"/>
    <xsl:template match="/">
        <xsl:element name="forecasts">
            <!--<xsl:element name="forecast">-->
                <!--<xsl:element name="forecastDayOfWeek"/>-->
                <!--<xsl:element name="forecastDay"/>-->
                <!--<xsl:element name="forecastDescription">-->
                    <!--<xsl:value-of select="//section[1]/div[1]/div[2]/p[1]/text()"/>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="forecastWind">-->
                    <!--<xsl:value-of select="normalize-space(substring-after(//section[1]/div[1]/div[2]/p[2]/text()[3], ' '))"/>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="forecastTemp">-->
                    <!--<xsl:value-of select="substring-after(substring-after(//section[1]/div[1]/div[2]/p[2]/text()[1], ' '), ' ')"/>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="forecastIcon">-->
                    <!--<xsl:variable name="parent" select="//section[1]/div[1]/div[2]/div[2]/img/@src"/>-->
                    <!--<xsl:for-each select="$icon_mapping/mappings/mapping">-->
                        <!--<xsl:if test="./from[text() = $parent]">-->
                            <!--<xsl:value-of select="./to/text()"/>-->
                        <!--</xsl:if>-->
                    <!--</xsl:for-each>-->
                <!--</xsl:element>-->
            <!--</xsl:element>-->
            <xsl:for-each select="//table/tbody/tr[not(position()>7)]">
                <xsl:element name="forecast">
                    <xsl:element name="forecastDayOfWeek"/>
                    <xsl:element name="forecastDay"/>
                    <xsl:element name="forecastDescription">
                        <xsl:value-of select="./td[3]/text()"/>
                    </xsl:element>
                    <xsl:element name="forecastWind">
                        <xsl:value-of select="./td[5]/text()"/>
                    </xsl:element>
                    <xsl:element name="forecastTemp">
                        <xsl:value-of select="./td[4]/text()"/>
                    </xsl:element>
                    <xsl:element name="forecastIcon">
                        <xsl:variable name="parent" select="."/>
                        <xsl:for-each select="$icon_mapping/mappings/mapping">
                            <xsl:if test="./from[text() = $parent/td[1]/img/@src]">
                                <xsl:value-of select="./to/text()"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
