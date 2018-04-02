<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="WEB-INF/custom.tld"%>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
</head>
<body>
    <div align="right"><h3>Admin ${name}</h3> (<a href="/j_spring_security_logout">Logout</a>)</div>
    <a href="/admin/add">add user</a>

    <t:tableTag users="${users}" cssClass="table table-bordered" />

</body>
</html>
