function makeXMLRequest(url, method, callbackFunction, headers, stringForPostRequest) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 401)) {
            if (callbackFunction) {
                // if (isXMLResponse) {
                //     callbackFunction(xhr.responseXML);
                // } else {
                //     callbackFunction(xhr.responseText);
                // }
                callbackFunction(xhr);
            }
        }
    };

    xhr.open(method, url, true);
    if (headers) {
        for (var i = 0; i < headers.length; i++) {
            xhr.setRequestHeader(headers[i].key, headers[i].value);
        }
    }
    if (method == 'POST') {
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    }
    xhr.send(stringForPostRequest);
}