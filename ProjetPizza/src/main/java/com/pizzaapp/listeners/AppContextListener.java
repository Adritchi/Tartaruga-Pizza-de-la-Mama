package com.pizzaapp.listeners;

import com.pizzaapp.utils.Database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// Annotation pour indiquer que cette classe est un écouteur de contexte
@WebListener
public class AppContextListener implements ServletContextListener {

    // Méthode appelée lors de l'initialisation du contexte
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialiser la base de données avec le contexte de servlet
        Database.initialize(sce.getServletContext());
    }

    // Méthode appelée lors de la destruction du contexte
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Code de nettoyage si nécessaire
    }
}
