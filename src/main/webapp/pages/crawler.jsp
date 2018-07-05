<!DOCTYPE html>
<%@page contentType="text/html; ISO-8859-1" pageEncoding="UTF-8" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Manage Crawler</title>
    <link rel="stylesheet" type="text/css" media="screen" href="css/crawler.css"/>
    <link href="https://unpkg.com/ionicons@4.2.0/dist/css/ionicons.min.css" rel="stylesheet"/>
    <script src="js/crawler.js"></script>
    <script src="js/utility.js"></script>
</head>

<body>
<div class="wrapper">
    <div class="bug-logo">
        <i class="icon ion-md-bug"></i>
    </div>
    <div class="unlock-code">
        <span>MÃ MỞ KHÓA</span>
        <input type="text"/>
        <button>Mở khóa</button>
    </div>
    <div class="crawler-all">
        <span>Chạy hết</span>
        <div onclick="operateCrawlers(false)" class="action-all play">
            <i class="icon ion-md-play"></i>
        </div>
        <div onclick="operateCrawlers(true)" class="action-all pause">
            <i class="icon ion-md-pause"></i>
        </div>
        <span>Dừng hết</span>
    </div>
    <div class="crawler-list">
        <div id="capital" class="crawler">
            <div class="crawler-header">
                <div class="crawler-icon">
                    <i class="icon ion-md-bug"></i>
                </div>
                <div class="crawler-name">
                    <span>BỘ CÀO THỦ ĐÔ</span>
                </div>
                <div class="crawler-target">
                    <a href="http://www.sport-histoire.fr/en/Geography/ISO_codes_countries.php">
                        http://www.sport-histoire.fr
                    </a>
                </div>
                <div class="crawler-progress">
                    <div class="progress-bar">
                        <div class="progress-bar-status"></div>
                    </div>
                    <span class="progress-bar-percent">0%</span>
                </div>
                <div class="crawler-actions">
                    <div onclick="operateCrawler('capital', false)" class="action">
                        <i class="icon ion-md-play play"></i>
                    </div>
                    <div onclick="operateCrawler('capital', true)" class="action">
                        <i class="icon ion-md-pause stop"></i>
                    </div>
                </div>
            </div>
        </div>
        <div id="forecast" class="crawler">
            <div class="crawler-header">
                <div class="crawler-icon">
                    <i class="icon ion-md-bug"></i>
                </div>
                <div class="crawler-name">
                    <span>BỘ CÀO THỜI TIẾT</span>
                </div>
                <div class="crawler-target">
                    <a href="https://www.timeanddate.com/weather">
                        https://www.timeanddate.com
                    </a>
                </div>
                <div class="crawler-progress">
                    <div class="progress-bar">
                        <div class="progress-bar-status"></div>
                    </div>
                    <span class="progress-bar-percent">0%</span>
                </div>
                <div class="crawler-actions">
                    <div onclick="operateCrawler('forecast', false)" class="action">
                        <i class="icon ion-md-play play"></i>
                    </div>
                    <div onclick="operateCrawler('forecast', true)" class="action">
                        <i class="icon ion-md-pause stop"></i>
                    </div>
                </div>
            </div>
        </div>
        <div id="news" class="crawler">
            <div class="crawler-header">
                <div class="crawler-icon">
                    <i class="icon ion-md-bug"></i>
                </div>
                <div class="crawler-name">
                    <span>BỘ CÀO TIN TỨC</span>
                </div>
                <div class="crawler-target">
                    <a href="https://tintuc.vn/tin-quoc-te">
                        https://tintuc.vn
                    </a>
                </div>
                <div class="crawler-progress">
                    <div class="progress-bar">
                        <div class="progress-bar-status"></div>
                    </div>
                    <span class="progress-bar-percent">0%</span>
                </div>
                <div class="crawler-actions">
                    <div onclick="operateCrawler('news', false)" class="action">
                        <i class="icon ion-md-play play"></i>
                    </div>
                    <div onclick="operateCrawler('news', true)" class="action">
                        <i class="icon ion-md-pause stop"></i>
                    </div>
                </div>
            </div>
        </div>
        <div id="image" class="crawler">
            <div class="crawler-header">
                <div class="crawler-icon">
                    <i class="icon ion-md-bug"></i>
                </div>
                <div class="crawler-name">
                    <span>BỘ CÀO HÌNH ẢNH</span>
                </div>
                <div class="crawler-target">
                    <a href="https://unsplash.com/search/">
                        https://unsplash.com
                    </a>
                </div>
                <div class="crawler-progress">
                    <div class="progress-bar">
                        <div class="progress-bar-status"></div>
                    </div>
                    <span class="progress-bar-percent">0%</span>
                </div>
                <div class="crawler-actions">
                    <div onclick="operateCrawler('image', false)" class="action">
                        <i class="icon ion-md-play play"></i>
                    </div>
                    <div onclick="operateCrawler('image', true)" class="action">
                        <i class="icon ion-md-pause stop"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>