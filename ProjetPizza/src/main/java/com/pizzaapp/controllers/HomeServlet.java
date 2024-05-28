package com.pizzaapp.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour gérer les requêtes vers la page d'accueil.
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    /**
     * Gère les requêtes GET en transférant la requête à la page JSP home.jsp.
     *
     * @param request  la requête HttpServletRequest.
     * @param response la réponse HttpServletResponse.
     * @throws ServletException si une erreur de traitement survient.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Transférer la requête à la page JSP home.jsp
        request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
    }
}
