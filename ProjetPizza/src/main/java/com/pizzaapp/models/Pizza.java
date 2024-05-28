package com.pizzaapp.models;

import java.util.List;

// Classe représentant une pizza
public class Pizza {
    // Attributs pour la taille, la croûte, la sauce et la liste des ingrédients de la pizza
    private String size;
    private String crust;
    private String sauce;
    private List<Ingredient> ingredients;

    // Constructeur pour initialiser les attributs size, crust, sauce et ingredients
    public Pizza(String size, String crust, String sauce, List<Ingredient> ingredients) {
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;
        this.ingredients = ingredients;
    }

    // Getter pour l'attribut size
    public String getSize() {
        return size;
    }

    // Getter pour l'attribut crust
    public String getCrust() {
        return crust;
    }

    // Getter pour l'attribut sauce
    public String getSauce() {
        return sauce;
    }

    // Getter pour l'attribut ingredients
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
