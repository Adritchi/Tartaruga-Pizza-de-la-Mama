package com.pizzaapp.models;

/**
 * Classe représentant les croûtes pour les pizzas.
 */
public class Crust {
    // Attributs pour le nom et le prix de la croûte
    private String name;
    private String price;

    /**
     * Constructeur pour initialiser les attributs name et price.
     *
     * @param name  le nom de la croûte.
     * @param price le prix de la croûte.
     */
    public Crust(String name, String price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Getter pour l'attribut name.
     *
     * @return le nom de la croûte.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pour l'attribut name.
     *
     * @param name le nouveau nom de la croûte.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pour l'attribut price.
     *
     * @return le prix de la croûte.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter pour l'attribut price.
     *
     * @param price le nouveau prix de la croûte.
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
