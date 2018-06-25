<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes"/>

    <!--<xsl:template match="/">-->
    <!--<xsl:apply-templates/>-->
    <!--</xsl:template>-->

    <xsl:template match="/*[local-name()='forecasts']">
        <div class="today-temperature">
            <div class="today-temperature-number">
                <span>
                    <xsl:value-of select="//*[local-name()='forecast'][1]/*[local-name()='forecastTemp']"/>
                </span>
                <span>°</span>
                <div>
                    <xsl:value-of select="//*[local-name()='forecast'][1]/*[local-name()='forecastDayOfWeek']"/> ngày
                    <xsl:value-of select="//*[local-name()='forecast'][1]/*[local-name()='forecastDay']"/>
                </div>
            </div>
            <div class="today-temperature-icon">
                <i class="icon ion-md-rainy"></i>
                <span>
                    <xsl:value-of select="//*[local-name()='forecast'][1]/*[local-name()='forecastWind']"/>
                </span>
            </div>
        </div>
        <xsl:for-each select="//*[local-name()='forecast'][position() != 1]">
            <div class="temperature">
                <div class="temperature-day">
                    <span>
                        <xsl:value-of select="./*[local-name()='forecastDayOfWeek']"/>
                    </span>
                </div>
                <i class="icon ion-md-cloud"></i>
                <span>
                    <xsl:value-of select="./*[local-name()='forecastTemp']"/>°
                </span>
            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>