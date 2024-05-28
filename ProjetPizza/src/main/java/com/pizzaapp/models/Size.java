package com.pizzaapp.models;

// Classe représentant les tailles pour les pizzas
public class Size {
    // Attributs pour le nom, la taille et le prix de la pizza
    private String name;
    private String size;
    private String price;

    // Constructeur pour initialiser les attributs name, size et price
    public Size(String name, String size, String price) {
        this.name = name;
        this.size = size;
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

    // Getter pour l'attribut size
    public String getSize() {
        return size;
    }

    // Setter pour l'attribut size
    public void setSize(String size) {
        this.size = size;
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
