var backgrounds = [];
var currentPage;
var HEADER_ACCEPT = { key: "Accept", value: "application/xml" };

window.onload = function () {

    images = parse(images);
    getImages(images);

    currentCapital = parse(currentCapital);

    capitals = parse(localStorage.getItem("capitalList"));

    currentPage = 1;

};

window.onclick = function (event) {
    if (event.target.id == 'search' || event.target.id == 'auto-complete' || event.target.id == 'search-result' || event.target.id == 'did-you-mean') {
        toggleResult(this.document.getElementById("search"), true);
    } else {
        toggleResult(null, false);
    }
};

function setMainNavActive(element, index, boolAnimation) {
    var random = Math.round(Math.random() * (backgrounds.length - 1));
    var items = document.getElementsByClassName("main-nav-item");
    for (var i = 0; i < items.length; i++) {
        items.item(i).classList.remove("main-nav-item-active");
    }
    element.classList.add("main-nav-item-active");

    if (boolAnimation) {
        setBackground(backgrounds[random]);
        triggerBackgroundAnimation();
    }

    var tabs = document.getElementById("main-info-gui-layer").children;

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].style.display = 'none';
    }

    switch (index) {
        case 0:
            enterForecastTab();
            break;
        case 1:
            enterNewsTab();
            break;
        case 2:
            enterGalleryTab();
            break;
    }
}

function setBackground(backgroundImg) {
    var background = document.getElementById("background");
    var backgroundProperties = {
        background: 'url(' + backgroundImg + ') no-repeat center',
        backgroundSize: 'cover'
    };
    var mainInfoOverlay = document.getElementById("main-info-overlay");
    var mainInfoOverlayProperties = {
        background: 'url("' + backgroundImg + '") no-repeat center',
        backgroundSize: 'cover',
        backgroundAttachment: 'fixed'
    };
    setProperty(background, backgroundProperties);
    setProperty(mainInfoOverlay, mainInfoOverlayProperties);
}

function enterForecastTab() {
    var tempTab = document.getElementById("temperature-tab");
    tempTab.style.display = 'grid';
}

function enterGalleryTab() {
    var galleryTab = document.getElementById("gallery-tab");
    var parentGuiLayer = document.getElementById("main-info-gui-layer");
    var galleryItems = document.getElementsByClassName("gallery-item");
    var parentWidth = getComputedStyle(parentGuiLayer).width.replace("px", "");
    var parentHeight = getComputedStyle(parentGuiLayer).height.replace("px", "");

    for (var i = 0; i < galleryItems.length; i++) {
        var item = galleryItems[i];
        item.style.transformOrigin = 'right top';
        item.style.transform = 'rotate(90deg) translateX(' + parseFloat(parentHeight) + 'px)';
    }
    galleryTab.style.height = parseFloat(parentWidth) + "px"; // rotate 90deg
    galleryTab.style.width = (parseFloat(parentHeight) + 19) + "px"; // rotate 90deg + hide scroller bar width
    galleryTab.style.transform = 'rotate(-90deg) translateY(-' + (parseFloat(parentHeight) + 19) + 'px)';
    galleryTab.style.gridAutoRows = parseFloat(parentHeight) + "px";
    galleryTab.style.display = 'grid';

}

function enterNewsTab() {
    var background = document.getElementById("background");
    var backgroundOverlay = document.getElementById("background-overlay");
    setTimeout(function () {
        triggerFade("Out", document.getElementById("gui-layer"), '0.5s', 'forwards');
        setProperty(background, { filter: "blur(10px)" });
        setProperty(backgroundOverlay, { opacity: 0.6 });
        setTimeout(function () {
            showNewsTab();
        }, 100);
    }, 250);
}

function exitNewsTab() {
    var background = document.getElementById("background");
    var backgroundOverlay = document.getElementById("background-overlay");
    var newsTab = document.getElementById("news-tab");
    triggerFade("Out", newsTab, '0.5s', 'forwards');
    setTimeout(function () {
        triggerFade("In", document.getElementById("gui-layer"), '0.5s', 'forwards');
        setProperty(background, { filter: "none" });
        setProperty(backgroundOverlay, { opacity: 0.5 });
        setProperty(newsTab, { display: "none" });
        setMainNavActive(document.getElementById("tab-item-1"), 0);
    }, 500);
}

