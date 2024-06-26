<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pizzaapp.models.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/styleslogin.css">
</head>
<body>
    <!-- En-tête de la page avec le titre et les options de connexion/déconnexion -->
    <div class="header">
        <h1>Connexion</h1>
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

    <!-- Contenu principal de la page de connexion -->
    <div class="container">
        <div class="account-form">
            <h2>Connexion</h2>
            <!-- Formulaire de connexion -->
            <form action="<%=request.getContextPath()%>/login" method="post">
                <div class="form-group">
                    <label for="name">Nom :</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe :</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn">Se connecter</button>
                </div>
            </form>
            <p>Vous n'avez pas de compte ? <a href="<%=request.getContextPath()%>/jsp/register.jsp">Inscrivez-vous ici</a></p>
            <!-- Affichage d'un message d'erreur en cas de problème de connexion -->
            <% if(request.getAttribute("error") != null) { %>
                <p style="color:red;"><%= request.getAttribute("error") %></p>
            <% } %>
        </div>
    </div>
</body>
</html>
