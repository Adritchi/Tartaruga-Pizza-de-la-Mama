<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pizzaapp.models.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mon Compte - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="stylesacc.css">
</head>
<body>
    <!-- En-tête de la page avec le titre et les options de connexion/déconnexion -->
    <div class="header">
        <h1>Mon Compte</h1>
        <div class="user-greeting">
            <%
                // Récupérer l'utilisateur connecté à partir de la session
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

    <!-- Barre de navigation avec des liens vers différentes pages -->
    <div class="navbar">
        <a href="home.jsp">Accueil</a>
        <a href="customize.jsp">Je personnalise ma pizza</a>
        <a href="account.jsp">Mon Compte</a>
    </div>

    <!-- Contenu principal de la page de compte -->
    <div class="container">
        <div class="account-form">
            <!-- Formulaire de mise à jour des informations de compte -->
            <form action="<%=request.getContextPath()%>/account" method="post">
                <div class="form-group">
                    <label for="name">Nom :</label>
                    <input type="text" id="name" name="name" class="form-control" value="<%= user.getName() %>">
                </div>
                <div class="form-group">
                    <label for="address">Adresse :</label>
                    <input type="text" id="address" name="address" class="form-control" value="<%= user.getAddress() %>">
                </div>
                <div class="form-group">
                    <label for="phone">Téléphone :</label>
                    <input type="text" id="phone" name="phone" class="form-control" value="<%= user.getPhone() %>">
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe :</label>
                    <input type="password" id="password" name="password" class="form-control" value="<%= user.getPassword() %>">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn">Sauvegarder</button>
                </div>
                <!-- Affichage d'un message de succès si présent dans la session -->
                <%
                    String successMessage = (String) session.getAttribute("successMessage");
                    if (successMessage != null) {
                %>
                    <p class="success-message"><%= successMessage %></p>
                    <%
                        // Supprimer le message après l'avoir affiché
                        session.removeAttribute("successMessage");
                    %>
                <%
                    }
                %>
            </form>
        </div>
    </div>
</body>
</html>
