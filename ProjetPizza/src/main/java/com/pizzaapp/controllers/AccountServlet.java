package com.pizzaapp.controllers;

import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("jsp/login.jsp");
        } else {
            request.getRequestDispatcher("jsp/account.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            try {
                List<User> users = Database.getUsers();
                int newUserId = users.size() + 1;
                user = new User(newUserId, name, address, phone);
            } catch (Exception e) {
                throw new ServletException("Error retrieving users", e);
            }
        } else {
            user.setName(name);
            user.setAddress(address);
            user.setPhone(phone);
        }

        request.getSession().setAttribute("user", user);

        try {
            Database.saveUser(user);
        } catch (Exception e) {
            throw new ServletException("Error saving user", e);
        }

        request.getRequestDispatcher("jsp/account.jsp").forward(request, response);
    }
}