function triggerBackgroundAnimation() {
    var background = document.getElementById("background");
    var mainInfoOverlay = document.getElementById("main-info-overlay");
    background.classList.remove("opacityAnimation");
    mainInfoOverlay.classList.remove("opacityAnimation");
    setTimeout(function () {
        background.classList.add("opacityAnimation");
        mainInfoOverlay.classList.add("opacityAnimation");
    }, 20)
}

function triggerFade(direction, element, duration, fillMode, timingFunction) {
    timingFunction = timingFunction || 'cubic-bezier(0,.33,0,1.01);';
    element.style.animationName = 'fade' + direction;
    element.style.animationDuration = duration;
    element.style.animationFillMode = fillMode;
    element.style.animationTimingFunction = timingFunction;
}

function setProperty(element, properties) {
    for (var prop in properties) {
        element.style[prop] = properties[prop];
    }
}

function showNewsTab() {
    var newsTab = document.getElementById("news-tab");
    triggerFade("In", newsTab, '0.5s', 'forwards');
    newsTab.style.display = "grid";
    newsTab.style.zIndex = 3;
}

function getImages(parsedXML) {
    var urls = parsedXML.getElementsByTagName("url");
    var prototype = document.getElementsByClassName("gallery-item")[0];
    var galleryTab = document.getElementById("gallery-tab");
    backgrounds = [];

    if(urls.length !== 0){
        while (galleryTab.firstElementChild) {
            galleryTab.removeChild(galleryTab.firstElementChild);
        }
    }
    

    for (var i = 0; i < urls.length; i++) {
        var url = urls[i].childNodes[0].textContent;
        var galleryItem = prototype.cloneNode(true);
        galleryItem.firstElementChild.style.backgroundImage = "url('" + url + "')";
        galleryTab.appendChild(galleryItem);
        backgrounds.push(url);
    }


    setMainNavActive(this.document.getElementById("tab-item-1"), 0, true);
}

function getNews(parsedXML) {
    // new contents
    var newsContents = parsedXML.getElementsByTagName("news");

    //card divs
    var cards = document.getElementsByClassName("news-event-card");

    //mapping content from old to new
    for (var i = 0; i < newsContents.length; i++) {
        var card = cards[i];
        var news = newsContents[i];
        var postOriginUrl = news.getElementsByTagName("postOriginUrl")[0].textContent;
        var postImageUrl = news.getElementsByTagName("postImgUrl")[0].textContent;
        var date = news.getElementsByTagName("date")[0].textContent;
        date = date.substring(6).replace("-", "/");
        var title = news.getElementsByTagName("title")[0].textContent;
        card.setAttribute("onclick", "openNewsPostInOtherBrowserTab('" + postOriginUrl + "');");
        card.getElementsByClassName("event-card-image")[0].setAttribute("style", "background-image: url('" + postImageUrl + "');");
        card.getElementsByClassName("event-card-date")[0].firstElementChild.innerHTML = date;
        card.getElementsByClassName("event-card-description")[0].firstElementChild.innerHTML = title;
    }
}

function getForecasts(parsedXML) {
    //new content from server
    var forecasts = parsedXML.getElementsByTagName("forecast");
    var todayForecast = forecasts[0];

    // get today div
    var todayForecastTemp = document.getElementById("today-temperature-number");
    var todayForecastDay = document.getElementById("today-temperature-day");
    var todayForecastIcon = document.getElementById("today-temperature-icon");
    var todayForecastWind = document.getElementById("today-temperature-wind");

    // get everyday divs
    var everydayForecasts = document.getElementsByClassName("temperature");

    if (forecasts.length > 0) {
        //for today forecast
        todayForecastTemp.innerHTML =
            todayForecast.getElementsByTagName("forecastTemp")[0].textContent;

        todayForecastDay.innerHTML =
            todayForecast.getElementsByTagName("forecastDayOfWeek")[0].textContent + ' ngày ' +
            todayForecast.getElementsByTagName("forecastDay")[0].textContent;

        todayForecastIcon.setAttribute("class",
            todayForecast.getElementsByTagName("forecastIcon")[0].textContent);

        todayForecastWind.innerHTML = todayForecast.getElementsByTagName("forecastWind")[0].textContent;
        //end today

        // for other days, i = 1 because of above setting for the first
        for (var i = 1; i < forecasts.length; i++) {
            var content = forecasts[i];
            var forecast = everydayForecasts[i - 1];

            var dayOfWeek = forecast.children[0].firstElementChild;
            var icon = forecast.children[1].firstElementChild;
            var temp = forecast.children[2];

            dayOfWeek.innerHTML = content.getElementsByTagName("forecastDayOfWeek")[0].textContent;
            icon.setAttribute("class", content.getElementsByTagName("forecastIcon")[0].textContent);
            temp.innerHTML = content.getElementsByTagName("forecastTemp")[0].textContent + '&#176;';

        }
    } else {
        todayForecastTemp.innerHTML = '&#35;';

        todayForecastDay.innerHTML = '&#8212;';

        todayForecastIcon.setAttribute("class", "wi wi-na");

        todayForecastWind.innerHTML = 'km/h';

        for (var i = 0; i < everydayForecasts.length; i++) {
            var forecast = everydayForecasts[i];
            var dayOfWeek = forecast.children[0].firstElementChild;
            var icon = forecast.children[1].firstElementChild;
            var temp = forecast.children[2];

            dayOfWeek.innerHTML = '&#8212;';
            icon.setAttribute("class", "wi wi-na");
            temp.innerHTML = '&#35;' + '&#176;';
        }
    }
}

