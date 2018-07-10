<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/*[local-name()='forecasts']">
        <xsl:variable name="firstForecast" select="//*[local-name()='forecast'][1]"/>
        <xsl:variable name="everydayForecast" select="//*[local-name()='forecast'][position() != 1]"/>
        <div class="today-temperature">
            <div class="today-temperature-number">
                <span id="today-temperature-number">
                    <xsl:if test="$firstForecast">
                        <xsl:value-of select="$firstForecast/*[local-name()='forecastTemp']"/>
                    </xsl:if>
                    <xsl:if test="not($firstForecast)">
                        &#35;
                    </xsl:if>
                </span>
                <span>
                    <i class="wi wi-celsius"></i>
                </span>
                <div id="today-temperature-day">
                    <xsl:if test="$firstForecast">
                        <xsl:value-of select="$firstForecast/*[local-name()='forecastDayOfWeek']"/> ngày
                        <xsl:value-of select="$firstForecast/*[local-name()='forecastDay']"/>
                    </xsl:if>
                    <xsl:if test="not($firstForecast)">
                        &#8212;
                    </xsl:if>
                </div>
            </div>
            <div class="today-temperature-icon">
                <div>
                    <i id="today-temperature-icon">
                        <xsl:attribute name="class">
                            <xsl:if test="$firstForecast">
                                <xsl:value-of select="$firstForecast/*[local-name()='forecastIcon']"/>
                            </xsl:if>
                            <xsl:if test="not($firstForecast)">
                                <xsl:text>wi wi-na</xsl:text>
                            </xsl:if>
                        </xsl:attribute>
                    </i>
                </div>
                <div>
                    <span id="today-temperature-wind">
                        <xsl:if test="$firstForecast">
                            <xsl:value-of select="$firstForecast/*[local-name()='forecastWind']"/>
                        </xsl:if>
                        <xsl:if test="not($firstForecast)">
                            <xsl:text>km/h</xsl:text>
                        </xsl:if>
                    </span>
                </div>
            </div>
        </div>
        <xsl:if test="$everydayForecast">
            <xsl:call-template name="recursiveForecast">
                <xsl:with-param name="counter" select="1"/>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="not($everydayForecast)">
            <xsl:call-template name="recursiveForecastNone">
                <xsl:with-param name="counter" select="1"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="recursiveForecast">
        <xsl:param name="counter"/>
        <xsl:if test="$counter!=7">
            <div class="temperature">
                <div class="temperature-day">
                    <span>
                        <xsl:value-of select="//*[local-name()='forecast'][$counter+1]/*[local-name()='forecastDayOfWeek']"/>
                    </span>
                </div>
                <div>
                    <i>
                        <xsl:attribute name="class">
                            <xsl:value-of select="//*[local-name()='forecast'][$counter+1]/*[local-name()='forecastIcon']"/>
                        </xsl:attribute>
                    </i>
                </div>
                <span>
                    <xsl:value-of select="//*[local-name()='forecast'][$counter+1]/*[local-name()='forecastTemp']"/>
                </span>
            </div>
            <xsl:call-template name="recursiveForecast">
                <xsl:with-param name="counter" select="$counter+1"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="recursiveForecastNone">
        <xsl:param name="counter"/>
        <xsl:if test="$counter!=7">
            <div class="temperature">
                <div class="temperature-day">
                    <span>
                        <xsl:text>&#8212;</xsl:text>
                    </span>
                </div>
                <div>
                    <i>
                        <xsl:attribute name="class">
                            <xsl:text>wi wi-na</xsl:text>
                        </xsl:attribute>
                    </i>
                </div>
                <span>
                    <xsl:text>&#35;&#176;</xsl:text>
                </span>
            </div>
            <xsl:call-template name="recursiveForecastNone">
                <xsl:with-param name="counter" select="$counter+1"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>


    <xsl:template match="/*[local-name()='stories']">
        <xsl:for-each select="./*[local-name()='news']">
            <div class="news-event-card">
                <xsl:attribute name="onclick">
                    <xsl:text>openUrlInNewTab('</xsl:text>
                    <xsl:value-of select="./*[local-name()='postOriginUrl']"/>
                    <xsl:text>');</xsl:text>
                </xsl:attribute>
                <div class="event-card">
                    <div class="event-card-image">
                        <xsl:attribute name="style">
                            <xsl:text>background-image: url('</xsl:text>
                            <xsl:value-of select="./*[local-name()='postImgUrl']"/>
                            <xsl:text>');</xsl:text>
                        </xsl:attribute>
                        <div class="event-card-date">
                            <span>
                                <xsl:value-of
                                        select="substring-after(translate(./*[local-name()='date'], '-', '/'), '/')"/>
                            </span>
                        </div>
                        <!--<div class="event-card-title">-->
                        <!--<xsl:value-of select="./*[local-name()='title']"/>-->
                        <!--</div>-->
                    </div>
                    <div class="event-card-description">
                        <span>
                            <xsl:value-of select="./*[local-name()='title']"/>
                        </span>
                    </div>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>