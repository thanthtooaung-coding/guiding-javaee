<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Transfer Money</title>
</head>
<body>
    <h1>Transfer Money</h1>
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>
    <form action="banking" method="post">
        <input type="hidden" name="action" value="transfer">
        From Account:
        <select name="sourceAccountNumber">
            <c:forEach items="${accounts}" var="account">
                <option value="${account.accountNumber}">${account.accountName} (${account.accountType}) - Balance: ${account.balance}</option>
            </c:forEach>
        </select>
        Target Account: <input type="text" name="targetAccountNumber">
        Amount: <input type="text" name="amount">
        <input type="submit" value="Transfer">
    </form>
    <a href="banking?action=dashboard">Back to Dashboard</a>
</body>
</html>
