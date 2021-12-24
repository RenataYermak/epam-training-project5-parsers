<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <meta charset="utf-8">
    <title>Parsers</title>
</head>
<body>
<div>
    <h1>&lt;RESULTs/&gt;<br></h1>
    <jsp:useBean id="parserType" scope="request" type="java.lang.String"/>
    <h1>Parser: <span class="span">${parserType}</span><br></h1>
    <table class="center">
        <tr>
            <th>ID</th>
            <th>Year</th>
            <th>Country</th>
            <th>Author</th>
            <th>Valuable</th>
            <th>Theme</th>
            <th>Type</th>
        </tr>
        <jsp:useBean id="cards" scope="request" type="java.util.List"/>
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${card.id}</td>
                <td>${card.year}</td>
                <td>${card.country}</td>
                <td>${card.author}</td>
                <td>${card.valuable.name}</td>
                <td>${card.theme.name}</td>
                <td>${card.type.name}</td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
