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
        // Récupérer l'utilisateur de la session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            // Si l'utilisateur n'est pas connecté, le rediriger vers la page de connexion
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Récupérer les paramètres du formulaire de personnalisation de la pizza
        String size = request.getParameter("size");
        String crust = request.getParameter("crust");
        String sauce = request.getParameter("sauce");
        String[] selectedIngredients = request.getParameterValues("ingredients");

        // Convertir les ingrédients sélectionnés en objets Ingredient
        List<Ingredient> ingredients = new ArrayList<>();
        if (selectedIngredients != null) {
            for (String ingredient : selectedIngredients) {
                String[] parts = ingredient.split(":");
                String name = parts[0];
                String price = parts[1];
                ingredients.add(new Ingredient(name, price));
            }
        }

        // Créer un objet Pizza avec les paramètres récupérés
        Pizza pizza = new Pizza(size, crust, sauce, ingredients);

        // Récupérer les commandes existantes pour générer un nouvel ID de commande
        List<Order> orders;
        try {
            orders = Database.getOrders();
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des commandes", e);
        }
        int newOrderId = orders.size() + 1;

        // Ajouter la nouvelle pizza à une liste de pizzas
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizza);

        // Créer une nouvelle commande avec la liste de pizzas
        Order order = new Order(newOrderId, pizzas);

        // Sauvegarder la nouvelle commande dans la base de données
        try {
            Database.saveOrder(order);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement de la commande", e);
        }

        // Ajouter la pizza à la requête et transférer la requête à la page de personnalisation
        request.setAttribute("pizza", pizza);
        request.getRequestDispatcher("jsp/customize.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'utilisateur de la session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            // Si l'utilisateur n'est pas connecté, le rediriger vers la page de connexion
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            // Récupérer les données nécessaires pour la personnalisation de la pizza
            List<Ingredient> ingredients = Database.getIngredients();
            List<Size> sizes = Database.getSizes();
            List<Bases> bases = Database.getBases();
            List<Crust> crusts = Database.getCrusts();

            // Ajouter les données récupérées à la requête
            request.setAttribute("ingredients", ingredients);
            request.setAttribute("sizes", sizes);
            request.setAttribute("bases", bases);
            request.setAttribute("crusts", crusts);

            // Transférer la requête à la page JSP de personnalisation
            request.getRequestDispatcher("jsp/customize.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement des données", e);
        }
    }
}
