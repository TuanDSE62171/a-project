<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : tintucTemplate.xsl
    Created on : June 13, 2018, 3:08 PM
    Author     : daotuan
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns="https://www.news.com" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" version="1.0"/>
    <xsl:template match="/">
        <xsl:element name="stories">
            <xsl:for-each select="div/div[@class='media' and div[@class='media-left']]">
                <xsl:element name="news">
                    <xsl:element name="postName">
                        <xsl:value-of select="div/a/img[@class='thumbnail']/@alt"/>
                    </xsl:element>
                    <xsl:element name="postImgUrl">
                        <xsl:value-of select="div/a/img[@class='thumbnail']/@src"/>
                    </xsl:element>
                    <xsl:element name="postOriginUrl">
                        <xsl:value-of select="div[2]/h3/a/@href"/>
                    </xsl:element>
                    <xsl:element name="postDateTime">
                        <xsl:value-of select="div[2]/p[@class='smaller']/span[@class='dim']/@datetime"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
