<%
    // Invalider la session pour d�connecter l'utilisateur
    session.invalidate();
    // Rediriger l'utilisateur vers la page d'accueil
    response.sendRedirect("home.jsp");
%>
