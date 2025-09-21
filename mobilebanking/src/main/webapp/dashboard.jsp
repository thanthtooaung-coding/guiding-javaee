<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="${empty cookie.theme.value ? 'light' : cookie.theme.value}">
<h1>Welcome, ${not empty user.firstName ? user.firstName : user.username}!</h1>

<c:if test="${not empty lastLoginTime}">
    <p>Your last login was on: ${lastLoginTime}</p>
</c:if>

<form action="banking" method="post" style="display: inline;">
    <input type="hidden" name="action" value="changeTheme">
    <select name="theme" onchange="this.form.submit()">
        <option value="light" ${cookie.theme.value == 'light' ? 'selected' : ''}>Light Theme</option>
        <option value="dark" ${cookie.theme.value == 'dark' ? 'selected' : ''}>Dark Theme</option>
    </select>
</form>

<a class="${empty cookie.theme.value ? 'light' : cookie.theme.value}" href="banking?action=transfer">Make a Transfer</a> |
<a class="${empty cookie.theme.value ? 'light' : cookie.theme.value}" href="banking?action=createAccount">Open New Account</a> |
<a class="${empty cookie.theme.value ? 'light' : cookie.theme.value}" href="banking?action=profile">My Profile</a> |
<a class="${empty cookie.theme.value ? 'light' : cookie.theme.value}" href="banking?action=logout">Logout</a>

<h2>Your Accounts</h2>
<c:if test="${not empty accounts}">
    <table border="1">
        <thead>
        <tr>
            <th>Account Name</th>
            <th>Type</th>
            <th>Account Number</th>
            <th>Balance</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="acc" items="${accounts}">
            <tr>
                <td>${acc.accountName}</td>
                <td>${acc.accountType}</td>
                <td>${acc.accountNumber}</td>
                <td>$${acc.balance}</td>
                <td><a class="${empty cookie.theme.value ? 'light' : cookie.theme.value}" href="banking?action=accountDetails&accountNumber=${acc.accountNumber}">View Transactions</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty accounts}">
    <p>You have no accounts. <a href="banking?action=createAccount">Open one now!</a></p>
</c:if>

</body>
</html>
