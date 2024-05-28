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

/**
 * Servlet pour gérer les opérations sur les ingrédients. Non utile au niveau d'avancement actuel du projet.
 */
@WebServlet("/ingredients")
public class IngredientServlet extends HttpServlet {

    /**
     * Gère les requêtes GET en récupérant la liste des ingrédients depuis la base de données et en transférant la requête à la page JSP ingredients.jsp.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer la liste des ingrédients depuis la base de données
            List<Ingredient> ingredients = Database.getIngredients();
            // Ajouter la liste des ingrédients en tant qu'attribut à la requête
            request.setAttribute("ingredients", ingredients);
        } catch (Exception e) {
            // En cas d'erreur, lancer une exception ServletException avec un message d'erreur
            throw new ServletException("Erreur lors du chargement des ingrédients", e);
        }
        // Transférer la requête à la page JSP ingredients.jsp
        request.getRequestDispatcher("jsp/ingredients.jsp").forward(request, response);
    }

    /**
     * Gère les requêtes POST en ajoutant un nouvel ingrédient à la base de données.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire pour le nom et le prix de l'ingrédient
        String ingredientName = request.getParameter("name");
        String ingredientPrice = request.getParameter("price");

        // Vérifier que les paramètres ne sont pas null ou vides
        if (ingredientName != null && !ingredientName.trim().isEmpty() && ingredientPrice != null && !ingredientPrice.trim().isEmpty()) {
            // Créer un nouvel objet Ingredient avec les paramètres récupérés
            Ingredient ingredient = new Ingredient(ingredientName, ingredientPrice);
            try {
                // Sauvegarder le nouvel ingrédient dans la base de données
                Database.saveIngredient(ingredient);
            } catch (Exception e) {
                // En cas d'erreur, lancer une exception ServletException avec un message d'erreur
                throw new ServletException("Erreur lors de l'enregistrement de l'ingrédient", e);
            }
        }
        // Rediriger vers la page des ingrédients pour afficher la liste mise à jour
        response.sendRedirect("ingredients");
    }
}
