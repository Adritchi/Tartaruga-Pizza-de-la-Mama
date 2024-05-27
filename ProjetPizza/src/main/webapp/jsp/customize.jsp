<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pizzaapp.models.*" %>
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
            <div class="customization-left">
                <h2>Crée Ta Pizza - Plus d'un Million de Possibilités</h2>
                <div class="customization-details">
                    <form id="customize-form" action="customize" method="post">
                        <div>
                            <h2>Taille</h2>
                            <%
                                List<Size> sizes = Database.getSizes();
                                if (sizes != null && !sizes.isEmpty()) {
                                    for (Size size : sizes) {
                            %>
                                        <div class="size-item">
                                            <input type="radio" name="size" value="<%= size.getName() %>" id="size-<%= size.getName() %>">
                                            <label for="size-<%= size.getName() %>">
                                                <img src="images/<%= size.getName().toLowerCase() %>.png" alt="<%= size.getName() %>">
                                                <span><%= size.getName() %> - <%= size.getSize() %> - <%= size.getPrice() %> € </span>
                                            </label>
                                        </div>
                            <%
                                    }
                                } else {
                            %>
                                    <p>Aucune taille disponible.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
                            <h2>Pâte</h2>
                            <%
                                List<Crust> crusts = Database.getCrusts();
                                if (crusts != null && !crusts.isEmpty()) {
                                    for (Crust crust : crusts) {
                            %>
                                        <div class="crust-item">
                                            <input type="radio" name="crust" value="<%= crust.getName() %>" id="crust-<%= crust.getName() %>">
                                            <label for="crust-<%= crust.getName() %>">
                                                <img src="images/<%= crust.getName().toLowerCase() %>.png" alt="<%= crust.getName() %>">
                                                <span><%= crust.getName() %> - <%= crust.getPrice() %> €</span>
                                            </label>
                                        </div>
                            <%
                                    }
                                } else {
                            %>
                                    <p>Aucune pâte disponible.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
                            <h2>Sauce</h2>
                            <%
                                List<Bases> bases = Database.getBases();
                                if (bases != null && !bases.isEmpty()) {
                                    for (Bases base : bases) {
                            %>
                                        <div class="base-item">
                                            <input type="radio" name="base" value="<%= base.getName() %>" id="base-<%= base.getName() %>">
                                            <label for="base-<%= base.getName() %>">
                                                <img src="images/<%= base.getName().toLowerCase() %>.png" alt="<%= base.getName() %>">
                                                <span><%= base.getName() %> - <%= base.getPrice() %> €</span>
                                            </label>
                                        </div>
                            <%
                                    }
                                } else {
                            %>
                                    <p>Aucune sauce disponible.</p>
                            <%
                                }
                            %>
                        </div>
                        <div>
<h2>Ingrédients</h2>
<div class="customization-ingredients">
    <%
        List<Ingredient> ingredients = Database.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
    %>
                <div class="ingredient-item">
                    <input type="checkbox" name="ingredients" value="<%= ingredient.getName().toLowerCase() %>" id="ingredient-<%= ingredient.getName().toLowerCase() %>">
                    <label for="ingredient-<%= ingredient.getName().toLowerCase() %>">
                        <img src="images/<%= ingredient.getName().toLowerCase() %>.png" alt="<%= ingredient.getName() %>">
                        <span><%= ingredient.getName() %> - <%= ingredient.getPrice() %> €</span>
                    </label>
                </div>
    <%
            }
        } else {
    %>
            <p>Aucun ingrédient disponible.</p>
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
<script>
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("customize-form");
    const pizzaImage = document.getElementById("pizza-image");

    form.addEventListener("change", function() {
        const formData = new FormData(form);
        const crust = formData.get("crust");
        const base = formData.get("base");
        const ingredients = formData.getAll("ingredients");

        console.log("Selected crust:", crust); // Log selected crust
        console.log("Selected base:", base); // Log selected base
        console.log("Selected ingredients:", ingredients); // Log selected ingredients

        // Clear the current pizza image
        pizzaImage.innerHTML = "";

        // Add crust image if selected
        if (crust) {
            const crustImagePath = ("images/" + ${crust} + ".png");
            console.log("Crust image path:", crustImagePath); // Log the crust image path
            const crustImg = document.createElement("img");
            crustImg.src = crustImagePath;
            crustImg.alt = crust;
            crustImg.className = "ingredient-preview";
            crustImg.onerror = function() {
                console.error(`Crust image not found at ${crustImagePath}`);
            };
            pizzaImage.appendChild(crustImg);
        }

        // Add base image if selected
        if (base) {
            const baseImagePath = `images/${base.toLowerCase()}.png`;
            console.log("Base image path:", baseImagePath); // Log the base image path
            const baseImg = document.createElement("img");
            baseImg.src = baseImagePath;
            baseImg.alt = base;
            baseImg.className = "ingredient-preview";
            baseImg.onerror = function() {
                console.error(`Base image not found at ${baseImagePath}`);
            };
            pizzaImage.appendChild(baseImg);
        }

        // Add ingredient images
        ingredients.forEach(ingredientName => {
            if (ingredientName) {
                const ingredientImagePath = ("images/" + ${ingredientName} + ".png");
                console.log("Ingredient image path:", ingredientImagePath); // Log the ingredient image path
                const img = document.createElement("img");
                img.src = ingredientImagePath;
                img.alt = ingredientName;
                img.className = "ingredient-preview";
                img.onerror = function() {
                    console.error(`Ingredient image not found at ${ingredientImagePath}`);
                };
                pizzaImage.appendChild(img);
            } else {
                console.error("Ingredient name is empty");
            }
        });
    });
});


</script>
</body>
</html>
