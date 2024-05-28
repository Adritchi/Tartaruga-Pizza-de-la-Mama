<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pizzaapp.models.*" %>
<%@ page import="com.pizzaapp.utils.Database" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personnalise ta Pizza - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <!-- En-tête de la page avec le titre principal -->
    <div class="header">
        <h1>Customise ta propre Pizza</h1>
    </div>

    <!-- Section pour saluer l'utilisateur s'il est connecté, sinon afficher les liens de connexion et d'inscription -->
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

    <!-- Barre de navigation avec les liens vers les différentes sections du site -->
    <div class="navbar">
        <a href="home.jsp">Accueil</a>
        <a href="customize.jsp">Je personnalise ma pizza</a>
        <a href="account.jsp">Mon Compte</a>
    </div>

    <!-- Conteneur principal de la page -->
    <div class="container">
        <div class="customization-container">
            <div class="customization-left">
                <h2>Plus d'un Million de Possibilités</h2>
                <div class="customization-details">
                    <!-- Formulaire de personnalisation de la pizza -->
                    <form id="customize-form" action="customize" method="post">
                        <!-- Sélection de la taille de la pizza -->
                        <div class="customization-group">
                            <h2>Taille</h2>
                            <div class="options">
                                <% List<Size> sizes = Database.getSizes(); %>
                                <% if (sizes != null && !sizes.isEmpty()) { %>
                                    <% for (Size size : sizes) { %>
                                        <div class="option-item">
                                            <input type="radio" name="size" value="<%= size.getName() %>" id="size-<%= size.getName() %>" data-price="<%= size.getPrice() %>">
                                            <label for="size-<%= size.getName() %>">
                                                <span><%= size.getName() %> - <%= size.getSize() %> - <%= size.getPrice() %> €</span>
                                            </label>
                                        </div>
                                    <% } %>
                                <% } else { %>
                                    <p>Aucune taille disponible.</p>
                                <% } %>
                            </div>
                        </div>
                        
                        <!-- Sélection de la pâte de la pizza -->
                        <div class="customization-group">
                            <h2>Pâte</h2>
                            <div class="dropdown">
                                <select id="crust-select" name="crust" disabled>
                                    <option value="">Sélectionner une pâte</option>
                                    <% List<Crust> crusts = Database.getCrusts(); %>
                                    <% if (crusts != null && !crusts.isEmpty()) { %>
                                        <% for (Crust crust : crusts) { %>
                                            <option value="<%= crust.getName() %>" data-price="<%= crust.getPrice() %>">
                                                <%= crust.getName() %> - <%= crust.getPrice() %> €
                                            </option>
                                        <% } %>
                                    <% } else { %>
                                        <option value="">Aucune pâte disponible.</option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        
                        <!-- Sélection de la sauce de la pizza -->
                        <div class="customization-group">
                            <h2>Sauce</h2>
                            <div class="dropdown">
                                <select id="base-select" name="base" disabled>
                                    <option value="">Sélectionner une sauce</option>
                                    <% List<Bases> bases = Database.getBases(); %>
                                    <% if (bases != null && !bases.isEmpty()) { %>
                                        <% for (Bases base : bases) { %>
                                            <option value="<%= base.getName() %>" data-price="<%= base.getPrice() %>">
                                                <%= base.getName() %> - <%= base.getPrice() %> €
                                            </option>
                                        <% } %>
                                    <% } else { %>
                                        <option value="">Aucune sauce disponible.</option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        
                        <!-- Sélection des ingrédients pour la pizza -->
                        <div class="customization-group">
                            <h2>Ingrédients</h2>
                            <div class="customization-ingredients" id="ingredients-container">
                                <% List<Ingredient> ingredients = Database.getIngredients(); %>
                                <% if (ingredients != null && !ingredients.isEmpty()) { %>
                                    <% for (Ingredient ingredient : ingredients) { %>
                                        <div class="ingredient-item">
                                            <input type="checkbox" name="ingredient" value="<%= ingredient.getName().toLowerCase() %>" id="ingredient-<%= ingredient.getName().toLowerCase() %>" data-price="<%= ingredient.getPrice() %>" disabled>
                                            <label for="ingredient-<%= ingredient.getName().toLowerCase() %>">
                                                <img src="images/<%= ingredient.getName().toLowerCase() %>.png" alt="<%= ingredient.getName() %>">
                                                <span><%= ingredient.getName() %> - <%= ingredient.getPrice() %> €</span>
                                            </label>
                                        </div>
                                    <% } %>
                                <% } else { %>
                                    <p>Aucun ingrédient disponible.</p>
                                <% } %>
                            </div>
                        </div>
                        <!-- Bouton pour ajouter la pizza personnalisée -->
                        <button type="submit" class="add-button" id="add-button" disabled>Ajouter</button>
                    </form>
                </div>
            </div>
            <!-- Section 2: Image rendue de la pizza -->
            <div class="customization-center">
                <h2>Prévisualisation de la Pizza</h2>
                <div id="pizza-image" class="pizza-preview">
                    <!-- JavaScript va mettre à jour dynamiquement cette section -->
                </div>
            </div>
            <!-- Section 3: Détail de la commande -->
            <div class="customization-right">
                <h2>Détail de la commande</h2>
                <div class="customization-details">
                    <h3>Ingrédients Sélectionnés :</h3>
                    <ul id="selected-ingredients" class="ingredient-list">
                        <!-- Les ingrédients sélectionnés pour la pizza actuelle -->
                    </ul>
                    <h3>Pizzas Commandées :</h3>
                    <div id="ordered-pizzas" class="pizza-list">
                        <!-- Liste des pizzas commandées -->
                    </div>
                    <button id="new-pizza-button" class="add-button new-pizza-button">Nouvelle Pizza</button>
                </div>
                <div>
                    <h3>Prix Total :</h3>
                    <p id="total-price">0 €</p>
                </div>
                <button id="confirm-order" class="add-button confirm-button">Confirmer la commande</button>
            </div>
        </div>
    </div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("customize-form");
    const pizzaImage = document.getElementById("pizza-image");
    const crustSelect = document.getElementById("crust-select");
    const baseSelect = document.getElementById("base-select");
    const sizeOptions = document.querySelectorAll("input[name='size']");
    const ingredientCheckboxes = document.querySelectorAll(".ingredient-item input[type='checkbox']");
    const addButton = document.getElementById("add-button");
    const orderedPizzasList = document.getElementById("ordered-pizzas");
    const selectedIngredientsList = document.getElementById("selected-ingredients");
    const newPizzaButton = document.getElementById("new-pizza-button");
    const totalPriceElement = document.getElementById("total-price");
    const confirmOrderButton = document.getElementById("confirm-order");

    let pizzas = [];
    let totalPrice = 0; // Initialiser le prix total

    // Fonction pour réinitialiser le formulaire
    function clearForm() {
        sizeOptions.forEach(option => option.checked = false);
        crustSelect.disabled = true;
        crustSelect.value = "";
        baseSelect.disabled = true;
        baseSelect.value = "";
        ingredientCheckboxes.forEach(checkbox => {
            checkbox.checked = false;
            checkbox.disabled = true;
        });
        addButton.disabled = true;
        pizzaImage.innerHTML = "";
        selectedIngredientsList.innerHTML = "";
    }

    // Fonction pour calculer le prix de la pizza actuelle
    function calculateCurrentPizzaPrice() {
        let currentPizzaPrice = 0;
        const selectedCrust = crustSelect.options[crustSelect.selectedIndex];
        const selectedBase = baseSelect.options[baseSelect.selectedIndex];

        sizeOptions.forEach(option => {
            if (option.checked) {
                currentPizzaPrice += parseFloat(option.getAttribute("data-price"));
            }
        });

        if (selectedCrust && selectedCrust.value) {
            currentPizzaPrice += parseFloat(selectedCrust.getAttribute("data-price"));
        }

        if (selectedBase && selectedBase.value) {
            currentPizzaPrice += parseFloat(selectedBase.getAttribute("data-price"));
        }

        ingredientCheckboxes.forEach(checkbox => {
            if (checkbox.checked) {
                currentPizzaPrice += parseFloat(checkbox.getAttribute("data-price"));
            }
        });

        return currentPizzaPrice;
    }

    // Fonction pour mettre à jour le prix de la pizza actuelle
    function updateCurrentPizzaPrice() {
        const currentPizzaPrice = calculateCurrentPizzaPrice();
        totalPriceElement.textContent = (totalPrice + currentPizzaPrice).toFixed(2) + " €";
    }

    // Gestionnaire d'événements pour les changements dans le formulaire
    form.addEventListener("change", function() {
        const formData = new FormData(form);
        const size = formData.get("size");
        const crust = formData.get("crust");
        const base = formData.get("base");
        const ingredients = formData.getAll("ingredient");

        if (size) {
            crustSelect.disabled = false;
        } else {
            crustSelect.disabled = true;
            crustSelect.value = "";
            baseSelect.disabled = true;
            baseSelect.value = "";
            ingredientCheckboxes.forEach(checkbox => checkbox.disabled = true);
            addButton.disabled = true;
        }

        if (crust) {
            baseSelect.disabled = false;
        } else {
            baseSelect.disabled = true;
            baseSelect.value = "";
            ingredientCheckboxes.forEach(checkbox => checkbox.disabled = true);
            addButton.disabled = true;
        }

        if (base) {
            ingredientCheckboxes.forEach(checkbox => checkbox.disabled = false);
            addButton.disabled = false;
        } else {
            ingredientCheckboxes.forEach(checkbox => checkbox.disabled = true);
            addButton.disabled = true;
        }

        pizzaImage.innerHTML = "";

        // Fonction pour ajouter des images des ingrédients à la prévisualisation
        function appendImage(src, alt, zIndex) {
            const img = document.createElement("img");
            img.src = src;
            img.alt = alt;
            img.style.zIndex = zIndex;
            img.style.position = 'absolute';
            img.className = "ingredient-preview";
            img.onerror = function() {
                console.error(`Image not found at ${src}`);
            };
            pizzaImage.appendChild(img);
        }

        if (crust) {
            const crustImagePath = "images/" + crust.toLowerCase() + ".png";
            appendImage(crustImagePath, crust, 1);
        }

        if (base) {
            const baseImagePath = "images/" + base.toLowerCase() + ".png";
            appendImage(baseImagePath, base, 2);
        }

        ingredients.forEach((ingredientName, index) => {
            if (ingredientName) {
                const ingredientImagePath = "images/" + ingredientName.toLowerCase() + ".png";
                appendImage(ingredientImagePath, ingredientName, index + 3);
            }
        });

        selectedIngredientsList.innerHTML = "";
        if (size) {
            const sizeItem = document.createElement("li");
            sizeItem.textContent = "Taille : " + size;
            selectedIngredientsList.appendChild(sizeItem);
        }
        if (crust) {
            const crustItem = document.createElement("li");
            crustItem.textContent = "Pâte : " + crust;
            selectedIngredientsList.appendChild(crustItem);
        }
        if (base) {
            const baseItem = document.createElement("li");
            baseItem.textContent = "Sauce : " + base;
            selectedIngredientsList.appendChild(baseItem);
        }
        ingredients.forEach(ingredient => {
            const ingredientItem = document.createElement("li");
            ingredientItem.textContent = ingredient;
            selectedIngredientsList.appendChild(ingredientItem);
        });

        updateCurrentPizzaPrice();
    });
    
    // Fonction pour mettre à jour la liste des pizzas commandées
    function updatePizzaList() {
        orderedPizzasList.innerHTML = '';
        pizzas.forEach((pizza, index) => {
            const pizzaSummary = document.createElement("div");
            pizzaSummary.className = "pizza-item";
            pizzaSummary.innerHTML = `
                <h4>Pizza ${index + 1}</h4>
                <p><strong>Taille :</strong> ${pizza.size}</p>
                <p><strong>Pâte :</strong> ${pizza.crust}</p>
                <p><strong>Sauce :</strong> ${pizza.base}</p>
                <p><strong>Ingrédients :</strong> ${pizza.ingredients.join(", ")}</p>
            `;
            orderedPizzasList.appendChild(pizzaSummary);
        });
    }

    // Gestionnaire d'événements pour la soumission du formulaire
    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const size = formData.get("size");
        const crust = formData.get("crust");
        const base = formData.get("base");
        const ingredients = formData.getAll("ingredient");

        if (size && crust && base) {
            const pizza = {
                size: size,
                crust: crust,
                base: base,
                ingredients: ingredients
            };

            pizzas.push(pizza);

            // Ajouter le prix de la pizza actuelle au prix total une seule fois
            const currentPizzaPrice = calculateCurrentPizzaPrice();
            totalPrice += currentPizzaPrice;

            updatePizzaList();

            // Réinitialiser uniquement l'affichage du prix de la pizza actuelle
            totalPriceElement.textContent = totalPrice.toFixed(2) + " €";

            clearForm();
        }
    });

    // Gestionnaire d'événements pour le bouton de nouvelle pizza
    newPizzaButton.addEventListener("click", function() {
        clearForm();
    });

    // Gestionnaire d'événements pour confirmer la commande
    document.getElementById("confirm-order").addEventListener("click", function() {
        if (pizzas.length === 0) {
            alert("Veuillez ajouter au moins une pizza avant de confirmer la commande.");
            event.preventDefault();
            return;
        }

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/ProjetPizza/order", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                alert("Commande confirmée et enregistrée avec succès.");
            } else if (xhr.readyState === 4) {
                alert("Une erreur est survenue lors de l'enregistrement de la commande.");
            }
        };

        const formData = new URLSearchParams();
        pizzas.forEach((pizza, index) => {
            formData.append(`pizza${index}Size`, pizza.size);
            formData.append(`pizza${index}Crust`, pizza.crust);
            formData.append(`pizza${index}Base`, pizza.base);
            pizza.ingredients.forEach((ingredient, i) => {
                formData.append(`pizza${index}Ingredient${i}`, ingredient);
            });
        });

        xhr.send(formData.toString());
        pizzas = [];
        totalPrice = 0; // Réinitialiser le prix total après confirmation de la commande
        updateCurrentPizzaPrice(); // Mettre à jour le prix total affiché
        clearForm();
    });

    // Ajouter des événements pour les sélecteurs et les cases à cocher des ingrédients pour mettre à jour le prix de la pizza actuelle
    const selects = document.querySelectorAll("select");
    selects.forEach(select => {
        select.addEventListener("change", function() {
            this.blur();
        });
    });

    ingredientCheckboxes.forEach(checkbox => {
        checkbox.addEventListener("change", function() {
            updateCurrentPizzaPrice();
        });
    });

    crustSelect.addEventListener("change", function() {
        updateCurrentPizzaPrice();
    });

    baseSelect.addEventListener("change", function() {
        updateCurrentPizzaPrice();
    });

    sizeOptions.forEach(option => {
        option.addEventListener("change", function() {
            updateCurrentPizzaPrice();
        });
    });
});
</script>


</body>
</html>
