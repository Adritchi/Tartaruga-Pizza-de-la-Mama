<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order - PizzaApp</title>
</head>
<body>
    <h1>Your Order</h1>
    <p>Pizza with ingredients:</p>
    <ul>
        <c:forEach var="ingredient" items="${pizza.ingredients}">
            <li>${ingredient.name}</li>
        </c:forEach>
    </ul>
    <p>Total Price: ${pizza.totalPrice}€</p>
    <form action="order" method="post">
        <button type="submit">Confirm Order</button>
    </form>
</body>
</html>
