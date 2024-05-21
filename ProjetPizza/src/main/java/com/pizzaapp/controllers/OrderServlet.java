package com.pizzaapp.controllers;

import com.pizzaapp.models.Order;
import com.pizzaapp.models.Pizza;
import com.pizzaapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/order.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Pizza pizza = (Pizza) request.getSession().getAttribute("pizza");

        if (user == null || pizza == null) {
            response.sendRedirect("home");
            return;
        }

        Order order = new Order(user, pizza);
        request.setAttribute("order", order);
        request.getRequestDispatcher("jsp/order.jsp").forward(request, response);
    }
}
