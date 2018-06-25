<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : capital.xsl
    Created on : June 13, 2018, 7:32 PM
    Author     : daotuan
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns="https://www.capitals.com" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" version="1.0"/>
        <xsl:template match="/">
        <xsl:element name="capitals">
            <xsl:for-each select="table/tr[position() != 1 and count(td)!=1]">
                <xsl:if test="td[4][text() != '-']">
                    <xsl:element name="capital">
                        <xsl:attribute name="countryName">
                            <xsl:value-of select="td[1]/a/text()"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:attribute>
                        <xsl:attribute name="iso2Code">
                            <xsl:value-of select="td[4]/text()"/>
                        </xsl:attribute>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
