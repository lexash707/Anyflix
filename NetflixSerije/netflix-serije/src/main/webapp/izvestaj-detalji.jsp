<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Izvestaj Serija</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="style.css" />
</head>
<body>

<header class="header">
    <h1 class="heading-primary">Anyflix</h1>
    <h2 class="heading-secondary">Generisanje izvestaja</h2>
</header>

<form action="/serije/izvestajSerija" method="post" class="login-form-boi">

    <select multiple name="selektovaneSerije" class="select-boi">
        <c:forEach items="${serije}" var="s">
            <option value="${s.idSerije}">${s.naziv}</option>
        </c:forEach>
    </select>
    <button class="btn" type="submit">Generiši izveštaj</button>

</form>
</body>
</html>