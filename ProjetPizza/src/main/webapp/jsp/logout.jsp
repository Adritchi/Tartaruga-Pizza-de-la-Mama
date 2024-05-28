<%
    // Invalider la session pour déconnecter l'utilisateur
    session.invalidate();
    // Rediriger l'utilisateur vers la page d'accueil
    response.sendRedirect("home.jsp");
%>
