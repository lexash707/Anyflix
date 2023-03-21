<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    <h2 class="heading-secondary">Welcome ${korisnik.username}</h2>
</header>


<sec:authorize access="!isAuthenticated()">
    <a href="/login" class="link-boi"><button class="btn">Log In</button></a>
</sec:authorize>

<div class="grid-container">

    <div class="big-flex-boi">
        <sec:authorize access="hasRole('admin')">
            <div class="king-flexa-pezanta">
                <a href="/novaSerija" class="link-boi"><button class="btn">Dodaj seriju</button> </a>
                <a href="/zanr/izvestajZanr" target="_blank"><button class="btn">Izvestaj Zanrova</button></a>
                <a href="/izvestajSerija"><button class="btn">Izvestaj Serija</button> </a>
            </div>
        </sec:authorize>

        <sec:authorize access="isAuthenticated()">
            <div class="king-flexa-pezanta">
                <form action="/korisnici/logout" method="post">
                    <button type="submit" class="btn link-boi">Log Out</button>
                </form>
                <a href="/omiljene"><button class="btn">Omiljene serije</button></a>
            </div>
        </sec:authorize>
    </div>

    <form action="/" method="get" class="search-boi search-form-boi">
        <select name="zanrovi" class="select-boi">
            <option value="">Svi Zanrovi</option>--%>
                    <c:forEach items="${zanrovi}" var="z">
                        <option value="${z.idZanr}">${z.naziv}</option>
                    </c:forEach>

        </select>
        <input
                type="text"
                name="pretraga"
                placeholder="Unesi naziv serije"
                class="input-boi"
        />
        <button type="submit" name="trazi" class="btn">Pretraga</button>
    </form>

    <c:forEach items="${serije}" var="s">
        <div class="flex-container">
            <a class="link-boi" href="serije/details/${s.idSerije}">${s.getNaziv()}</a>
            <img src="/img/${s.getSlika()}" class="catalog-img"/>
        </div>
    </c:forEach>
</div>