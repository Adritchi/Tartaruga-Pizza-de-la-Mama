package com.pizzaapp.models;

/**
 * Classe représentant les ingrédients pour les pizzas.
 */
public class Ingredient {
    // Attributs pour le nom et le prix de l'ingrédient
    private String name;
    private String price;

    /**
     * Constructeur pour initialiser les attributs name et price.
     *
     * @param name  le nom de l'ingrédient.
     * @param price le prix de l'ingrédient.
     */
    public Ingredient(String name, String price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Getter pour l'attribut name.
     *
     * @return le nom de l'ingrédient.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pour l'attribut name.
     *
     * @param name le nouveau nom de l'ingrédient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pour l'attribut price.
     *
     * @return le prix de l'ingrédient.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter pour l'attribut price.
     *
     * @param price le nouveau prix de l'ingrédient.
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
