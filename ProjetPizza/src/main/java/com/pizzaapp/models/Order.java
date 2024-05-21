package com.pizzaapp.models;

public class Order {
    private User user;
    private Pizza pizza;

    public Order(User user, Pizza pizza) {
        this.user = user;
        this.pizza = pizza;
    }

    public User getUser() {
        return user;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public double getTotalPrice() {
        return pizza.getTotalPrice();
    }
}
