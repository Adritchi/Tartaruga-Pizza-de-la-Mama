package com.pizzaapp.filters;

import com.pizzaapp.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Annotation pour indiquer que ce filtre est appliqué aux URLs spécifiées
@WebFilter({"/jsp/account.jsp", "/account", "/jsp/customize.jsp", "/customize"})
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Méthode d'initialisation du filtre (peut être laissée vide si non utilisée)
    }

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

    @Override
    public void destroy() {
        // Méthode de destruction du filtre (peut être laissée vide si non utilisée)
    }
}
