<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product Form</title>
    <style>
        body { font-family: sans-serif; }
        .form-container { width: 50%; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }
        .form-container h2 { text-align: center; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input { width: 100%; padding: 8px; box-sizing: border-box; }
        .form-group button { padding: 10px 15px; background-color: #4CAF50; color: white; border: none; cursor: pointer; border-radius: 5px; }
    </style>
</head>
<body>
<div class="form-container">
    <c:if test="${product != null}">
    <form action="update" method="post">
        <h2>Edit Product</h2>
        </c:if>
        <c:if test="${product == null}">
        <form action="insert" method="post">
            <h2>Add New Product</h2>
            </c:if>

            <c:if test="${product != null}">
                <input type="hidden" name="id" value="<c:out value='${product.id}' />" />
            </c:if>

            <div class="form-group">
                <label for="name">Product Name:</label>
                <input type="text" id="name" name="name" value="<c:out value='${product.name}' />" required>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="text" id="price" name="price" value="<c:out value='${product.price}' />" required pattern="[0-9]+(\.[0-9]{1,2})?" title="Enter a valid price like 123.45">
            </div>

            <div class="form-group">
                <button type="submit">Save</button>
            </div>
        </form>
</div>
</body>
</html>
