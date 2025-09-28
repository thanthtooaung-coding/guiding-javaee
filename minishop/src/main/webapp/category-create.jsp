<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Category Form</title>
</head>
<body>
<h1>Create Category Form</h1>
<form action="mini-shop" method="post">
    <input type="hidden" name="action" value="category-create">
    Name: <input type="text" name="name"><br>
    <input type="submit" value="Create">
</form>
</body>
</html>
