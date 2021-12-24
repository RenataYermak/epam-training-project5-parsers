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
    <h1>&lt;PARSERs/&gt;<br></h1>
    <div class="center div-form">
        <form name="parserForm" method="post" action="parserController" enctype="multipart/form-data">
            <div>
                <label>
                    <select name="parserType">
                        <option value="DOM">DOM</option>
                        <option value="SAX">SAX</option>
                        <option value="StAX">StAX</option>
                    </select>
                </label>
                <div>
                    <input type="file" name="file">
                </div>
            </div>
            <button type="submit" name="command" value="parse">Parse</button>
        </form>
    </div>
</div>
</body>
</html>
