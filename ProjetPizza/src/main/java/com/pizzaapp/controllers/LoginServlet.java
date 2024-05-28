package com.pizzaapp.controllers;

import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Identifiant de version pour la sérialisation

    // Méthode doPost pour gérer les requêtes POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire de connexion
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        try {
            // Récupérer la liste des utilisateurs depuis la base de données
            List<User> users = Database.getUsers();
            for (User user : users) {
                // Vérifier si le nom et le mot de passe correspondent à un utilisateur
                if (user.getName().equals(name) && user.getPassword().equals(password)) {
                    // Si l'utilisateur est trouvé, ajouter l'utilisateur à la session et rediriger vers la page de compte
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/jsp/account.jsp");
                    return;
                }
            }
            // Si les informations de connexion sont incorrectes, ajouter un message d'erreur à la requête
            request.setAttribute("error", "Nom ou mot de passe incorrect");
            // Transférer la requête à la page de connexion avec le message d'erreur
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } catch (Exception e) {
            // En cas d'erreur, lancer une exception ServletException avec un message d'erreur
            throw new ServletException("Erreur lors de la connexion de l'utilisateur", e);
        }
    }
}
