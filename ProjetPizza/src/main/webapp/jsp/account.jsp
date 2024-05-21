<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>My Account - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="header">
        <h1>My Account</h1>
    </div>
    <div class="container">
        <form action="account" method="post">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${user.name}">
            
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="${user.address}">
            
            <label for="phone">Phone:</label>
            <input type="text" id="phone" name="phone" value="${user.phone}">
            
            <button type="submit">Save</button>
        </form>
    </div>
</body>
</html>
