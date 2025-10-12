<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Product List</title>
</head>
<body>
<h1>Product List</h1>
<a href="mini-shop?action=product-create">Create New</a>
<c:if test="${not empty products}">
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Image URL</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.description}</td>
                <td>${product.imageUrl}</td>
                <td><a href="mini-shop?action=product-edit&id=${product.id}">Edit</a>
                    |
                    <form action="mini-shop" method="post">
                        <input type="hidden" name="action" value="product-delete">
                        <input type="hidden" name="id" value="${product.id}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty products}">
    <p>No products found.</p>
</c:if>
</body>
</html>