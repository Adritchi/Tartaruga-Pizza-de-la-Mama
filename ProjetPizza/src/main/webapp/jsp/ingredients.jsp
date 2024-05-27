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
    <title>Manage Ingredients - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="header">
        <h1>Manage Ingredients</h1>
    </div>
    <div class="container">
        <h2>Add New Ingredient</h2>
        <form action="ingredients" method="post">
            <input type="text" name="name" placeholder="Ingredient Name" required>
            <button type="submit">Add Ingredient</button>
        </form>

        <h2>Current Ingredients</h2>
        <ul>
            <%
                List<Ingredient> ingredients = Database.getIngredients();
                if (ingredients != null) {
                    for (Ingredient ingredient : ingredients) {
            %>
                        <li><%= ingredient.getName() %></li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</body>
</html>
