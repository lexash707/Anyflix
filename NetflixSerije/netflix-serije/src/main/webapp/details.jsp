<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Detalji</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/style.css" />
</head>
<header class="header">
    <h1 class="heading-primary">Anyflix</h1>
</header>
<body>
<a href="/"><button class="btn">Back</button></a>
<div class="details-flex-container">

    <div class="top-boi">
        <img src="/img/${s.slika}" class="details-image"/>
        <div class="big-boi">
            <div>
                <p>${s.sinopsis}</p>
            </div>
            <c:if test="${!empty korisnik}">
            <div>
                <form action="/serije/dodajOmiljenu" method="post">
                    <input type="hidden" value="${s.idSerije}" name="serija">
                    <input type="hidden" value="${korisnik.idKorisnika}" name="korisnik">
                    <button class="btn">Dodaj u omiljene</button>
                </form>
            </div>
            <div>
                <form action="/serije/oceniSeriju" method="post">
                    <input type="number" min="1" max="10" step="1" name="ocena" class="input-boi">
                    <input type="text" name="komentar" class="input-boi">
                    <input type="hidden" value="${s.idSerije}" name="serija" class="input-boi">
                    <input type="hidden" value="${korisnik.idKorisnika}" name="korisnik" class="input-boi">
                    <button type="submit" class="btn">Oceni</button>
                </form>
            </div>
            </c:if>
        </div>
    </div>
    <div class="bottom-boi">
        <c:forEach items="${ocene}" var="o">
            <div class="smol-boi">
                <div class="omega-boi">
                    <div>
                    ${o.getKorisnik().getUsername()}
                    </div>
                    <div>
                    Komentar: ${o.komentar}
                    </div>
                </div>
                <div>
                    Ocena: ${o.ocena}
                </div>
            </div>
        </c:forEach>
    </div>
</div>


</body>
</html>