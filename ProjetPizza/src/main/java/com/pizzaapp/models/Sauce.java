package com.pizzaapp.models;

/**
 * Classe représentant les sauces pour les pizzas.
 */
public class Sauce {
    // Attributs pour le nom et le prix de la sauce
    private String name;
    private String price;

    /**
     * Constructeur pour initialiser les attributs name et price.
     *
     * @param name  le nom de la sauce.
     * @param price le prix de la sauce.
     */
    public Sauce(String name, String price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Getter pour l'attribut name.
     *
     * @return le nom de la sauce.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pour l'attribut name.
     *
     * @param name le nouveau nom de la sauce.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pour l'attribut price.
     *
     * @return le prix de la sauce.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter pour l'attribut price.
     *
     * @param price le nouveau prix de la sauce.
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
