var HEADER_TEXT_XML = { key: "Accept", value: "text/plain, application/json" };
var intervals = [
    {
        name: 'capital',
        stopped: true,
        progress: 0,
        id: null
    },
    {
        name: 'news',
        stopped: true,
        progress: 0,
        id: null
    },
    {
        name: 'image',
        stopped: true,
        progress: 0,
        id: null
    },
    {
        name: 'forecast',
        stopped: true,
        progress: 0,
        id: null
    }
];

window.onload = function () {
    render();
}

function setNotification(msg, valid) {
    var notification = document.getElementById("notification");
    notification.innerHTML = msg;
    notification.style.color = valid ? '#4cb74c' : '#b74c4c';
}

function getToken() {
    var token = document.getElementById("token").value;
    return token.trim();
}

function operateCrawlers(stop) {
    for (var i = 0; i < intervals.length; i++) {
        var interval = intervals[i];
        stopCrawler(interval, stop); // stop: false/true
    }
}

function operateCrawler(name, stop) {
    for (var i = 0; i < intervals.length; i++) {
        if (intervals[i].name.toLowerCase() == name.toLowerCase()) {
            stopCrawler(intervals[i], stop);
        }
    }
}

function handleProgressResponse(crawlerName, response) {
    var crawler = document.getElementById(crawlerName);
    var progress = crawler.getElementsByClassName("progress-bar-status")[0];
    var percent = crawler.getElementsByClassName("progress-bar-percent")[0];

    progress.style.gridColumn = '1 / span ' + parseInt(response);
    percent.innerHTML = parseInt(response) + '%';

    for (var i = 0; i < intervals.length; i++) {
        if (intervals[i].name == crawlerName) {
            intervals[i].progress = parseInt(response);
            if (parseInt(response) == 100) {
                intervals[i].stopped = true;
                clearInterval(intervals[i].id);
            }
        }
    }
    render();
}

function stopCrawler(crawler, stop) {
    if (isCrawlerStopped(crawler.name) === stop) {
        return;
    }
    if (!stop) {
        makeXMLRequest("http://localhost:8080/crawler/" + crawler.name + "/resume", "POST", function (responseResume) {
            if (responseResume.status != 401) {
                setNotification("Mã hợp lệ", true);
                crawler.id = setInterval(function () {
                    makeXMLRequest("http://localhost:8080/crawler/progress/" + crawler.name, "POST", function (responseProgress) {
                        if (responseProgress.status != 401) {
                            handleProgressResponse(crawler.name, responseProgress.responseText);
                            setNotification("Mã hợp lệ", true);
                        } else {
                            // invalid token
                            setNotification("Mã mở khóa không hợp lệ", false);
                        }
                    }, [HEADER_TEXT_XML], "token=" + getToken());
                }, 1000);
                crawler.stopped = false;
            } else {
                // invalid token
                setNotification("Mã mở khóa không hợp lệ", false);
            }
        }, [HEADER_TEXT_XML], "token=" + getToken());
    } else {
        crawler.stopped = true;
        makeXMLRequest("http://localhost:8080/crawler/" + crawler.name + "/pause", "POST", function (responsePause) {
            if (responsePause.status != 401) {
                setNotification("Mã hợp lệ", true);
                clearInterval(crawler.id);
                render();
            } else {
                // invalid token
                setNotification("Mã mở khóa không hợp lệ", false);
            }
        }, [HEADER_TEXT_XML], "token=" + getToken());
    }
}

function isCrawlerStopped(name) {
    for (var i = 0; i < intervals.length; i++) {
        if (intervals[i].name.toLowerCase() === name.toLowerCase()) {
            return intervals[i].stopped;
        }
    }
    return null;
}

function isAllCrawlersStopped() {
    var stoppedAll = true;
    for (var i = 0; i < intervals.length; i++) {
        if (!isCrawlerStopped(intervals[i].name)) {
            stoppedAll = false;
            break;
        }
    }
    return stoppedAll;
}

function isAllCrawlersFinished() {
    var stoppedAll = isAllCrawlersStopped();
    var totalPercent = (intervals.length * 100);
    for (var i = 0; i < intervals.length; i++) {
        totalPercent -= intervals[i].progress;
    }

    return (stoppedAll && !totalPercent);
}

function render() {
    var actionAll = document.getElementsByClassName("action-all");
    var stoppedAll = isAllCrawlersStopped();
    var finishedAll = isAllCrawlersFinished();
    var token = document.getElementById("token");

    // reset style
    for (var i = 0; i < actionAll.length; i++) {
        actionAll[i].firstElementChild.style.opacity = '1';
        actionAll[i].onclick = null;
    }
    // adjust
    if (!finishedAll) {
        actionAll[0].firstElementChild.className = 'icon ion-md-play play';
    }

    if (stoppedAll && stoppedAll != null) {
        token.readOnly = false;
        actionAll[1].firstElementChild.style.opacity = '0.1';
        if (finishedAll) {
            actionAll[0].firstElementChild.className = 'icon ion-md-refresh restart';
        }
        actionAll[0].onclick = (function () {
            return function () {
                operateCrawlers(false);
            };
        })();
    } else if (!stoppedAll) {
        token.readOnly = true;
        actionAll[0].firstElementChild.style.opacity = '0.1';
        actionAll[1].onclick = (function () {
            return function () {
                operateCrawlers(true);
            };
        })();
    }

    var crawlers = document.getElementsByClassName("crawler");
    for (var i = 0; i < crawlers.length; i++) {
        var actions = crawlers[i].getElementsByClassName("action");
        var percent = parseInt(crawlers[i].getElementsByClassName("progress-bar-percent")[0].innerText.replace("%", ""));
        var progressStatus = crawlers[i].getElementsByClassName("progress-bar-status")[0];
        var stopped = isCrawlerStopped(crawlers[i].id);

        // reset style
        for (var j = 0; j < actions.length; j++) {
            actions[j].firstElementChild.style.opacity = '1';
            actions[j].onclick = null;
        }
        // adjust 
        if (percent != 100) {
            actions[0].firstElementChild.className = 'icon ion-md-play play';
        }

        if (stopped && stopped != null) {
            actions[1].firstElementChild.style.opacity = '0.1';
            actions[0].onclick = (function (index) {
                return function () {
                    operateCrawler(crawlers[index].id, false);
                };
            })(i);
            if (percent == 100) {
                progressStatus.style.backgroundColor = '#5e5e5e';
                actions[0].firstElementChild.className = 'icon ion-md-refresh restart';
            } else {
                progressStatus.style.backgroundColor = '#b74c4c';
            }
        } else if (!stopped) {
            actions[0].firstElementChild.style.opacity = '0.1';
            actions[1].onclick = (function (index) {
                return function () {
                    operateCrawler(crawlers[index].id, true);
                };
            })(i);
            progressStatus.style.backgroundColor = '#4cb74c';
        }
    }
}