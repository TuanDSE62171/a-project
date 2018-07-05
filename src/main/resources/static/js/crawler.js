var HEADER_TEXT_XML = { key: "Accept", value: "text/plain, application/json" };
var intervals = [
    {
        name: 'capital',
        stopped: true,
        id: null
    },
    {
        name: 'news',
        stopped: true,
        id: null
    },
    {
        name: 'image',
        stopped: true,
        id: null
    },
    {
        name: 'forecast',
        stopped: true,
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
        stopCrawler(interval, stop); // stop: false
    }
}

function operateCrawler(name, stop) {
    for (var i = 0; i < intervals.length; i++) {
        if (intervals[i].name.toLowerCase() == name.toLowerCase()) {
            stopCrawler(intervals[i], stop);
        }
    }
}

function handleProgressResponse(crawler, response) {
    var capital = document.getElementById(crawler);
    var progress = capital.getElementsByClassName("progress-bar-status")[0];
    var percent = capital.getElementsByClassName("progress-bar-percent")[0];

    progress.style.gridColumn = '1 / span ' + parseInt(response);
    percent.innerHTML = parseInt(response) + '%';
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
                    }, [HEADER_TEXT_XML], "token="+getToken());
                }, 1000);
                crawler.stopped = false;
                render();
            } else {
                // invalid token
                setNotification("Mã mở khóa không hợp lệ", false);
            }
        }, [HEADER_TEXT_XML], "token="+getToken());
    } else {
        crawler.stopped = true;
        makeXMLRequest("http://localhost:8080/crawler/" + crawler.name + "/pause", "POST", function (responsePause) {
            if (responsePause.status != 401) {
                setNotification("Mã hợp lệ", true);
                render();
            } else {
                // invalid token
                setNotification("Mã mở khóa không hợp lệ", false);
            }
        }, [HEADER_TEXT_XML], "token="+getToken());
        clearInterval(crawler.id);
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

function render() {
    var actionAll = document.getElementsByClassName("action-all");
    var stoppedAll = false;
    for (var i = 0; i < intervals.length; i++) {
        if (!isCrawlerStopped(intervals[i].name)) {
            break;
        }
        stoppedAll = true;
    }
    // reset style
    for (var i = 0; i < actionAll.length; i++) {
        actionAll[i].firstElementChild.style.opacity = '1';
    }
    // adjust
    if (stoppedAll && stoppedAll != null) {
        actionAll[1].firstElementChild.style.opacity = '0.1';
    } else if (!stoppedAll) {
        actionAll[0].firstElementChild.style.opacity = '0.1';
    }

    var crawlers = document.getElementsByClassName("crawler");
    for (var i = 0; i < crawlers.length; i++) {
        var actions = crawlers[i].getElementsByClassName("action");
        var progressStatus = crawlers[i].getElementsByClassName("progress-bar-status")[0];
        var stopped = isCrawlerStopped(crawlers[i].id);
        // reset style
        for (var j = 0; j < actions.length; j++) {
            actions[j].firstElementChild.style.opacity = '1';
        }
        // adjust 
        if (stopped && stopped != null) {
            actions[1].firstElementChild.style.opacity = '0.1';
            progressStatus.style.backgroundColor = '#b74c4c';
        } else if (!stopped) {
            actions[0].firstElementChild.style.opacity = '0.1';
            progressStatus.style.backgroundColor = '#4cb74c';
        }
    }
}