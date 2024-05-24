package com.pizzaapp.controllers;

import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try {
            int id = Database.getNextUserId();
            User newUser = new User(id, name, address, phone, password);
            Database.saveUser(newUser);
            response.sendRedirect("jsp/login.jsp");
        } catch (Exception e) {
            throw new ServletException("Error registering user", e);
        }
    }
}
