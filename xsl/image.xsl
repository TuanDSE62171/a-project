<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : capital.xsl
    Created on : June 13, 2018, 7:32 PM
    Author     : daotuan
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns="https://www.images.com" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" version="1.0"/>
    <xsl:template match="/">
        <xsl:element name="images">
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
            <xsl:for-each select="//div[@class='y5w1y']/div/figure/a/div/img">
                <xsl:element name="image">
                    <xsl:element name="iso2Code"/>
                    <xsl:element name="width"/>
                    <xsl:element name="height"/>
                    <xsl:element name="url">
                        <xsl:value-of select="./@src"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
