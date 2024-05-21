package com.pizzaapp.models;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private List<Ingredient> ingredients;

    public Pizza() {
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public double getTotalPrice() {
        return ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }
}
