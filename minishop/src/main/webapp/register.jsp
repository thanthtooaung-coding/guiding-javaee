<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Form</title>
</head>
<body>
<h1>Register Form</h1>
<form action="mini-shop" method="post">
    <input type="hidden" name="action" value="register">
    Username: <input type="text" name="username"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Register">
</form>
</body>
</html>
