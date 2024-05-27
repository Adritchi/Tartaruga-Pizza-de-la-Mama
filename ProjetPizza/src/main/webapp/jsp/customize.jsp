<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pizzaapp.models.Pizza" %>
<%@ page import="com.pizzaapp.models.Ingredient" %>
<%@ page import="com.pizzaapp.models.Crust" %>
<%@ page import="com.pizzaapp.models.Size" %>
<%@ page import="com.pizzaapp.models.Bases" %>
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
                                List<Size> sizes = Database.getSizes();
                                if (sizes != null) {
                                    for (Size size : sizes) {
                            %>
                                        <div class="size-item">
                                            <input type="checkbox" name="sizes" value="<%= size.getName() %>:<%= size.getPrice() %>" id="size-<%= size.getName() %>">
                                            <label for="size-<%= size.getName() %>">
                                                <img src="images/<%= size.getName().toLowerCase() %>.svg" alt="<%= size.getName() %>">
                                                <span><%= size.getName() %> - <%= size.getSize() %> - <%= size.getPrice() %> € </span>
                                            </label>
                                        </div>
                            <%
                                    }
                                } else {
                            %>
                                    <p>No size available.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
                            <h2>Pâte</h2>
                            <%
                                List<Crust> crusts = Database.getCrusts();
                                if (crusts != null) {
                                    for (Crust crust : crusts) {
                            %>
                                        <div class="crust-item">
                                            <input type="checkbox" name="ingredients" value="<%= crust.getName() %>:<%= crust.getPrice() %>" id="crust-<%= crust.getName() %>">
                                            <label for="crust-<%= crust.getName() %>">
                                                <img src="images/<%= crust.getName().toLowerCase() %>.svg" alt="<%= crust.getName() %>">
                                                <span><%= crust.getName() %> - $<%= crust.getPrice() %></span>
                                            </label>
                                        </div>
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
                                List<Bases> bases = Database.getBases();
                                if (bases != null) {
                                    for (Bases base : bases) {
                            %>
                                        <div class="base-item">
                                            <input type="checkbox" name="ingredients" value="<%= base.getName() %>:<%= base.getPrice() %>" id="base-<%= base.getName() %>">
                                            <label for="base-<%= base.getName() %>">
                                                <img src="images/<%= base.getName().toLowerCase() %>.svg" alt="<%= base.getName() %>">
                                                <span><%= base.getName() %> - $<%= base.getPrice() %></span>
                                            </label>
                                        </div>
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
                                    List<Ingredient> ingredients = Database.getIngredients();
                                    if (ingredients != null) {
                                        for (Ingredient ingredient : ingredients) {
                                %>
                                            <div class="ingredient-item">
                                                <input type="checkbox" name="ingredients" value="<%= ingredient.getName() %>:<%= ingredient.getPrice() %>" id="ingredient-<%= ingredient.getName() %>">
                                                <label for="ingredient-<%= ingredient.getName() %>">
                                                    <img src="images/<%= ingredient.getName().toLowerCase() %>.svg" alt="<%= ingredient.getName() %>">
                                                    <span><%= ingredient.getName() %> - <%= ingredient.getPrice() %> €</span>
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
