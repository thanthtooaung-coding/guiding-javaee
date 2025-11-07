<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Genre List</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            border: 1px solid #ccc;
            text-align: left;
            padding: 10px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h2>Genre List</h2>
<a href="genre-new">Add new genre</a>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty genreList}">
        <tr>
            <td colspan="3">No genre found</td>
    </c:if>
    <c:forEach var="genre" items="${genreList}">
        <tr>
            <td>
                <c:out value="${genre.id}" />
            </td>
            <td>
                <c:out value="${genre.name}" />
            </td>
            <td>
                <a href="genre-edit?id=${genre.id}">Edit</a>
                <a href="genre-delete?id=${genre.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>