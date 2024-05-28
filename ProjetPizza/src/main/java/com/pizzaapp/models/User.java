package com.pizzaapp.models;

// Classe représentant un utilisateur de l'application
public class User {
    // Attributs pour l'identifiant, le nom, l'adresse, le téléphone et le mot de passe de l'utilisateur
    private int id;
    private String name;
    private String address;
    private String phone;
    private String password;

    // Constructeur pour initialiser les attributs id, name, address, phone et password
    public User(int id, String name, String address, String phone, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    // Getter pour l'attribut id
    public int getId() {
        return id;
    }

    // Getter pour l'attribut name
    public String getName() {
        return name;
    }

    // Setter pour l'attribut name
    public void setName(String name) {
        this.name = name;
    }

    // Getter pour l'attribut address
    public String getAddress() {
        return address;
    }

    // Setter pour l'attribut address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter pour l'attribut phone
    public String getPhone() {
        return phone;
    }

    // Setter pour l'attribut phone
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter pour l'attribut password
    public String getPassword() {
        return password;
    }

    // Setter pour l'attribut password
    public void setPassword(String password) {
        this.password = password;
    }
}
