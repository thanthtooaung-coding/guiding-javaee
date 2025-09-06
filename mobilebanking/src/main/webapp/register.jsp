<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Register</h1>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
<form action="banking" method="post">
    <input type="hidden" name="action" value="register">
    Username: <input type="text" name="username"><br/>
    Password: <input type="password" name="password"><br/>
    <input type="submit" value="Register">
</form>
</body>
</html>
