<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<%@ taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://bootstrap-3.ru/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://bootstrap-3.ru/examples/signin/signin.css" rel="stylesheet">

    <title>Sign in</title>
  </head>
  <body>

<div class="container">
    <form action="/j_spring_security_check" method="post" class="form-signin">
      <c:if test= "${not empty param.reg}">Successful registration</c:if>
      <h2 class="form-signin-heading">Please sign in</h2>
      <input type="text" name="j_username" class="form-control" placeholder="login" required autofocus />
      <input type="password" name="j_password" class="form-control" placeholder="password" required />
      <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign in" />
      <a href="/registration">Registration</a>
    </form>
</div>

  <security:authorize access="hasRole('admin')" var= "isUSer"/>
  <c:if test="${isUSer}">Вы вошли</c:if>

  <div align="center">
    <c:if test= "${not empty param.error}">Invalid login or password!</c:if>
  </div>
  </body>
</html>
