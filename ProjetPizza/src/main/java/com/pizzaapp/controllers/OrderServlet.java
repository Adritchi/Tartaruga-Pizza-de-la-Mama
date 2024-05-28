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

/**
 * Servlet pour gérer les opérations de commande de pizzas.
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    /**
     * Gère les requêtes POST pour créer et enregistrer une nouvelle commande de pizzas.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderServlet doPost method called."); // Message de débogage

        try {
            List<Pizza> pizzas = new ArrayList<>();
            for (int i = 0; ; i++) {
                // Récupérer les paramètres de la requête pour chaque pizza
                String size = request.getParameter("pizza" + i + "Size");
                if (size == null) break; // Si aucune taille n'est trouvée, sortir de la boucle

                String crust = request.getParameter("pizza" + i + "Crust");
                String base = request.getParameter("pizza" + i + "Base");

                // Récupérer les ingrédients de la pizza
                List<Ingredient> ingredients = new ArrayList<>();
                for (int j = 0; ; j++) {
                    String ingredient = request.getParameter("pizza" + i + "Ingredient" + j);
                    if (ingredient == null) break; // Si aucun ingrédient n'est trouvé, sortir de la boucle
                    ingredients.add(new Ingredient(ingredient, "0")); // Remplacer "0" par le prix réel si nécessaire
                }

                // Ajouter la pizza à la liste des pizzas
                pizzas.add(new Pizza(size, crust, base, ingredients));
            }

            // Générer le prochain identifiant de commande
            int orderId = Database.getNextOrderId();
            Order order = new Order(orderId, pizzas);

            // Sauvegarder la commande dans la base de données
            Database.saveOrder(order);

            // Répondre au client avec un message de confirmation
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Commande confirmée et enregistrée avec succès.");
            System.out.println("Order successfully saved."); // Message de confirmation

        } catch (Exception e) {
            // Gérer les exceptions
            System.err.println("Error in OrderServlet: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Erreur lors de l'enregistrement de la commande", e);
        }
    }
}
