<!DOCTYPE html>
<%@page contentType="text/html; ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <c:set var="list" value="${requestScope.LIST}"/>
    <c:forEach var="forecast" items="${list}">
        ${forecast.forecastTemp}
    </c:forEach>
</body>
</html>