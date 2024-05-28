package com.pizzaapp.models;

/**
 * Classe représentant les bases pour les pizzas.
 */
public class Bases {
    // Attributs pour le nom et le prix de la base
    private String name;
    private String price;

    /**
     * Constructeur pour initialiser les attributs name et price.
     *
     * @param name  le nom de la base.
     * @param price le prix de la base.
     */
    public Bases(String name, String price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Getter pour l'attribut name.
     *
     * @return le nom de la base.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pour l'attribut name.
     *
     * @param name le nouveau nom de la base.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pour l'attribut price.
     *
     * @return le prix de la base.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter pour l'attribut price.
     *
     * @param price le nouveau prix de la base.
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
