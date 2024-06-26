package com.pizzaapp.utils;

import com.pizzaapp.models.Ingredient;
import com.pizzaapp.models.Bases;
import com.pizzaapp.models.Crust;
import com.pizzaapp.models.Size;
import com.pizzaapp.models.Order;
import com.pizzaapp.models.Pizza;
import com.pizzaapp.models.User;
import org.w3c.dom.*;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour les opérations de base de données utilisant des fichiers XML.
 */
public class Database {
    private static ServletContext context;

    private static String ORDER_FILE_PATH;
    private static String USER_FILE_PATH;
    private static String SIZE_FILE_PATH;
    private static String BASE_FILE_PATH;
    private static String CRUST_FILE_PATH;
    private static String INGREDIENT_FILE_PATH;

    /**
     * Initialise les chemins des fichiers XML.
     *
     * @param servletContext le contexte de servlet pour obtenir les chemins réels des fichiers.
     */
    public static void initialize(ServletContext servletContext) {
        context = servletContext;
        ORDER_FILE_PATH = context.getRealPath("/data/orders.xml");
        USER_FILE_PATH = context.getRealPath("/data/users.xml");
        SIZE_FILE_PATH = context.getRealPath("/data/sizes.xml");
        BASE_FILE_PATH = context.getRealPath("/data/bases.xml");
        CRUST_FILE_PATH = context.getRealPath("/data/crusts.xml");
        INGREDIENT_FILE_PATH = context.getRealPath("/data/ingredients.xml");

        System.out.println("ORDER_FILE_PATH: " + ORDER_FILE_PATH);
        System.out.println("USER_FILE_PATH: " + USER_FILE_PATH);
        System.out.println("SIZE_FILE_PATH: " + SIZE_FILE_PATH);
        System.out.println("BASE_FILE_PATH: " + BASE_FILE_PATH);
        System.out.println("CRUST_FILE_PATH: " + CRUST_FILE_PATH);
        System.out.println("INGREDIENT_FILE_PATH: " + INGREDIENT_FILE_PATH);
    }

    /**
     * Retourne le chemin réel pour un chemin relatif donné, en créant les répertoires parents si nécessaire.
     *
     * @param relativePath le chemin relatif à convertir en chemin réel.
     * @return le chemin réel correspondant.
     */
    private static String getRealPath(String relativePath) {
        String fullPath = context.getRealPath(relativePath);
        File file = new File(fullPath);
        file.getParentFile().mkdirs(); // Crée les répertoires parents si nécessaire
        return fullPath;
    }

    /**
     * Sauvegarde une commande dans le fichier XML.
     *
     * @param order la commande à sauvegarder.
     * @throws Exception si une erreur survient lors de la sauvegarde.
     */
    public static synchronized void saveOrder(Order order) throws Exception {
        Document doc = getDocument(ORDER_FILE_PATH);
        Element root = doc.getDocumentElement();

        int orderId = order.getId();
        Element orderElement = doc.createElement("order");
        orderElement.setAttribute("id", String.valueOf(orderId));

        for (Pizza pizza : order.getPizzas()) {
            Element pizzaElement = doc.createElement("pizza");
            pizzaElement.setAttribute("size", pizza.getSize());
            pizzaElement.setAttribute("crust", pizza.getCrust());
            pizzaElement.setAttribute("sauce", pizza.getSauce());

            for (Ingredient ingredient : pizza.getIngredients()) {
                Element ingredientElement = doc.createElement("ingredient");
                ingredientElement.setTextContent(ingredient.getName());
                pizzaElement.appendChild(ingredientElement);
            }

            orderElement.appendChild(pizzaElement);
        }

        root.appendChild(orderElement);

        saveDocument(doc, ORDER_FILE_PATH);
        System.out.println("Order saved to " + ORDER_FILE_PATH);
    }

