package com.pizzaapp.models;

import java.util.List;

/**
 * Classe représentant une pizza.
 */
public class Pizza {
    // Attributs pour la taille, la croûte, la sauce et la liste des ingrédients de la pizza
    private String size;
    private String crust;
    private String sauce;
    private List<Ingredient> ingredients;

    /**
     * Constructeur pour initialiser les attributs size, crust, sauce et ingredients.
     *
     * @param size        la taille de la pizza.
     * @param crust       la croûte de la pizza.
     * @param sauce       la sauce de la pizza.
     * @param ingredients la liste des ingrédients de la pizza.
     */
    public Pizza(String size, String crust, String sauce, List<Ingredient> ingredients) {
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;
        this.ingredients = ingredients;
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
     * Getter pour l'attribut crust.
     *
     * @return la croûte de la pizza.
     */
    public String getCrust() {
        return crust;
    }

    /**
     * Getter pour l'attribut sauce.
     *
     * @return la sauce de la pizza.
     */
    public String getSauce() {
        return sauce;
    }

    /**
     * Getter pour l'attribut ingredients.
     *
     * @return la liste des ingrédients de la pizza.
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
