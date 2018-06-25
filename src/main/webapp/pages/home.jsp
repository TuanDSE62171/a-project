<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="forecasts" value="${requestScope.forecasts}"/>


<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>WhereTo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" type="text/css" media="screen" href="css/index.css"/>
    <link href="https://unpkg.com/ionicons@4.1.2/dist/css/ionicons.min.css" rel="stylesheet"/>
    <script src="js/index.js"></script>
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
            <div class="news-event-filter">
                <div class="news-event-filter-item" onclick="setFilterItemActive(this, 0)">
                    TIN TỨC
                </div>
                <div class="news-event-filter-item" onclick="setFilterItemActive(this, 1)">
                    SỰ KIỆN
                </div>
                <div class="news-event-filter-item news-event-filter-item-active"
                     onclick="setFilterItemActive(this, 2)">
                    CẢ 2
                </div>
                <div class="news-event-filter-item" onclick="setFilterItemActive(this, 3)">
                    <i class="icon ion-md-close"></i>
                </div>
            </div>
        </div>
        <div class="news-event-grid">
            <div class="news-event-card">
                <div class="event-card">
                    <div class="event-card-image">
                        <div class="event-card-date">
                            <span>22/3</span>
                        </div>
                        <div class="event-card-title">Tiêu đề sự kiện</div>
                    </div>
                    <div class="event-card-description">
                                        <span>
                                            Đây là đoạn mô tả vô dụng. Mục đích của nó là làm cho đoạn văn trở nên dài
                                            hơn.
                                        </span>
                    </div>
                </div>
            </div>
            <div class="news-event-card">
                <div class="news-card">
                    <div class="news-card-header">
                        Tiêu đề tin tức, tiêu đề này phải thật dài để nhìn cho hợp lí
                    </div>
                    <div class="news-card-description">
                                        <span>
                                            Đoạn mô tả cho 1 tin tức nào đó, phải cố tỏ ra là tin tức này càng dài càng
                                            tốt.
                                        </span>
                    </div>
                    <div class="news-card-footer">
                        Tên trang đăng tin tức
                    </div>
                </div>
            </div>
            <div class="news-event-card">
                <div class="event-card">
                    <div class="event-card-image">
                        <div class="event-card-date">
                            <span>22/3</span>
                        </div>
                        <div class="event-card-title">Tiêu đề sự kiện</div>
                    </div>
                    <div class="event-card-description">
                                        <span>
                                            Đây là đoạn mô tả vô dụng. Mục đích của nó là làm cho đoạn văn trở nên dài
                                            hơn.
                                        </span>
                    </div>
                </div>
            </div>
            <div class="news-event-card">
                <div class="event-card">
                    <div class="event-card-image">
                        <div class="event-card-date">
                            <span>22/3</span>
                        </div>
                        <div class="event-card-title">Tiêu đề sự kiện</div>
                    </div>
                    <div class="event-card-description">
                                        <span>
                                            Đây là đoạn mô tả vô dụng. Mục đích của nó là làm cho đoạn văn trở nên dài
                                            hơn.
                                        </span>
                    </div>
                </div>
            </div>
            <div class="news-event-card">
                <div class="news-card">
                    <div class="news-card-header">
                        Tiêu đề tin tức, tiêu đề này phải thật dài để nhìn cho hợp lí
                    </div>
                    <div class="news-card-description">
                                        <span>
                                            Đoạn mô tả cho 1 tin tức nào đó, phải cố tỏ ra là tin tức này càng dài càng
                                            tốt.
                                        </span>
                    </div>
                    <div class="news-card-footer">
                        Tên trang đăng tin tức
                    </div>
                </div>
            </div>
            <div class="news-event-card">
                <div class="news-card">
                    <div class="news-card-header">
                        Tiêu đề tin tức, tiêu đề này phải thật dài để nhìn cho hợp lí
                    </div>
                    <div class="news-card-description">
                                        <span>
                                            Đoạn mô tả cho 1 tin tức nào đó, phải cố tỏ ra là tin tức này càng dài càng
                                            tốt.
                                        </span>
                    </div>
                    <div class="news-card-footer">
                        Tên trang đăng tin tức
                    </div>
                </div>
            </div>
            <div class="news-event-card">
                <div class="event-card">
                    <div class="event-card-image">
                        <div class="event-card-date">
                            <span>22/3</span>
                        </div>
                        <div class="event-card-title">Tiêu đề sự kiện</div>
                    </div>
                    <div class="event-card-description">
                                        <span>
                                            Đây là đoạn mô tả vô dụng. Mục đích của nó là làm cho đoạn văn trở nên dài
                                            hơn.
                                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="gui-layer" class="gui-layer">
        <input id="search" class="search" type="text" value="Bucharest, RO"/>
        <div id="current-status" class="current-status">
            <div class="current-status-1">
                <i class="icon ion-md-pin"></i>
            </div>
            <div class="current-status-2">
                FLORIDA, USA
            </div>
            <div class="current-status-3">
                20:15 pm
            </div>
        </div>
        <div id="main" class="main">
            <div class="main-nav">
                <div class="main-nav-item main-nav-item-active" onclick="setMainNavActive(this, 0)">
                    <div class="main-nav-item-body">
                        <i class="icon ion-md-sunny"></i>
                        Thời tiết
                    </div>
                    <div class="main-nav-item-color-footer"></div>
                </div>
                <div class="main-nav-item" onclick="setMainNavActive(this, 1)">
                    <div class="main-nav-item-body">
                        <i class="icon ion-md-paper-plane"></i>
                        Tin tức và Sự kiện
                    </div>
                    <div class="main-nav-item-color-footer"></div>
                </div>
                <div class="main-nav-item" onclick="setMainNavActive(this, 2)">
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
                <div class="main-info-gui-layer">
                    <div class="temperature-tab">
                        <c:if test="${not empty forecasts}">
                            <c:import charEncoding="UTF-8" var="xslt" url="../xsl/home.xsl"/>
                            <x:transform doc="${forecasts}" xslt="${xslt}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>