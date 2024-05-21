package com.pizzaapp.controllers;

import com.pizzaapp.models.Order;
import com.pizzaapp.models.Pizza;
import com.pizzaapp.models.User;
import com.pizzaapp.utils.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Order> orders = Database.getOrders();
            request.setAttribute("orders", orders);
        } catch (Exception e) {
            throw new ServletException("Error loading orders", e);
        }
        request.getRequestDispatcher("jsp/order.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Pizza pizza = (Pizza) request.getSession().getAttribute("pizza");

        if (user == null || pizza == null) {
            response.sendRedirect("home");
            return;
        }

        try {
            List<Order> orders = Database.getOrders();
            int newOrderId = orders.size() + 1;
            Order order = new Order(newOrderId, pizza);
            Database.saveOrder(order);
            request.setAttribute("order", order);
        } catch (Exception e) {
            throw new ServletException("Error saving order", e);
        }
        
        request.getRequestDispatcher("jsp/order.jsp").forward(request, response);
    }
}
