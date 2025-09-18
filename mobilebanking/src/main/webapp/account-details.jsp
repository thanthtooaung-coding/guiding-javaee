<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Account Details</title>
</head>
<body>
<h1>Account Details: ${account.accountName} (${account.accountType})</h1>
<p>Account Number: ${account.accountNumber}</p>
<p>Current Balance: <span style="font-weight: bold; color: green;">$${account.balance}</span></p>

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
                            <span style="color:red; font-weight:bold;">Sent</span>
                        </c:when>
                        <c:otherwise>
                            <span style="color:green; font-weight:bold;">Received</span>
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
    <p>No transactions found for this account.</p>
</c:if>
<br/>
<a href="banking?action=dashboard">Back to Dashboard</a>
</body>
</html>
