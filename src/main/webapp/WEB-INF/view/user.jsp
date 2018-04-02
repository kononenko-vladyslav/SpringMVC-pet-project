<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>User</title>
        <link href="http://bootstrap-3.ru/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="http://bootstrap-3.ru/examples/signin/signin.css" rel="stylesheet">
    </head>
    <body>

    <div align="center">
        <h1 class="form-signin-heading">Hello, ${name}!</h1>
        </br></br></br>
        Click <a href="/j_spring_security_logout">here</a> to logout
    </div>
    </body>
</html>