function parse(stringXML) {
    var parser = new DOMParser();
    return parser.parseFromString(stringXML, "application/xml");
}

function toggleResult(input, bool) {
    if (bool) {
        document.getElementById("auto-complete").style.display = 'grid';
        searchCapital(input);
    } else {
        document.getElementById("auto-complete").style.display = 'none';
    }
}

function searchCapital(input) {
    var value = input.value.toLowerCase();
    while (value.match(/[,\s]/g)) {
        value = value.replace(/[,\s]/g, "");
    }
    var fuzzySearchFound = false;
    var result = [];
    if (capitals) {
        var capitalList = capitals.getElementsByTagName("capital");
        for (var i = 0; i < capitalList.length; i++) {
            var capital = capitalList[i];
            var name = capital.getAttributeNode("name").textContent.toLowerCase();
            var countryName = capital.getAttributeNode("countryName").textContent.toLowerCase();
            var code = capital.getAttributeNode("iso2Code").textContent.toLowerCase();
            if (name.includes(value) || countryName.includes(value) || code.includes(value)) {
                result.push(capital);
            } else if (i == capitalList.length - 1 && result.length === 0) {
                // re-search to find near-correct record
                for (var i = 0; i < capitalList.length; i++) {
                    capital = capitalList[i];
                    name = capital.getAttributeNode("name").textContent.toLowerCase();
                    countryName = capital.getAttributeNode("countryName").textContent.toLowerCase();
                    code = capital.getAttributeNode("iso2Code").textContent.toLowerCase();
                    var dist1 = (editDist(value, name) / name.length);
                    var dist2 = (editDist(value, countryName) / countryName.length);
                    var dist3 = (editDist(value, code) / code.length);
                    var minDist = min(dist1, dist2, dist3);
                    if (minDist <= 0.5) {
                        document.getElementById("did-you-mean").style.display = 'block';
                        fuzzySearchFound = true;
                        result.push(capital);
                    }
                }
            }
        }
        result = result.sort(function (capA, capB) {
            var strValA = capA.getAttributeNode("name").textContent + ", " +
                capA.getAttributeNode("countryName").textContent + ", " +
                capA.getAttributeNode("iso2Code").textContent;
            var strValB = capB.getAttributeNode("name").textContent + ", " +
                capB.getAttributeNode("countryName").textContent + ", " +
                capB.getAttributeNode("iso2Code").textContent;
            return strValA - strValB;
        });
    }
    displaySearchResult(result, fuzzySearchFound);
}

