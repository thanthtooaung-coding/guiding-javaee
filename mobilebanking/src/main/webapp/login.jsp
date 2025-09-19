<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
<form action="banking" method="post">
    <input type="hidden" name="action" value="login">
    Username: <input type="text" name="username"><br/>
    Password: <input type="password" name="password"><br/>
    <input type="checkbox" name="rememberMe" value="true"> Remember Me<br/><br/>
    <input type="submit" value="Login">
</form>
</body>
</html>
