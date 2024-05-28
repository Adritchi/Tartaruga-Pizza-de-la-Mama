package com.pizzaapp.models;

// Classe représentant les sauces pour les pizzas
public class Sauce {
    // Attributs pour le nom et le prix de la sauce
    private String name;
    private String price;

    // Constructeur pour initialiser les attributs name et price
    public Sauce(String name, String price) {
        this.name = name;
        this.price = price;
    }

    // Getter pour l'attribut name
    public String getName() {
        return name;
    }

    // Setter pour l'attribut name
    public void setName(String name) {
        this.name = name;
    }

    // Getter pour l'attribut price
    public String getPrice() {
        return price;
    }

    // Setter pour l'attribut price
    public void setPrice(String price) {
        this.price = price;
    }
}
