package com.pizzaapp.controllers;

import com.pizzaapp.models.*;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomizationServlet extends HttpServlet {
    private static int orderIdCounter = 1;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String size = request.getParameter("size");
        String crust = request.getParameter("crust");
        String sauce = request.getParameter("sauce");
        String[] selectedIngredients = request.getParameterValues("ingredients");

        List<Ingredient> ingredients = new ArrayList<>();
        if (selectedIngredients != null) {
            for (String ingredient : selectedIngredients) {
                String[] parts = ingredient.split(":");
                String name = parts[0];
                String price = parts[1];
                ingredients.add(new Ingredient(name, price));
            }
        }

        Pizza pizza = new Pizza(size, crust, sauce, ingredients);

        // Récupérer les commandes existantes pour générer un nouvel ID
        List<Order> orders;
        try {
            orders = Database.getOrders();
        } catch (Exception e) {
            throw new ServletException("Error retrieving orders", e);
        }
        int newOrderId = orders.size() + 1;

        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizza);

        Order order = new Order(newOrderId, pizzas);

        try {
            Database.saveOrder(order);
        } catch (Exception e) {
            throw new ServletException("Error saving order", e);
        }

        request.setAttribute("pizza", pizza);
        request.getRequestDispatcher("jsp/customize.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            List<Ingredient> ingredients = Database.getIngredients();
            List<Size> sizes = Database.getSizes();
            List<Bases> bases = Database.getBases();
            List<Crust> crusts = Database.getCrusts();
            request.setAttribute("ingredients", ingredients);
            request.setAttribute("sizes", sizes);
            request.setAttribute("bases", bases);
            request.setAttribute("crusts", crusts);
            request.getRequestDispatcher("jsp/customize.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading data", e);
        }
    }
}
