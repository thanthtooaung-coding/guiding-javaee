<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student List</title>
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
<h2>Student List</h2>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Age</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var = "student" items="studentList">
            <tr>
                <td>
                    <c:out value="${student.name}" />
                </td>
                <td>
                    <c:out value="${student.age}" />
                </td>
            </tr>
        </c:forEach>

    </tbody>
</table>
</body>
</html>