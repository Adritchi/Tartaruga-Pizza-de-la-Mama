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
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        try {
            List<User> users = Database.getUsers();
            for (User user : users) {
                if (user.getName().equals(name) && user.getPassword().equals(password)) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/jsp/account.jsp");
                    return;
                }
            }
            request.setAttribute("error", "Nom ou mot de passe incorrect");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error logging in user", e);
        }
    }
}
