<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Registracija</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--&lt;%&ndash;<form action="/korisnici/dodajKorisnika" method="post">&ndash;%&gt;--%>
<%--&lt;%&ndash;    <div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <div>username: <input type="text" name="username"></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <div>password: <input type="text" name="password"></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <input type="hidden" name="id_tipa" value="2">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <button type="submit" name="dodaj">Dodaj korisnik</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;    </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>

<%--</body>--%>
<%--</html>--%>

<html>
<head>
    <title>Register</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="style.css" />
</head>
<body>
<header class="header">
    <h1 class="heading-primary">Anyflix</h1>
</header>
<div class="login-form-wrapper-boi">
    <form action="/korisnici/dodajKorisnika" method="post" class="login-form-boi">
        <h4 class="label-boi">Registruj se</h4>
        <div><div class="label-boi">Username:</div> <input type="text" name="username" class="input-boi"> </div>
        <div><div class="label-boi">Password:</div> <input type="password" name="password" class="input-boi"> </div>
        <input type="hidden" name="tipKorisnika" value="2">
        <button type="submit" name="login" class="btn">Register</button>
    </form>
    <a href="/login" class="link-boi"><button class="btn">Log in</button></a>
</div>
</body>
</html>