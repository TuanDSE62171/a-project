var HEADER = { key: "Accept", value: "text/plain" };
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

window.onload = function() {
    render();
}

function operateCrawlers(stop) {
    for (var i = 0; i < intervals.length; i++) {
        var interval = intervals[i];
        stopCrawler(interval, stop); // stop: false
    }
}

function operateCrawler(name, stop){
    for(var i = 0; i < intervals.length; i++){
        if(intervals[i].name.toLowerCase() == name.toLowerCase()){
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
    if(isCrawlerStopped(crawler.name)===stop){
        return;
    }
    if (!stop) {
        makeXMLRequest("http://localhost:8080/crawler/" + crawler.name + "/resume", "GET", function () {
            crawler.id = setInterval(function () {
                makeXMLRequest("http://localhost:8080/crawler/progress/" + crawler.name, "GET", function (response) {
                    handleProgressResponse(crawler.name, response);
                }, HEADER, null, null);
            }, 1000);
            crawler.stopped = false;
            render();
        }, HEADER, null, null);
    } else {
        crawler.stopped = true;
        makeXMLRequest("http://localhost:8080/crawler/"+crawler.name+"/pause", "GET", render, HEADER, null, null);
        clearInterval(crawler.id);
    }
}

function isCrawlerStopped(name){
    for(var i = 0; i < intervals.length; i++){
        if(intervals[i].name.toLowerCase() === name.toLowerCase()){
            return intervals[i].stopped;
        }
    }
    return null;
}

function render(){
    var actionAll = document.getElementsByClassName("action-all");
    var stoppedAll = false;
    for(var i = 0; i < intervals.length; i++){
        if(!isCrawlerStopped(intervals[i].name)){
            break;
        }
        stoppedAll = true;
    }
    // reset style
    for(var i = 0; i < actionAll.length; i++){
        actionAll[i].firstElementChild.style.opacity = '1';
    }
    // adjust
    if(stoppedAll && stoppedAll != null){
        actionAll[1].firstElementChild.style.opacity = '0.1';
    } else if(!stoppedAll) {
        actionAll[0].firstElementChild.style.opacity = '0.1';
    }

    var crawlers = document.getElementsByClassName("crawler");
    for(var i = 0; i < crawlers.length; i++){
        var actions = crawlers[i].getElementsByClassName("action");
        var stopped = isCrawlerStopped(crawlers[i].id);
        // reset style
        for(var j = 0; j < actions.length; j++){
            actions[j].firstElementChild.style.opacity = '1';
        }
        // adjust 
        if(stopped && stopped != null){
            actions[1].firstElementChild.style.opacity = '0.1';
        } else if(!stopped) {
            actions[0].firstElementChild.style.opacity = '0.1';
        }
    }
}