<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Dodaj seriju</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="style.css" />
</head>
<body>
<header class="header">
    <h1 class="heading-primary">Anyflix</h1>
</header>
<form action="/serije/dodajSeriju" method="post" enctype="multipart/form-data" class="login-form-boi">
    <div>
        <div><div class="label-boi">Naziv:</div> <input type="text" name="naziv" class="input-boi"></div>
        <div><div class="label-boi">Sinopsis: </div><input type="text" name="sinopsis" class="input-boi"></div>
        <div><div class="label-boi">Slika:</div><input type="file" name="image" accept="image/png, image/jpeg" class="input-boi"/></div>
        <div>
        <select multiple name="zanrovi" class="select-boi">
            <c:forEach items="${zanrovi}" var="z">
                <option value="${z.idZanr}">${z.naziv}</option>
            </c:forEach>
        </select>
        </div>
        <button type="submit" name="dodaj" class="btn">Dodaj seriju</button>
    </div>
        <c:if test="${!empty poruka}">
            <span style="color: #880707" class="error-boi">${poruka}</span>
        </c:if>
</form>

</body>
</html>