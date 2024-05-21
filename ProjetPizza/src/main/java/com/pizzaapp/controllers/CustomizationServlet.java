package com.pizzaapp.controllers;

import com.pizzaapp.models.Ingredient;
import com.pizzaapp.models.Pizza;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/customize")
public class CustomizationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ingredient> ingredients = new ArrayList<>();
        // Add default ingredients for demonstration purposes
        ingredients.add(new Ingredient("Cheese", 1.00));
        ingredients.add(new Ingredient("Pepperoni", 1.50));
        ingredients.add(new Ingredient("Mushrooms", 1.00));
        
        request.setAttribute("ingredients", ingredients);
        request.getRequestDispatcher("jsp/customize.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedIngredients = request.getParameterValues("ingredients");
        Pizza pizza = new Pizza();
        if (selectedIngredients != null) {
            for (String ingredient : selectedIngredients) {
                pizza.addIngredient(new Ingredient(ingredient, 1.00)); // Simplified for example
            }
        }
        request.setAttribute("pizza", pizza);
        request.getRequestDispatcher("jsp/order.jsp").forward(request, response);
    }
}
