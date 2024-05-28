package com.pizzaapp.controllers;

import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet pour gérer les opérations du compte utilisateur.
 */
public class AccountServlet extends HttpServlet {

    /**
     * Gère les requêtes POST pour mettre à jour les informations du compte utilisateur.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Récupérer la session actuelle
        User user = (User) session.getAttribute("user"); // Récupérer l'utilisateur connecté à partir de la session

        if (user != null) { // Vérifier si l'utilisateur est connecté
            // Récupérer les paramètres du formulaire de modification du compte utilisateur
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");

            // Utiliser les setters pour mettre à jour les informations de l'utilisateur
            user.setName(name);
            user.setAddress(address);
            user.setPhone(phone);
            user.setPassword(password);

            try {
                Database.saveUser(user); // Sauvegarder les modifications de l'utilisateur dans la base de données
                // Mettre à jour l'attribut de session avec les nouvelles informations de l'utilisateur
                session.setAttribute("user", user);
                session.setAttribute("successMessage", "Modification effectuée !");
            } catch (Exception e) {
                // En cas d'erreur, lancer une exception ServletException avec un message d'erreur
                throw new ServletException("Erreur lors de l'enregistrement de l'utilisateur", e);
            }
        }

        // Rediriger vers la page de compte après la modification
        response.sendRedirect(request.getContextPath() + "/jsp/account.jsp");
    }

    /**
     * Gère les requêtes GET pour afficher la page de compte utilisateur.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Transférer la requête à la page JSP du compte utilisateur
        request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
    }
}
