<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Product Form</title>
</head>
<body>
<h1>Create Product Form</h1>
<form action="mini-shop" method="post">
    <input type="hidden" name="action" value="product-create">
    Name: <input type="text" name="name"><br>
    Price: <input type="number" name="price"><br>
    Description: <input type="text" name="description"><br>
    Image URL: <input type="text" name="imageUrl"><br>
    <select name="categoryId">
        <c:forEach items="${categories}" var="category">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Create">
</form>
</body>
</html>
