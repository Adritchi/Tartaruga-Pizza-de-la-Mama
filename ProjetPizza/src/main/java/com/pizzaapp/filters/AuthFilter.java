package com.pizzaapp.filters;

import com.pizzaapp.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre d'authentification pour restreindre l'accès aux pages protégées.
 */
@WebFilter({"/jsp/account.jsp", "/account", "/jsp/customize.jsp", "/customize"})
public class AuthFilter implements Filter {

    /**
     * Méthode d'initialisation du filtre (peut être laissée vide si non utilisée).
     *
     * @param filterConfig la configuration du filtre.
     * @throws ServletException si une erreur de traitement survient.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Méthode d'initialisation du filtre (peut être laissée vide si non utilisée)
    }

    /**
     * Méthode principale du filtre pour traiter chaque requête/response.
     *
     * @param request  la requête ServletRequest.
     * @param response la réponse ServletResponse.
     * @param chain    la chaîne de filtres.
     * @throws IOException      si une erreur d'entrée/sortie survient.
     * @throws ServletException si une erreur de traitement survient.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Convertir les requêtes et réponses génériques en types HTTP
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // Récupérer l'utilisateur de la session
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
            res.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
        } else {
            // Si l'utilisateur est connecté, continuer avec la chaîne de filtres
            chain.doFilter(request, response);
        }
    }

    /**
     * Méthode de destruction du filtre (peut être laissée vide si non utilisée).
     */
    @Override
    public void destroy() {
        // Méthode de destruction du filtre (peut être laissée vide si non utilisée)
    }
}
