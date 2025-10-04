<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Product Form</title>
</head>
<body>
<h1>Edit Product Form</h1>
<form action="mini-shop" method="post">
    <input type="hidden" name="action" value="product-edit">
    <input type="hidden" name="id" value="${product.id}">
    Name: <input type="text" name="name" value="${product.name}"><br>
    Price: <input type="text" name="price" value="${product.price}"><br>
    Description: <input type="text" name="description" value="${product.description}"><br>
    Image Url: <input type="text" name="imageUrl" value="${product.imageUrl}"><br>
    <input type="submit" value="Update">
</form>
</body>
</html>