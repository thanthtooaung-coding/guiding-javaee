<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Account</title>
</head>
<body>
    <h1>Open a New Bank Account</h1>
    <form action="banking" method="post">
        <input type="hidden" name="action" value="createAccount">
        Account Name: <input type="text" name="accountName" required><br>
        Account Type:
        <select name="accountType" required>
            <option value="Checking">Checking</option>
            <option value="Savings">Savings</option>
        </select>
        Initial Deposit: <input type="number" name="initialDeposit" required><br>
        <input type="submit" value="Create Account">
    </form>
</body>
</html>
