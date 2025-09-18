<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Welcome, ${not empty user.firstName ? user.firstName : user.username}!</h1>

<a href="banking?action=transfer">Make a Transfer</a> |
<a href="banking?action=createAccount">Open New Account</a> |
<a href="banking?action=profile">My Profile</a> |
<a href="banking?action=logout">Logout</a>

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
                <td><a href="banking?action=accountDetails&accountNumber=${acc.accountNumber}">View Transactions</a></td>
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
