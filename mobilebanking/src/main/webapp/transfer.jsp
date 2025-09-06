<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Transfer Money</title>
</head>
<body>
<h1>Transfer Money</h1>
<c:if test="${not empty error}">
  <p style="color:red;">${error}</p>
</c:if>
<form action="banking" method="post">
  <input type="hidden" name="action" value="transfer">
  Target Account Number: <input type="text" name="targetAccountNumber"><br/>
  Amount: <input type="text" name="amount"><br/>
  <input type="submit" value="Transfer">
</form>
<a href="banking?action=dashboard">Back to Dashboard</a>
</body>
</html>
