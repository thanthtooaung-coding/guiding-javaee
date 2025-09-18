<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<h1>Your Profile</h1>
<c:if test="${not empty message}">
    <p style="color:green;">${message}</p>
</c:if>
<form action="banking" method="post">
    <input type="hidden" name="action" value="updateProfile">
    Username: <strong>${user.username}</strong><br/><br/>
    First Name: <input type="text" name="firstName" value="${user.firstName}"><br/><br/>
    Last Name: <input type="text" name="lastName" value="${user.lastName}"><br/><br/>
    Email: <input type="email" name="email" value="${user.email}"><br/><br/>
    <input type="submit" value="Update Profile">
</form>
<br/>
<a href="banking?action=dashboard">Back to Dashboard</a>
</body>
</html>
