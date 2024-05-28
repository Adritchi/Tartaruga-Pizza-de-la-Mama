package com.pizzaapp.models;

import java.util.List;

// Classe représentant une commande de pizzas
public class Order {
    // Attributs pour l'identifiant de la commande et la liste de pizzas
    private int id;
    private List<Pizza> pizzas;

    // Constructeur pour initialiser les attributs id et pizzas
    public Order(int id, List<Pizza> pizzas) {
        this.id = id;
        this.pizzas = pizzas;
    }

    // Getter pour l'attribut id
    public int getId() {
        return id;
    }

    // Getter pour l'attribut pizzas - une commande peut contenir plusieurs pizzas
    public List<Pizza> getPizzas() {
        return pizzas;
    }
}
