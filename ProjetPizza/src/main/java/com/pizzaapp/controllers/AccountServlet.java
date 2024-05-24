package com.pizzaapp.controllers;

import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
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
                Database.saveUser(user);
                // Mettre à jour l'attribut de session
                session.setAttribute("user", user);
                session.setAttribute("successMessage", "Modification effectuée !");
            } catch (Exception e) {
                throw new ServletException("Erreur lors de l'enregistrement de l'utilisateur", e);
            }
        }

        response.sendRedirect(request.getContextPath() + "/jsp/account.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
    }
}
