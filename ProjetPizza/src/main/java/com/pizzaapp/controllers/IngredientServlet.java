package com.pizzaapp.controllers;

import com.pizzaapp.models.Ingredient;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ingredients")
public class IngredientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Ingredient> ingredients = Database.getIngredients();
            request.setAttribute("ingredients", ingredients);
        } catch (Exception e) {
            throw new ServletException("Error loading ingredients", e);
        }
        request.getRequestDispatcher("jsp/ingredients.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ingredientName = request.getParameter("name");
        if (ingredientName != null && !ingredientName.trim().isEmpty()) {
            Ingredient ingredient = new Ingredient(ingredientName);
            try {
                Database.saveIngredient(ingredient);
            } catch (Exception e) {
                throw new ServletException("Error saving ingredient", e);
            }
        }
        response.sendRedirect("ingredients");
    }
}
