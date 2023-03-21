<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
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
    <form action="/korisnici/login" method="post" class="login-form-boi">
        <h4 class="label-boi">Please enter your username and password</h4>
        <div><div class="label-boi">Username:</div> <input type="text" name="username" class="input-boi"> </div>
        <div><div class="label-boi">Password:</div> <input type="password" name="password" class="input-boi"> </div>
        <button type="submit" name="login" class="btn">Log in</button>
    </form>
    <a href="/registracija" class="link-boi"><button class="btn">Registruj se</button></a>
    <c:if test="${fail}">
        <div>
            <span class="error-boi">Korisnicko ime ili lozinka nisu ispravni</span>
        </div>
        <div>
            <img src="/img/loginfail.gif"/>
        </div>
    </c:if>
</div>
</body>
</html>
