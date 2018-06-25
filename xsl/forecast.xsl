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
    <xsl:template match="/">
        <xsl:element name="forecasts">
            <!--<xsl:element name="weather">-->
                <!--<xsl:element name="description">-->
                    <!--<xsl:value-of select="//div[@class='main-content-div']/section[1]/div/div[2]/p[1]/text()"/>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="windVelocity">-->
                    <!--<xsl:value-of select="normalize-space(substring(//div[@class='main-content-div']/section[1]/div/div[2]/p[2]/text()[3], 6, 13))"/>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="temp">-->
                    <!--<xsl:value-of select="//div[@class='main-content-div']/section[1]/div/div[2]/div[2]/text()"/>-->
                <!--</xsl:element>-->
                <!--<xsl:element name="icon">-->
                    <!--<xsl:value-of select="//div[@class='main-content-div']/section[1]/div/div[2]/img/@src"/>-->
                <!--</xsl:element>-->
            <!--</xsl:element>-->
            <xsl:for-each select="//table/tbody/tr[not(position()>7)]">
                <xsl:element name="forecast">
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
                        <xsl:value-of select="./td[1]/img/@src"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
