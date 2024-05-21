<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customize Pizza - PizzaApp</title>
</head>
<body>
    <h1>Customize your Pizza</h1>
    <form action="customize" method="post">
        <label for="ingredients">Choose your ingredients:</label><br>
        <c:forEach var="ingredient" items="${ingredients}">
            <input type="checkbox" name="ingredients" value="${ingredient.name}">${ingredient.name} (${ingredient.price}€)<br>
        </c:forEach>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
