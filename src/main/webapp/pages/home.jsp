<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="forecasts" value="${requestScope.forecasts}"/>
<c:set var="news" value="${requestScope.news}"/>

<c:import charEncoding="UTF-8" var="xslt" url="../xsl/home.xsl"/>

<x:parse doc="${requestScope.currentCapital}" var="currentCapital"/>
<x:set var="currentCapName" select="$currentCapital/*[local-name()='capitals']/*[local-name()='capital']"/>
<c:set var="totalNewsPages" value="${requestScope.totalNewsPages}"/>

<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Loca Port</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" type="text/css" media="screen" href="css/index.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="css/weather-icons.min.css"/>
    <link href="https://unpkg.com/ionicons@4.2.0/dist/css/ionicons.min.css" rel="stylesheet"/>
    <link rel="icon" type="image/png" sizes="16x16" href="asset/favicon-96x96.png"/>
    <script>
        var capitals = '${requestScope.capitals}';
        var images = '${requestScope.images}';
        var currentCapital = '${requestScope.currentCapital}';
        var totalNewsPages = parseInt(${totalNewsPages});
    </script>
    <script src="js/index.js"></script>
    <script src="js/utility.js"></script>
</head>

<body>
<div id="wrapper" class="wrapper">
    <div id="background" class="background opacityAnimation"></div>
    <div id="background-overlay" class="background-overlay"></div>
    <div id="news-tab" class="news-tab">
        <div class="news-event-header">
            <div class="news-event">
                TIN TỨC &amp; SỰ KIỆN
            </div>
            <div class="news-event-header-item" onclick="exitNewsTab()">
                <i class="icon ion-md-close"></i>
            </div>
        </div>
        <div id="news-event-grid" class="news-event-grid">
            <x:transform doc="${news}" xslt="${xslt}"/>
        </div>
        <div class="news-footer">
            <div id="news-previous-page">
                <i onclick="switchPage(-1)"></i>
            </div>
            <div id="current-page">1</div>
            <div id="slash">&#47;</div>
            <div id="total-page">${totalNewsPages}</div>
            <div  id="news-next-page">
                <i onclick="switchPage(1)" class="icon ion-md-arrow-dropright-circle"></i>
            </div>
        </div>
    </div>
    <div id="gui-layer" class="gui-layer">
        <input id="search" class="search" type="text"
               value="<x:out select="$currentCapName/@name"/>, <x:out select="$currentCapName/@iso2Code"/>"
               autocomplete="off"
               oninput="searchCapital(this)"
        />
        <div id="auto-complete" class="auto-complete">
            <div id="did-you-mean">Có phải bạn đang tìm:</div>
            <div id="search-result" class="search-result">
            </div>
        </div>
        <div id="main" class="main">
            <div class="main-nav">
                <div id="tab-item-1" class="main-nav-item main-nav-item-active" onclick="setMainNavActive(this, 0, true)">
                    <div class="main-nav-item-body">
                        <i class="icon ion-md-sunny"></i>
                        Thời tiết
                    </div>
                    <div class="main-nav-item-color-footer"></div>
                </div>
                <div id="tab-item-2" class="main-nav-item" onclick="setMainNavActive(this, 1, true)">
                    <div class="main-nav-item-body">
                        <i class="icon ion-md-paper-plane"></i>
                        Tin tức và Sự kiện
                    </div>
                    <div class="main-nav-item-color-footer"></div>
                </div>
                <div id="tab-item-3" class="main-nav-item" onclick="setMainNavActive(this, 2, true)">
                    <div class="main-nav-item-body">
                        <i class="icon ion-md-images"></i>
                        Bộ sưu tập
                    </div>
                    <div class="main-nav-item-color-footer"></div>
                </div>
                <div class="main-nav-overlay"></div>
            </div>
            <div class="main-info">
                <div id="main-info-overlay" class="main-info-overlay opacityAnimation"></div>
                <div id="main-info-gui-layer" class="main-info-gui-layer">
                    <div id="temperature-tab" class="temperature-tab">
                        <x:transform doc="${forecasts}" xslt="${xslt}"/>
                    </div>
                    <div id="gallery-tab" class="gallery-tab">
                        <div class="gallery-item">
                            <div class="image">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>