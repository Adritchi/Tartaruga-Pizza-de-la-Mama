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
    <title>Customize Your Pizza - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Customize Your Pizza</h1>
    </div>
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
    <div class="navbar">
    <a href="home.jsp">Accueil</a>
    <a href="customize.jsp">Je personnalise ma pizza</a>
    <a href="account.jsp">Mon Compte</a>
</div>
    <div class="container">
        <div class="customization-container">
            <!-- Section 1: Liste des ingrédients -->
            <div class="customization-left">
                <h2>Crée Ta Pizza - Plus d'un Million de Possibilités</h2>
                <div class="customization-details">
                    <form id="customize-form" action="customize" method="post">
                        <div>
                            <h2>Taille</h2>
                            <%
                                List<String> sizes = (List<String>) request.getAttribute("sizes");
                                if (sizes != null) {
                                    for (String size : sizes) {
                            %>
                                        <label>
                                            <input type="radio" name="size" value="<%= size %>" <%= size.equals("Medium") ? "checked" : "" %>> <%= size %>
                                        </label>
                            <%
                                    }
                                } else {
                            %>
                                    <p>No sizes available.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
                            <h2>Pâte</h2>
                            <%
                                List<String> crusts = (List<String>) request.getAttribute("crusts");
                                if (crusts != null) {
                                    for (String crust : crusts) {
                            %>
                                        <label>
                                            <input type="radio" name="crust" value="<%= crust %>" <%= crust.equals("Classique") ? "checked" : "" %>> <%= crust %>
                                        </label>
                            <%
                                    }
                                } else {
                            %>
                                    <p>No crusts available.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
                            <h2>Sauce</h2>
                            <%
                                List<String> bases = (List<String>) request.getAttribute("bases");
                                if (bases != null) {
                                    for (String base : bases) {
                            %>
                                        <label>
                                            <input type="radio" name="sauce" value="<%= base %>" <%= base.equals("Base Sauce Tomate") ? "checked" : "" %>> <%= base %>
                                        </label>
                            <%
                                    }
                                } else {
                            %>
                                    <p>No bases available.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
                            <h2>Ingrédients</h2>
                            <div class="customization-ingredients">
                                <%
                                    List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
                                    if (ingredients != null) {
                                        for (Ingredient ingredient : ingredients) {
                                %>
                                            <div class="ingredient-item">
                                                <input type="checkbox" name="ingredients" value="<%= ingredient.getName() %>" id="ingredient-<%= ingredient.getName() %>">
                                                <label for="ingredient-<%= ingredient.getName() %>">
                                                    <img src="images/<%= ingredient.getName().toLowerCase() %>.png" alt="<%= ingredient.getName() %>">
                                                    <span><%= ingredient.getName() %></span>
                                                </label>
                                            </div>
                                <%
                                        }
                                    } else {
                                %>
                                        <p>No ingredients available.</p>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                        <button type="submit" class="add-button">Ajouter</button>
                    </form>
                </div>
            </div>
            <!-- Section 2: Image rendue de la pizza -->
            <div class="customization-center">
                <h2>Prévisualisation de la Pizza</h2>
                <div id="pizza-image" class="pizza-preview">
                    <!-- JavaScript will dynamically update this -->
                </div>
            </div>
            <!-- Section 3: Détail de la commande -->
            <div class="customization-right">
                <h2>Détail de la commande</h2>
                <div class="customization-details">
                    <h3>Ingrédients Sélectionnés :</h3>
                    <ul id="selected-ingredients">
                        <%
                            Pizza pizza = (Pizza) request.getAttribute("pizza");
                            if (pizza != null) {
                                for (Ingredient ingredient : pizza.getIngredients()) {
                        %>
                                    <li><%= ingredient.getName() %></li>
                        <%
                                }
                            }
                        %>
                    </ul>
                </div>
                <button id="confirm-order" class="add-button">Confirmer la commande</button>
            </div>
        </div>
    </div>
    <script src="js/customize.js"></script>
</body>
</html>
