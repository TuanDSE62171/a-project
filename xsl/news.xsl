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
            <xsl:for-each select="//div[@class='w-body list']/div[@class='media' and div[1][@class='media-left']]">
                <xsl:element name="news">
                    <xsl:element name="title">
                        <xsl:value-of select="./div[1]/a/@title"/>
                    </xsl:element>
                    <xsl:element name="imgUrl">
                        <xsl:value-of select="./div[1]/a/img/@src"/>
                    </xsl:element>
                    <xsl:element name="originUrl">
                        <xsl:value-of select="./div[1]/a/@href"/>
                    </xsl:element>
                    <xsl:element name="isHotNews">
                        <xsl:value-of select="boolean(./div[2]/h3/i[@class='ico hot ico_status'])"/>
                    </xsl:element>
                    <xsl:element name="date">
                        <xsl:value-of select="substring-before(normalize-space(./div[2]/p/span/@datetime), ' ')"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
