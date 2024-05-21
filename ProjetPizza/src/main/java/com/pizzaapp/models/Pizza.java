package com.pizzaapp.models;

import java.util.List;

public class Pizza {
    private String size;
    private String crust;
    private String sauce;
    private List<Ingredient> ingredients;

    public Pizza(String size, String crust, String sauce, List<Ingredient> ingredients) {
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;
        this.ingredients = ingredients;
    }

    public String getSize() {
        return size;
    }

    public String getCrust() {
        return crust;
    }

    public String getSauce() {
        return sauce;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
