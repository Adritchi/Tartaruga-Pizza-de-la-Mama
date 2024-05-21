package com.pizzaapp.controllers;

import com.pizzaapp.models.Ingredient;
import com.pizzaapp.models.Pizza;
import com.pizzaapp.models.Order;
import com.pizzaapp.utils.Database;

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
    private static int orderIdCounter = 1;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String size = request.getParameter("size");
        String crust = request.getParameter("crust");
        String sauce = request.getParameter("sauce");
        String[] selectedIngredients = request.getParameterValues("ingredients");

        List<Ingredient> ingredients = new ArrayList<>();
        if (selectedIngredients != null) {
            for (String ingredient : selectedIngredients) {
                ingredients.add(new Ingredient(ingredient));
            }
        }

        Pizza pizza = new Pizza(size, crust, sauce, ingredients);
        Order order = new Order(orderIdCounter++, pizza);

        try {
            Database.saveOrder(order);
        } catch (Exception e) {
            throw new ServletException("Error saving order", e);
        }

        request.setAttribute("pizza", pizza);
        request.getRequestDispatcher("jsp/customize.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Ingredient> ingredients = Database.getIngredients();
            List<String> sizes = Database.getSizes();
            List<String> bases = Database.getBases();
            List<String> crusts = Database.getCrusts();
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
    

