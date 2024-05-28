package com.pizzaapp.models;

/**
 * Classe représentant un utilisateur de l'application.
 */
public class User {
    // Attributs pour l'identifiant, le nom, l'adresse, le téléphone et le mot de passe de l'utilisateur
    private int id;
    private String name;
    private String address;
    private String phone;
    private String password;

    /**
     * Constructeur pour initialiser les attributs id, name, address, phone et password.
     *
     * @param id       l'identifiant de l'utilisateur.
     * @param name     le nom de l'utilisateur.
     * @param address  l'adresse de l'utilisateur.
     * @param phone    le numéro de téléphone de l'utilisateur.
     * @param password le mot de passe de l'utilisateur.
     */
    public User(int id, String name, String address, String phone, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Getter pour l'attribut id.
     *
     * @return l'identifiant de l'utilisateur.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter pour l'attribut name.
     *
     * @return le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pour l'attribut name.
     *
     * @param name le nouveau nom de l'utilisateur.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pour l'attribut address.
     *
     * @return l'adresse de l'utilisateur.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter pour l'attribut address.
     *
     * @param address la nouvelle adresse de l'utilisateur.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter pour l'attribut phone.
     *
     * @return le numéro de téléphone de l'utilisateur.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter pour l'attribut phone.
     *
     * @param phone le nouveau numéro de téléphone de l'utilisateur.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter pour l'attribut password.
     *
     * @return le mot de passe de l'utilisateur.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter pour l'attribut password.
     *
     * @param password le nouveau mot de passe de l'utilisateur.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
