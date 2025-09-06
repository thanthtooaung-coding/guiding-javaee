<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Welcome, ${user.username}!</h1>
<h2>Account Details</h2>
<p>Account Number: ${account.accountNumber}</p>
<p>Current Balance: <span style="font-weight: bold; color: green;">$${account.balance}</span></p>

<a href="banking?action=transfer">Make a Transfer</a> | <a href="banking?action=logout">Logout</a>

<h2>Recent Transactions</h2>
<c:if test="${not empty transactions}">
    <table border="1">
        <thead>
        <tr>
            <th>Type</th>
            <th>Account</th>
            <th>Amount</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tx" items="${transactions}">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${tx.sourceAccount.id == account.id}">
                            Sent
                        </c:when>
                        <c:otherwise>
                            Received
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:if test="${tx.sourceAccount.id == account.id}">
                        To: ${tx.targetAccount.accountNumber}
                    </c:if>
                    <c:if test="${tx.sourceAccount.id != account.id}">
                        From: ${tx.sourceAccount.accountNumber}
                    </c:if>
                </td>
                <td>$${tx.amount}</td>
                <td>${tx.transactionDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty transactions}">
    <p>No transactions found.</p>
</c:if>

</body>
</html>
