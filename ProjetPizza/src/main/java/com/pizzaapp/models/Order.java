package com.pizzaapp.models;

import java.util.List;

public class Order {
    private int id;
    private List<Pizza> pizzas;

    public Order(int id, List<Pizza> pizzas) {
        this.id = id;
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
}
