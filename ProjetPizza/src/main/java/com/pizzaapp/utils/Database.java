package com.pizzaapp.utils;

import com.pizzaapp.models.Ingredient;
import com.pizzaapp.models.Order;
import com.pizzaapp.models.Pizza;
import com.pizzaapp.models.User;
import org.w3c.dom.*;

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
    private static final String DATA_DIR = "ProjetPizza/src/main/webapp/data";
    private static final String ORDER_FILE_PATH = DATA_DIR + "/orders.xml";
    private static final String USER_FILE_PATH = DATA_DIR + "/users.xml";
    private static final String SIZE_FILE_PATH = DATA_DIR + "/sizes.xml";
    private static final String BASE_FILE_PATH = DATA_DIR + "/bases.xml";
    private static final String CRUST_FILE_PATH = DATA_DIR + "/crusts.xml";
    private static final String INGREDIENT_FILE_PATH = DATA_DIR + "/ingredients.xml";

    // Save an order to the XML file
    public static void saveOrder(Order order) throws Exception {
        Document doc = getDocument(ORDER_FILE_PATH);
        Element root = doc.getDocumentElement();
        
        Element orderElement = doc.createElement("order");
        orderElement.setAttribute("id", String.valueOf(order.getId()));

        Element pizzaElement = doc.createElement("pizza");
        pizzaElement.setAttribute("size", order.getPizza().getSize());
        pizzaElement.setAttribute("crust", order.getPizza().getCrust());
        pizzaElement.setAttribute("sauce", order.getPizza().getSauce());
        
        for (Ingredient ingredient : order.getPizza().getIngredients()) {
            Element ingredientElement = doc.createElement("ingredient");
            ingredientElement.setTextContent(ingredient.getName());
            pizzaElement.appendChild(ingredientElement);
        }

        orderElement.appendChild(pizzaElement);
        root.appendChild(orderElement);
        
        saveDocument(doc, ORDER_FILE_PATH);
    }

    // Retrieve all orders from the XML file
    public static List<Order> getOrders() throws Exception {
        Document doc = getDocument(ORDER_FILE_PATH);
        NodeList orderNodes = doc.getElementsByTagName("order");
        List<Order> orders = new ArrayList<>();
        
        for (int i = 0; i < orderNodes.getLength(); i++) {
            Node orderNode = orderNodes.item(i);
            if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
                Element orderElement = (Element) orderNode;
                int id = Integer.parseInt(orderElement.getAttribute("id"));

                Element pizzaElement = (Element) orderElement.getElementsByTagName("pizza").item(0);
                String size = pizzaElement.getAttribute("size");
                String crust = pizzaElement.getAttribute("crust");
                String sauce = pizzaElement.getAttribute("sauce");

                NodeList ingredientNodes = pizzaElement.getElementsByTagName("ingredient");
                List<Ingredient> ingredients = new ArrayList<>();
                for (int j = 0; j < ingredientNodes.getLength(); j++) {
                    Node ingredientNode = ingredientNodes.item(j);
                    if (ingredientNode.getNodeType() == Node.ELEMENT_NODE) {
                        ingredients.add(new Ingredient(ingredientNode.getTextContent()));
                    }
                }
                Pizza pizza = new Pizza(size, crust, sauce, ingredients);
                orders.add(new Order(id, pizza));
            }
        }
        return orders;
    }

    // Save a user to the XML file
    public static void saveUser(User user) throws Exception {
        Document doc = getDocument(USER_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element userElement = doc.createElement("user");
        userElement.setAttribute("id", String.valueOf(user.getId()));
        userElement.setAttribute("name", user.getName());
        userElement.setAttribute("address", user.getAddress());
        userElement.setAttribute("phone", user.getPhone());

        root.appendChild(userElement);

        saveDocument(doc, USER_FILE_PATH);
    }

    // Retrieve all users from the XML file
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

                users.add(new User(id, name, address, phone));
            }
        }
        return users;
    }

    // Save an ingredient to the XML file
    public static void saveIngredient(Ingredient ingredient) throws Exception {
        Document doc = getDocument(INGREDIENT_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element ingredientElement = doc.createElement("ingredient");
        ingredientElement.setTextContent(ingredient.getName());
        root.appendChild(ingredientElement);

        saveDocument(doc, INGREDIENT_FILE_PATH);
    }

    // Retrieve all ingredients from the XML file
    public static List<Ingredient> getIngredients() throws Exception {
        Document doc = getDocument(INGREDIENT_FILE_PATH);
        NodeList ingredientNodes = doc.getElementsByTagName("ingredient");
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientNodes.getLength(); i++) {
            Node ingredientNode = ingredientNodes.item(i);
            if (ingredientNode.getNodeType() == Node.ELEMENT_NODE) {
                ingredients.add(new Ingredient(ingredientNode.getTextContent()));
            }
        }
        return ingredients;
    }

    // Save a size to the XML file
    public static void saveSize(String size) throws Exception {
        Document doc = getDocument(SIZE_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element sizeElement = doc.createElement("size");
        sizeElement.setTextContent(size);
        root.appendChild(sizeElement);

        saveDocument(doc, SIZE_FILE_PATH);
    }

    public static List<String> getSizes() throws Exception {
        Document doc = getDocument(SIZE_FILE_PATH);
        NodeList sizeNodes = doc.getElementsByTagName("size");
        List<String> sizes = new ArrayList<>();

        for (int i = 0; i < sizeNodes.getLength(); i++) {
            Node sizeNode = sizeNodes.item(i);
            if (sizeNode.getNodeType() == Node.ELEMENT_NODE) {
                sizes.add(sizeNode.getTextContent());
            }
        }
        return sizes;
    }


    // Save a base to the XML file
    public static void saveBase(String base) throws Exception {
        Document doc = getDocument(BASE_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element baseElement = doc.createElement("base");
        baseElement.setTextContent(base);
        root.appendChild(baseElement);

        saveDocument(doc, BASE_FILE_PATH);
    }

    // Retrieve all bases from the XML file
    public static List<String> getBases() throws Exception {
        Document doc = getDocument(BASE_FILE_PATH);
        NodeList baseNodes = doc.getElementsByTagName("base");
        List<String> bases = new ArrayList<>();

        for (int i = 0; i < baseNodes.getLength(); i++) {
            Node baseNode = baseNodes.item(i);
            if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
                bases.add(baseNode.getTextContent());
            }
        }
        return bases;
    }

    // Save a crust to the XML file
    public static void saveCrust(String crust) throws Exception {
        Document doc = getDocument(CRUST_FILE_PATH);
        Element root = doc.getDocumentElement();

        Element crustElement = doc.createElement("crust");
        crustElement.setTextContent(crust);
        root.appendChild(crustElement);

        saveDocument(doc, CRUST_FILE_PATH);
    }

    // Retrieve all crusts from the XML file
    public static List<String> getCrusts() throws Exception {
        Document doc = getDocument(CRUST_FILE_PATH);
        NodeList crustNodes = doc.getElementsByTagName("crust");
        List<String> crusts = new ArrayList<>();

        for (int i = 0; i < crustNodes.getLength(); i++) {
            Node crustNode = crustNodes.item(i);
            if (crustNode.getNodeType() == Node.ELEMENT_NODE) {
                crusts.add(crustNode.getTextContent());
            }
        }
        return crusts;
    }

    // Get the document from the XML file, or create a new one if it doesn't exist
    private static Document getDocument(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            createNewDocument(filePath);
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    // Create a new document with a root element
    private static void createNewDocument(String filePath) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        Element rootElement = doc.createElement(filePath.equals(ORDER_FILE_PATH) ? "orders" : 
                                                filePath.equals(USER_FILE_PATH) ? "users" :
                                                filePath.equals(SIZE_FILE_PATH) ? "sizes" :
                                                filePath.equals(BASE_FILE_PATH) ? "bases" :
                                                filePath.equals(CRUST_FILE_PATH) ? "crusts" : "ingredients");
        doc.appendChild(rootElement);
        saveDocument(doc, filePath);
    }

    // Save the document to the XML file
    private static void saveDocument(Document doc, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
