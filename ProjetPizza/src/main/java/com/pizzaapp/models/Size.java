package com.pizzaapp.models;

/**
 * Classe représentant les tailles pour les pizzas.
 */
public class Size {
    // Attributs pour le nom, la taille et le prix de la pizza
    private String name;
    private String size;
    private String price;

    /**
     * Constructeur pour initialiser les attributs name, size et price.
     *
     * @param name  le nom de la taille.
     * @param size  la taille de la pizza.
     * @param price le prix de la taille.
     */
    public Size(String name, String size, String price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    /**
     * Getter pour l'attribut name.
     *
     * @return le nom de la taille.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pour l'attribut name.
     *
     * @param name le nouveau nom de la taille.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pour l'attribut size.
     *
     * @return la taille de la pizza.
     */
    public String getSize() {
        return size;
    }

    /**
     * Setter pour l'attribut size.
     *
     * @param size la nouvelle taille de la pizza.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Getter pour l'attribut price.
     *
     * @return le prix de la taille.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter pour l'attribut price.
     *
     * @param price le nouveau prix de la taille.
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
