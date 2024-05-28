package com.pizzaapp.controllers;

import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour gérer l'inscription des utilisateurs.
 */
// Annotation pour indiquer que cette servlet est accessible via l'URL spécifique si besoin
//@WebServlet("/register") // (désactiver si l'URL de la servlet est configurée différemment)
public class RegisterServlet extends HttpServlet {

    /**
     * Gère les requêtes POST pour inscrire un nouvel utilisateur.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire d'inscription
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try {
            // Générer le prochain identifiant utilisateur
            int id = Database.getNextUserId();
            // Créer un nouvel objet User avec les informations récupérées
            User newUser = new User(id, name, address, phone, password);
            // Sauvegarder le nouvel utilisateur dans la base de données
            Database.saveUser(newUser);
            // Rediriger vers la page de connexion après l'inscription réussie
            response.sendRedirect("jsp/login.jsp");
        } catch (Exception e) {
            // Gérer les exceptions en lançant une ServletException avec un message d'erreur
            throw new ServletException("Erreur lors de l'enregistrement de l'utilisateur", e);
        }
    }
}
