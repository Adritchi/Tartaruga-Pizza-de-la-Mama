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
    <title>Home - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Bienvenue à Tartaruga Della Pizza Della Mama !</h1>
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
        <div class="section">
            <h2>La meilleure Pizza Italienne !</h2>
            <p>Bernadette, notre tortue, prépare des pizzas d'exceptions selon vos préférences.</p>
            <div class="features">
                <div class="feature">
                    <img src="images/ingredient-icon.webp" alt="Fresh Ingredients">
                    <h3>Des ingrédients frais !</h3>
                    <p>Issues du potager et du jardin de Bernadette à 30km de Rome</p>
                </div>
                <div class="feature">
                    <img src="images/delivery-icon.webp" alt="Fast Delivery">
                    <h3>Une livraison rapide</h3>
                    <p>Ne vous fiez pas à son allure de tortue...</p>
                </div>
                <div class="feature">
                    <img src="images/account-icon.png" alt="Easy Account Management">
                    <h3>Gestion de votre compte</h3>
                    <p>Gérer votre compte facilement.</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
