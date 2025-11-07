<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h1>Welcome, ${user.username}</h1>

    <a href="banking?action=transfer">Make a Transfer</a> | <a href="banking?action=createAccount">Open New Account</a> | <a href="banking?action=logout">Logout</a>

    <c:if test="${not empty lastLoginTime}">
        <p>Your last login was on: ${lastLoginTime}</p>
    </c:if>

    <h1>Your Accounts</h1>
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
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <td>${account.accountName}</td>
                        <td>${account.accountType}</td>
                        <td>${account.accountNumber}</td>
                        <td>${account.balance}</td>
                        <td><a href="banking?action=accountDetails&accountNumber=${account.accountNumber}">View Transaction</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty accounts}">
        <p>You have no accounts.</p>
    </c:if>
</body>
</html>
