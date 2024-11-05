<%-- 
    Document   : index
    Created on : 5 nov 2024, 0:55:10
    Author     : JoseH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            response.sendRedirect("PostControlador?accion=mostrarPagPrincipal");
        %>
    </body>
</html>
