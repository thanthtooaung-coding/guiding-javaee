<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h1>Username: ${username}</h1>
    <h2>UserType: ${usertype}</h2>
    <c:if test="${usertype == 'Admin'}">
        <a href="mini-shop?action=category-list">Category List</a> |
    </c:if>
    <a href="mini-shop?action=product-list">Product List</a>
</body>
</html>
