<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account Details</title>
</head>
<body>
    <h1>Account Details: ${account.accountName} (${account.accountType})</h1>
    <p>Account Number: ${account.accountNumber}</p>
    <p>Current Balance: <span style="font-weight: bold; color: green;">${account.balance}</span></p>

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
            <c:forEach items="${transactions}" var="transaction">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${transaction.sourceAccount.id == account.id}">
                                <span style="color: red">Sent</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: green">Received</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${transaction.type}</td>
                    <td>
                        <c:if test="${transaction.sourceAccount.id == account.id}">
                            <span style="color: red">${transaction.targetAccount.accountNumber}</span>
                        </c:if>
                        <c:if test="${transaction.sourceAccount.id != account.id}">
                            <span style="color: green">${transaction.sourceAccount.accountNumber}</span>
                        </c:if>
                    </td>
                    <td>${transaction.amount}</td>
                    <td>${transaction.date}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty transactions}">
        <p>No transactions found for this account.</p>
    </c:if>
</body>
</html>