function displaySearchResult(result, fuzzySearchFound) {
    var searchResult = document.getElementById("search-result");
    while (searchResult.firstChild) {
        searchResult.removeChild(searchResult.firstChild);
    }
    for (var i = 0; i < result.length; i++) {
        var capital = result[i];
        var strVal = capital.getAttributeNode("name").textContent + ", " +
            capital.getAttributeNode("countryName").textContent + ", " +
            capital.getAttributeNode("iso2Code").textContent;
        var div = document.createElement("div");
        div.innerHTML = strVal;
        div.setAttribute("class", "search-result-item");
        div.setAttribute("onclick",
            "getInfoCapital('" + capital.getAttributeNode("iso2Code").textContent + "', '"
            + (capital.getAttributeNode("name").textContent + ", " + capital.getAttributeNode("iso2Code").textContent)
            + "')");
        document.getElementById("search-result").appendChild(div);
    }
    if (result.length == 0) {
        document.getElementById("auto-complete").style.display = 'none';
    } else if (result.length != 0 && fuzzySearchFound) {
        setFuzzyItems(true);
        document.getElementById("did-you-mean").style.display = 'block';
        document.getElementById("auto-complete").style.gridTemplateRows = '20% 10% 70%';
        document.getElementById("search-result").style.gridRow = '3 / span 1';
        document.getElementById("auto-complete").style.display = 'grid';
    } else {
        setFuzzyItems(false);
        document.getElementById("did-you-mean").style.display = 'none';
        document.getElementById("auto-complete").style.display = 'grid';
        document.getElementById("auto-complete").style.gridTemplateRows = '100%';
        document.getElementById("search-result").style.gridRow = '1 / span 1';
    }
}

function getInfoCapital(code, strVal) {
    makeXMLRequest("http://localhost:8080/" + code + "/images", "GET", getImages, [HEADER_ACCEPT], true);
    makeXMLRequest("http://localhost:8080/" + code + "/forecasts", "GET", getForecasts, [HEADER_ACCEPT], true);

    toggleResult(null, false);
    document.getElementById("search").value = strVal;

}

function openNewsPostInOtherBrowserTab(url) {
    var tab = window.open(url, "_blank");
    tab.focus();
}

function switchPage(page) {
    makeXMLRequest("http://localhost:8080/totalpages?page=1", "GET", function (response) {
        totalNewsPages = parseInt(response);
        document.getElementById("total-page").innerHTML = response;
        page = parseInt(page);
        var next = document.getElementById("news-next-page");
        var pre = document.getElementById("news-previous-page");
        var arrowLeft = pre.firstElementChild;
        var arrowRight = next.firstElementChild

        pre.style.visibility = "hidden";
        next.style.visibility = "hidden";

        currentPage += page;
        if (currentPage <= 1) {
            currentPage = 1;
            arrowLeft.removeAttribute("class");
            arrowRight.setAttribute("class", "icon ion-md-arrow-dropright-circle");
            next.style.visibility = "visible";
        } else if (currentPage >= totalNewsPages) {
            arrowRight.removeAttribute("class");
            arrowLeft.setAttribute("class", "icon ion-md-arrow-dropleft-circle");
            currentPage = totalNewsPages;
            pre.style.visibility = "visible";
        } else {
            arrowLeft.setAttribute("class", "icon ion-md-arrow-dropleft-circle");
            arrowRight.setAttribute("class", "icon ion-md-arrow-dropright-circle");
            next.style.visibility = "visible";
            pre.style.visibility = "visible";
        }

        document.getElementById("current-page").innerHTML = currentPage;

        makeXMLRequest("http://localhost:8080/news?page=" + currentPage, "GET", getNews, [HEADER_ACCEPT], true);
    }, null, false);

}

function min(a, b, c) {
    return Math.min(Math.min(a, b), c);
}

function editDist(s1, s2) {
    var n = s1.length;
    var m = s2.length;

    var char_i;
    var char_j;
    var cost;

    if (n == 0) return m;
    if (m == 0) return n;

    var dist = new Array(n + 1);

    for (var k = 0; k < dist.length; k++) {
        dist[k] = new Array(m + 1);
    }

    for (var i = 0; i <= n; i++) {
        dist[i][0] = i;
    }
    for (var j = 0; j <= m; j++) {
        dist[0][j] = j;
    }

    for (var i = 1; i <= n; i++) {
        char_i = s1.charAt(i - 1);
        for (var j = 1; j <= m; j++) {
            char_j = s2.charAt(j - 1);
            if (char_i == char_j) {
                cost = 0;
            } else {
                cost = 1;
            }
            dist[i][j] = min(
                dist[i - 1][j] + 1,
                dist[i][j - 1] + 1,
                dist[i - 1][j - 1] + cost
            );
        }
    }
    return dist[n][m];
}

function setFuzzyItems(bool) {
    var items = document.getElementById("search-result").children;
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        item.className = "search-result-item" + (bool ? " fuzzy" : "");
    }
}
