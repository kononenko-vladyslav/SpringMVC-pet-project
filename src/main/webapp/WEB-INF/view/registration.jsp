<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <link href="http://bootstrap-3.ru/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://bootstrap-3.ru/examples/signin/signin.css" rel="stylesheet">
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

<h1>Registration</h1>
<c:if test="${action eq edit}">
    <c:set var="readonly" value="true" />
</c:if>
<div class="container">
    <form:form action="registration" commandName="user" method="post" class="form-signin">
        <form:input path="id" name="id" value="${user.id}" hidden="true" />
        <form:input path="role" name="role" value="2" hidden="true" />

        <table class="table table-border">
            <tr>
                <th>Login</th>
                <th><form:input path="login" />
                    <form:errors path="login" /></th>
            </tr>
            <tr>
                <th>Password</th>
                <th><form:input path="password" type="password" class="form-control" />
                    <form:errors path="password" /></th>
            </tr>
            <tr>
                <th>Password again</th>
                <th><form:input path="confirmPassword" class="form-control" type="password" name="passwordAgain" />
                    <form:errors path="confirmPassword" /></th>
            </tr>
            <tr>
                <th>Email</th>
                <th><form:input path="email" class="form-control" />
                    <form:errors path="email" /></th>
            </tr>
            <tr>
                <th>First name</th>
                <th><form:input path="firstname"  class="form-control"/>
                    <form:errors path="firstname" /></th>
            </tr>
            <tr>
                <th>Last name</th>
                <th><form:input path="lastname" class="form-control" />
                    <form:errors path="lastname" /></th>
            </tr>
            <tr>
                <th>Birthday</th>
                <th><form:input path="birthday" class="form-control"
                                placeholder="Day.Month.Year"/>
                    <form:errors path="birthday" /></th>
            </tr>

        </table>
        <div class="g-recaptcha" data-sitekey="6LeEiyYUAAAAALxBOURktlzXJJ7ZRmWl7n1SK4GY"></div>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Ok" >
    </form:form>
    <form action="/" class="form-signin">
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Cancel">
    </form>
</div>
<div align="center">
    <c:if test="${noUniqLogin}"><c:out value="User with login already exists" /></c:if><br>
    <c:if test="${noUniqEmail}"><c:out value="User with email already exists" /></c:if><br>
    <c:if test="${reCaptchaFail}"><c:out value="Check captcha!" /></c:if><br>
</div>
</body>
</html>
