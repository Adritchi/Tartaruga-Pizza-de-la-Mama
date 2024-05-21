<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>My Account - PizzaApp</title>
</head>
<body>
    <h1>My Account</h1>
    <form action="account" method="post">
        Name: <input type="text" name="name" value="${user.name}"><br>
        Address: <input type="text" name="address" value="${user.address}"><br>
        Phone: <input type="text" name="phone" value="${user.phone}"><br>
        <button type="submit">Save</button>
    </form>
</body>
</html>
