<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="style.css" />
    <title>ANYFLIX</title>

</head>
<body>
<header class="header">
    <h1 class="heading-primary">Anyflix</h1>
    <h2 class="heading-secondary">Omiljene serije korisnika ${korisnik.username}</h2>
</header>

<div class="grid-container">
    <c:forEach items="${omiljene}" var="s">
        <div class="flex-container">
            <a class="link-boi" href="serije/details/${s.idSerije}">${s.getNaziv()}</a>
            <img src="/img/${s.getSlika()}" class="catalog-img"/>
        </div>
    </c:forEach>
</div>