    /**
     * Ajoute des pizzas à un élément de commande dans le document XML.
     *
     * @param doc le document XML.
     * @param orderElement l'élément de commande auquel ajouter les pizzas.
     * @param pizzas la liste des pizzas à ajouter.
     */
    private static void addPizzasToOrderElement(Document doc, Element orderElement, List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            Element pizzaElement = doc.createElement("pizza");
            pizzaElement.setAttribute("size", pizza.getSize());
            pizzaElement.setAttribute("crust", pizza.getCrust());
            pizzaElement.setAttribute("sauce", pizza.getSauce());

            for (Ingredient ingredient : pizza.getIngredients()) {
                Element ingredientElement = doc.createElement("ingredient");
                ingredientElement.setTextContent(ingredient.getName());
                pizzaElement.appendChild(ingredientElement);
            }

            orderElement.appendChild(pizzaElement);
        }
    }

    /**
     * Récupère toutes les commandes à partir du fichier XML.
     *
     * @return une liste de commandes.
     * @throws Exception si une erreur survient lors de la récupération des commandes.
     */
    public static List<Order> getOrders() throws Exception {
        Document doc = getDocument(ORDER_FILE_PATH);
        NodeList orderNodes = doc.getElementsByTagName("order");
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < orderNodes.getLength(); i++) {
            Node orderNode = orderNodes.item(i);
            if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
                Element orderElement = (Element) orderNode;
                int id = Integer.parseInt(orderElement.getAttribute("id"));

                NodeList pizzaNodes = orderElement.getElementsByTagName("pizza");
                List<Pizza> pizzas = new ArrayList<>();
                
                for (int j = 0; j < pizzaNodes.getLength(); j++) {
                    Element pizzaElement = (Element) pizzaNodes.item(j);
                    String size = pizzaElement.getAttribute("size");
                    String crust = pizzaElement.getAttribute("crust");
                    String sauce = pizzaElement.getAttribute("sauce");

                    NodeList ingredientNodes = pizzaElement.getElementsByTagName("ingredient");
                    List<Ingredient> ingredients = new ArrayList<>();
                    for (int k = 0; k < ingredientNodes.getLength(); k++) {
                        Node ingredientNode = ingredientNodes.item(k);
                        if (ingredientNode.getNodeType() == Node.ELEMENT_NODE) {
                            ingredients.add(new Ingredient(ingredientNode.getTextContent(), "0")); // Remplacer "0" par le prix réel si nécessaire
                        }
                    }
                    Pizza pizza = new Pizza(size, crust, sauce, ingredients);
                    pizzas.add(pizza);
                }
                orders.add(new Order(id, pizzas));
            }
        }
        return orders;
    }

    /**
     * Sauvegarde un utilisateur dans le fichier XML.
     *
     * @param user l'utilisateur à sauvegarder.
     * @throws Exception si une erreur survient lors de la sauvegarde.
     */
    public static void saveUser(User user) throws Exception {
        Document doc = getDocument(USER_FILE_PATH);
        Element root = doc.getDocumentElement();

        NodeList userNodes = doc.getElementsByTagName("user");
        boolean userExists = false;

        for (int i = 0; i < userNodes.getLength(); i++) {
            Node userNode = userNodes.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                if (Integer.parseInt(userElement.getAttribute("id")) == user.getId()) {
                    userElement.setAttribute("name", user.getName());
                    userElement.setAttribute("address", user.getAddress());
                    userElement.setAttribute("phone", user.getPhone());
                    userElement.setAttribute("password", user.getPassword());
                    userExists = true;
                    break;
                }
            }
        }

        if (!userExists) {
            Element userElement = doc.createElement("user");
            userElement.setAttribute("id", String.valueOf(user.getId()));
            userElement.setAttribute("name", user.getName());
            userElement.setAttribute("address", user.getAddress());
            userElement.setAttribute("phone", user.getPhone());
            userElement.setAttribute("password", user.getPassword());
            root.appendChild(userElement);
        }

        saveDocument(doc, USER_FILE_PATH);
        System.out.println("User saved to " + USER_FILE_PATH);
    }

    /**
     * Récupère tous les utilisateurs à partir du fichier XML.
     *
     * @return une liste d'utilisateurs.
     * @throws Exception si une erreur survient lors de la récupération des utilisateurs.
     */
    public static List<User> getUsers() throws Exception {
        Document doc = getDocument(USER_FILE_PATH);
        NodeList userNodes = doc.getElementsByTagName("user");
        List<User> users = new ArrayList<>();

        for (int i = 0; i < userNodes.getLength(); i++) {
            Node userNode = userNodes.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                int id = Integer.parseInt(userElement.getAttribute("id"));
                String name = userElement.getAttribute("name");
                String address = userElement.getAttribute("address");
                String phone = userElement.getAttribute("phone");
                String password = userElement.getAttribute("password");
                users.add(new User(id, name, address, phone, password));
            }
        }
        return users;
    }

    /**
     * Obtient le prochain ID utilisateur.
     *
     * @return le prochain ID utilisateur.
     * @throws Exception si une erreur survient lors de la récupération des utilisateurs.
     */
    public static int getNextUserId() throws Exception {
        List<User> users = getUsers();
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Sauvegarde un ingrédient dans le fichier XML.
     *
     * @param ingredient l'ingrédient à sauvegarder.
     * @throws Exception si une erreur survient lors de la sauvegarde.
     */
    public static void saveIngredient(Ingredient ingredient) throws Exception {
        Document doc = getDocument(INGREDIENT_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element ingredientElement = doc.createElement("ingredient");
        ingredientElement.setTextContent(ingredient.getName());
        root.appendChild(ingredientElement);

        saveDocument(doc, INGREDIENT_FILE_PATH);
        System.out.println("Ingredient saved to " + INGREDIENT_FILE_PATH);
    }

    /**
     * Récupère tous les ingrédients à partir du fichier XML.
     *
     * @return une liste d'ingrédients.
     * @throws Exception si une erreur survient lors de la récupération des ingrédients.
     */
    public static List<Ingredient> getIngredients() throws Exception {
        Document doc = getDocument(INGREDIENT_FILE_PATH);
        NodeList ingredientNodes = doc.getElementsByTagName("ingredient");
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientNodes.getLength(); i++) {
            Node ingredientNode = ingredientNodes.item(i);
            if (ingredientNode.getNodeType() == Node.ELEMENT_NODE) {
                Element ingredientElement = (Element) ingredientNode;
                String name = ingredientElement.getAttribute("name");
                String price = ingredientElement.getAttribute("price");
                ingredients.add(new Ingredient(name, price));
            }
        }
        return ingredients;
    }

    /**
     * Sauvegarde une taille dans le fichier XML.
     *
     * @param size la taille à sauvegarder.
     * @throws Exception si une erreur survient lors de la sauvegarde.
     */
    public static void saveSize(String size) throws Exception {
        Document doc = getDocument(SIZE_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element sizeElement = doc.createElement("size");
        sizeElement.setTextContent(size);
        root.appendChild(sizeElement);

        saveDocument(doc, SIZE_FILE_PATH);
        System.out.println("Size saved to " + SIZE_FILE_PATH);
    }

    /**
     * Récupère toutes les tailles à partir du fichier XML.
     *
     * @return une liste de tailles.
     * @throws Exception si une erreur survient lors de la récupération des tailles.
     */
    public static List<Size> getSizes() throws Exception {
        Document doc = getDocument(SIZE_FILE_PATH);
        NodeList sizeNodes = doc.getElementsByTagName("size");
        List<Size> sizes = new ArrayList<>();

        for (int i = 0; i < sizeNodes.getLength(); i++) {
            Node sizeNode = sizeNodes.item(i);
            if (sizeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element sizeElement = (Element) sizeNode;
                String name = sizeElement.getAttribute("name");
                String size = sizeElement.getAttribute("size");
                String price = sizeElement.getAttribute("price");
                sizes.add(new Size(name, size, price));
            }
        }
        return sizes;
    }

    /**
     * Sauvegarde une base dans le fichier XML.
     *
     * @param base la base à sauvegarder.
     * @throws Exception si une erreur survient lors de la sauvegarde.
     */
    public static void saveBase(String base) throws Exception {
        Document doc = getDocument(BASE_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element baseElement = doc.createElement("base");
        baseElement.setTextContent(base);
        root.appendChild(baseElement);

        saveDocument(doc, BASE_FILE_PATH);
        System.out.println("Base saved to " + BASE_FILE_PATH);
    }

    /**
     * Récupère toutes les bases à partir du fichier XML.
     *
     * @return une liste de bases.
     * @throws Exception si une erreur survient lors de la récupération des bases.
     */
    public static List<Bases> getBases() throws Exception {
        Document doc = getDocument(BASE_FILE_PATH);
        NodeList baseNodes = doc.getElementsByTagName("base");
        List<Bases> bases = new ArrayList<>();

        for (int i = 0; i < baseNodes.getLength(); i++) {
            Node baseNode = baseNodes.item(i);
            if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
                Element baseElement = (Element) baseNode;
                String name = baseElement.getAttribute("name");
                String price = baseElement.getAttribute("price");
                bases.add(new Bases(name, price));
            }
        }
        return bases;
    }

    /**
     * Sauvegarde une croûte dans le fichier XML.
     *
     * @param crust la croûte à sauvegarder.
     * @throws Exception si une erreur survient lors de la sauvegarde.
     */
    public static void saveCrust(String crust) throws Exception {
        Document doc = getDocument(CRUST_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element crustElement = doc.createElement("crust");
        crustElement.setTextContent(crust);
        root.appendChild(crustElement);

        saveDocument(doc, CRUST_FILE_PATH);
        System.out.println("Crust saved to " + CRUST_FILE_PATH);
    }

    /**
     * Récupère toutes les croûtes à partir du fichier XML.
     *
     * @return une liste de croûtes.
     * @throws Exception si une erreur survient lors de la récupération des croûtes.
     */
    public static List<Crust> getCrusts() throws Exception {
        Document doc = getDocument(CRUST_FILE_PATH);
        NodeList crustNodes = doc.getElementsByTagName("crust");
        List<Crust> crusts = new ArrayList<>();

        for (int i = 0; i < crustNodes.getLength(); i++) {
            Node crustNode = crustNodes.item(i);
            if (crustNode.getNodeType() == Node.ELEMENT_NODE) {
                Element crustElement = (Element) crustNode;
                String name = crustElement.getAttribute("name");
                String price = crustElement.getAttribute("price");
                crusts.add(new Crust(name, price));
            }
        }
        return crusts;
    }

    /**
     * Obtient le document XML depuis le fichier ou en crée un nouveau s'il n'existe pas.
     *
     * @param filePath le chemin du fichier XML.
     * @return le document XML.
     * @throws Exception si une erreur survient lors de la récupération ou de la création du document.
     */
    private static Document getDocument(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            createNewDocument(filePath);
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    /**
     * Crée un nouveau document avec un élément racine.
     *
     * @param filePath le chemin du fichier XML.
     * @throws ParserConfigurationException si une erreur survient lors de la configuration du parseur.
     * @throws TransformerException si une erreur survient lors de la transformation du document.
     */
    private static void createNewDocument(String filePath) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        Element rootElement = doc.createElement(filePath.endsWith("orders.xml") ? "orders" :
                                                filePath.endsWith("users.xml") ? "users" :
                                                filePath.endsWith("sizes.xml") ? "sizes" :
                                                filePath.endsWith("bases.xml") ? "bases" :
                                                filePath.endsWith("crusts.xml") ? "crusts" : "ingredients");
        doc.appendChild(rootElement);
        saveDocument(doc, filePath);
    }

    /**
     * Sauvegarde le document dans le fichier XML.
     *
     * @param doc le document XML à sauvegarder.
     * @param filePath le chemin du fichier XML.
     * @throws TransformerException si une erreur survient lors de la transformation du document.
     */
    private static void saveDocument(Document doc, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    /**
     * Obtient le prochain ID de commande.
     *
     * @return le prochain ID de commande.
     * @throws Exception si une erreur survient lors de la récupération des commandes.
     */
    public static synchronized int getNextOrderId() throws Exception {
        Document doc = getDocument(ORDER_FILE_PATH);
        NodeList orderNodes = doc.getElementsByTagName("order");
        int maxOrderId = 0;
        for (int i = 0; i < orderNodes.getLength(); i++) {
            Element orderElement = (Element) orderNodes.item(i);
            int orderId = Integer.parseInt(orderElement.getAttribute("id"));
            if (orderId > maxOrderId) {
                maxOrderId = orderId;
            }
        }
        return maxOrderId + 1;
    }
}
