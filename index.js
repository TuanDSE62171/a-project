function setMainNavActive(element, index) {
    var items = document.getElementsByClassName("main-nav-item");
    for (i = 0; i < items.length; i++) {
        items.item(i).classList.remove("main-nav-item-active");
    }
    element.classList.add("main-nav-item-active");
    var background = document.getElementById("background");
    var backgroundProperties = {
        background: 'url("./asset/bucharest-wall-' + (index + 1) + '.jpg") no-repeat center',
        backgroundSize: 'cover'
    }
    var mainInfoOverlay = document.getElementById("main-info-overlay");
    var mainInfoOverlayProperties = {
        background: 'url("./asset/bucharest-wall-' + (index + 1) + '.jpg") no-repeat center',
        backgroundSize: 'cover',
        backgroundAttachment: 'fixed'
    }
    setProperty(background, backgroundProperties);
    setProperty(mainInfoOverlay, mainInfoOverlayProperties);
    triggerBackgroundAnimation();
    if (index == 1) {
        enterNewsTab();
    }
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
        setFilterItemActive(document.getElementsByClassName("news-event-filter-item")[2], 2);
    }, 500);
}

function setFilterItemActive(element, index) {
    if (index != 3) {
        var items = document.getElementsByClassName("news-event-filter-item");
        for (i = 0; i < items.length; i++) {
            items.item(i).classList.remove("news-event-filter-item-active");
        }
        element.classList.add("news-event-filter-item-active");
        triggerCardAnimation(index);
    } else {
        exitNewsTab();
    }
}

function triggerCardAnimation(index) {
    var cards = document.getElementsByClassName("news-event-card");
    for (i = 0; i < cards.length; i++) {
        var card = cards.item(i);
        setTimeout(function (cards, i, index) {
            var card = cards.item(i);
            card.style.animationDuration = '0.25s';
            card.style.animationFillMode = 'forwards';
            switch (cards.item(i).firstElementChild.className) {
                case 'news-card':
                    card.style.animationName = (index != 1 ? 'scaleUpFade' : 'scaleDownFade');
                    break;
                case 'event-card':
                    card.style.animationName = (index != 0 ? 'scaleUpFade' : 'scaleDownFade');
                    break;
                default:
                    break;
            }
        }, 20, cards, i, index);
    }
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