<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Category Form</title>
</head>
<body>
<h1>Edit Category Form</h1>
<form action="mini-shop" method="post">
    <input type="hidden" name="action" value="category-edit">
    <input type="hidden" name="id" value="${category.id}">
    Name: <input type="text" name="name" value="${category.name}"><br>
    <input type="submit" value="Update">
</form>
</body>
</html>
