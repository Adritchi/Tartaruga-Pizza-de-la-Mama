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

public class Database {
    private static ServletContext context;

    private static String ORDER_FILE_PATH;
    private static String USER_FILE_PATH;
    private static String SIZE_FILE_PATH;
    private static String BASE_FILE_PATH;
    private static String CRUST_FILE_PATH;
    private static String INGREDIENT_FILE_PATH;

    // Méthode pour initialiser les chemins des fichiers XML
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

    private static String getRealPath(String relativePath) {
        String fullPath = context.getRealPath(relativePath);
        File file = new File(fullPath);
        file.getParentFile().mkdirs(); // Crée les répertoires parents si nécessaire
        return fullPath;
    }

    // Sauvegarder une commande dans le fichier XML
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

    // Ajouter des pizzas à un élément commande
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

    // Récupérer toutes les commandes à partir du fichier XML
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

    // Sauvegarder un utilisateur dans le fichier XML
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

    // Récupérer tous les utilisateurs à partir du fichier XML
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

    // Obtenir le prochain ID utilisateur
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

    // Sauvegarder un ingrédient dans le fichier XML
    public static void saveIngredient(Ingredient ingredient) throws Exception {
        Document doc = getDocument(INGREDIENT_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element ingredientElement = doc.createElement("ingredient");
        ingredientElement.setTextContent(ingredient.getName());
        root.appendChild(ingredientElement);

        saveDocument(doc, INGREDIENT_FILE_PATH);
        System.out.println("Ingredient saved to " + INGREDIENT_FILE_PATH);
    }

    // Récupérer tous les ingrédients à partir du fichier XML
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

    // Sauvegarder une taille dans le fichier XML
    public static void saveSize(String size) throws Exception {
        Document doc = getDocument(SIZE_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element sizeElement = doc.createElement("size");
        sizeElement.setTextContent(size);
        root.appendChild(sizeElement);

        saveDocument(doc, SIZE_FILE_PATH);
        System.out.println("Size saved to " + SIZE_FILE_PATH);
    }

    // Récupérer toutes les tailles à partir du fichier XML
    public static List<Size> getSizes() throws Exception {
        Document doc = getDocument(SIZE_FILE_PATH);
        NodeList sizeNodes = doc.getElementsByTagName("size");
        List<Size> sizes = new ArrayList<>();

        for (int i = 0; i < sizeNodes.getLength(); i++) {
            Node sizetNode = sizeNodes.item(i);
            if (sizetNode.getNodeType() == Node.ELEMENT_NODE) {
                Element sizeElement = (Element) sizetNode;
                String name = sizeElement.getAttribute("name");
                String size = sizeElement.getAttribute("size");
                String price = sizeElement.getAttribute("price");
                sizes.add(new Size(name, size, price));
            }
        }
        return sizes;
    }

    // Sauvegarder une base dans le fichier XML
    public static void saveBase(String base) throws Exception {
        Document doc = getDocument(BASE_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element baseElement = doc.createElement("base");
        baseElement.setTextContent(base);
        root.appendChild(baseElement);

        saveDocument(doc, BASE_FILE_PATH);
        System.out.println("Base saved to " + BASE_FILE_PATH);
    }

    // Récupérer toutes les bases à partir du fichier XML
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

    // Sauvegarder une croûte dans le fichier XML
    public static void saveCrust(String crust) throws Exception {
        Document doc = getDocument(CRUST_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element crustElement = doc.createElement("crust");
        crustElement.setTextContent(crust);
        root.appendChild(crustElement);

        saveDocument(doc, CRUST_FILE_PATH);
        System.out.println("Crust saved to " + CRUST_FILE_PATH);
    }

    // Récupérer toutes les croûtes à partir du fichier XML
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

    // Obtenir le document XML depuis le fichier ou en créer un nouveau s'il n'existe pas
    private static Document getDocument(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            createNewDocument(filePath);
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    // Créer un nouveau document avec un élément racine
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

    // Sauvegarder le document dans le fichier XML
    private static void saveDocument(Document doc, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    // Obtenir le prochain ID de commande
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
