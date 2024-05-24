<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pizzaapp.models.Ingredient" %>
<%@ page import="com.pizzaapp.models.Pizza" %>
<%@ page import="com.pizzaapp.models.Order" %>
<%@ page import="com.pizzaapp.models.User" %>
<%@ page import="com.pizzaapp.utils.Database" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/stylesregister.css">
</head>
<body>
    <div class="header">
        <h1>Inscription</h1>
        <div class="user-greeting">
            <%
                User user = (User) session.getAttribute("user");
                if (user != null) {
            %>
                <span class="greeting-text">Bonjour <%= user.getName() %>!</span>
                <a class="auth-link" href="logout.jsp">Se déconnecter</a>
            <%
                } else {
            %>
                <a class="auth-link" href="login.jsp">Se connecter</a> |
                <a class="auth-link" href="register.jsp">S'inscrire</a>
            <%
                }
            %>
        </div>
    </div>
    <div class="navbar">
        <a href="home.jsp">Accueil</a>
        <a href="customize.jsp">Je personnalise ma pizza</a>
        <a href="account.jsp">Mon Compte</a>
    </div>
    <div class="container">
        <div class="register-form">
            <h2>Inscription</h2>
            <form action="<%=request.getContextPath()%>/register" method="post">
                <div class="form-group">
                    <label for="name">Nom :</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="address">Adresse :</label>
                    <input type="text" id="address" name="address" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="phone">Téléphone :</label>
                    <input type="text" id="phone" name="phone" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe :</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn">S'inscrire</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
