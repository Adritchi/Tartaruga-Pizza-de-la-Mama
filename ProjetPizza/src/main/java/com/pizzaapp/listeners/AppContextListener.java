package com.pizzaapp.listeners;

import com.pizzaapp.utils.Database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Écouteur de contexte d'application pour initialiser et nettoyer les ressources de la base de données.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    /**
     * Méthode appelée lors de l'initialisation du contexte de servlet.
     *
     * @param sce l'événement de contexte de servlet.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialiser la base de données avec le contexte de servlet
        Database.initialize(sce.getServletContext());
    }

    /**
     * Méthode appelée lors de la destruction du contexte de servlet.
     *
     * @param sce l'événement de contexte de servlet.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Code de nettoyage si nécessaire
    }
}
