<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Genre Form</title>
</head>
<body>
    <h1>Genre <c:if test="${genre == null}">New</c:if><c:if test="${genre != null}">Edit</c:if> Form</h1>
    <c:if test="${genre == null}">
    <form action="genre-save" method="post">
    </c:if>
    <c:if test="${genre != null}">
    <form action="genre-update" method="post">
    </c:if>
        <c:if test="${genre == null}">
            Name: <input type="text" name="name">
        </c:if>
        <c:if test="${genre != null}">
            Name: <input type="text" name="name" value="${genre.name}">
            <input name="id" value="${genre.id}" type="hidden">
        </c:if>

        <input type="submit" value="Submit">
    </form>
</body>
</html>
