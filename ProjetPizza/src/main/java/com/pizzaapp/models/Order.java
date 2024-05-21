package com.pizzaapp.models;

public class Order {
    private int id;
    private Pizza pizza;

    public Order(int id, Pizza pizza) {
        this.id = id;
        this.pizza = pizza;
    }

    public int getId() {
        return id;
    }

    public Pizza getPizza() {
        return pizza;
    }
}
