<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pizzaapp.models.Order" %>
<%@ page import="com.pizzaapp.models.Ingredient" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order - PizzaApp</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="header">
        <h1>Your Order</h1>
    </div>
    <div class="container">
        <h2>Order Details</h2>
        <%
            Order order = (Order) request.getAttribute("order");
            if (order != null) {
        %>
            <div>
                <h3>Order ID: <%= order.getId() %></h3>
                <h4>Ingredients:</h4>
                <ul>
                    <%
                        for (Ingredient ingredient : order.getPizza().getIngredients()) {
                    %>
                        <li><%= ingredient.getName() %></li>
                    <%
                        }
                %>
            </ul>
        </div>
    <%
        }
    %>

    <h2>Order History</h2>
    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders != null && !orders.isEmpty()) {
    %>
        <ul>
            <%
                for (Order histOrder : orders) {
            %>
                <li>
                    <strong>Order ID:</strong> <%= histOrder.getId() %><br>
                    <strong>Ingredients:</strong>
                    <ul>
                        <%
                            for (Ingredient ingredient : histOrder.getPizza().getIngredients()) {
                        %>
                            <li><%= ingredient.getName() %></li>
                        <%
                            }
                        %>
                    </ul>
                </li>
            <%
                }
            %>
        </ul>
    <%
        } else {
    %>
        <p>No orders found.</p>
    <%
        }
    %>
</div>
                        
                        </body>
</html>