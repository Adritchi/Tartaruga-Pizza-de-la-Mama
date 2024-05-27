package com.pizzaapp.controllers;

import com.pizzaapp.models.Ingredient;
import com.pizzaapp.models.Order;
import com.pizzaapp.models.Pizza;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderServlet doPost method called."); // Debug message
        try {
            List<Pizza> pizzas = new ArrayList<>();
            for (int i = 0; ; i++) {
                String size = request.getParameter("pizza" + i + "Size");
                if (size == null) break;
                String crust = request.getParameter("pizza" + i + "Crust");
                String sauce = request.getParameter("pizza" + i + "Base");

                List<Ingredient> ingredients = new ArrayList<>();
                for (int j = 0; ; j++) {
                    String ingredient = request.getParameter("pizza" + i + "Ingredient" + j);
                    if (ingredient == null) break;
                    ingredients.add(new Ingredient(ingredient, "0")); // Replace "0" with the actual price if necessary
                }

                pizzas.add(new Pizza(size, crust, sauce, ingredients));
            }

            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = new Order(orderId, pizzas);

            Database.saveOrder(order);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Commande confirmée et enregistrée avec succès.");
            System.out.println("Order successfully saved."); // Confirmation message

        } catch (Exception e) {
            System.err.println("Error in OrderServlet: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error saving order", e);
        }
    }
}
