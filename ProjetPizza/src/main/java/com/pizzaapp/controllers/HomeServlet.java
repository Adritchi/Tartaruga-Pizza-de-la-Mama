package com.pizzaapp.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Annotation pour indiquer que cette servlet est accessible via l'URL "/home"
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    // Méthode doGet pour gérer les requêtes GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Transférer la requête à la page JSP home.jsp
        request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
    }
}
