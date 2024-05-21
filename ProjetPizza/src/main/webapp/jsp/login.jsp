<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="header">
        <h1>Login to PizzaApp</h1>
    </div>
    <div class="container">
        <form action="login" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username">
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
            
            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>
