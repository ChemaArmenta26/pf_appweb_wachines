<%-- 
    Document   : acercaDe
    Created on : 6 dic 2024, 14:34:26
    Author     : JoseH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PlayPost - Acerca de</title>


        <link rel="stylesheet" href="<c:url value='/estilos/acercaDe.css'/>">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">      
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/BarraNavegacion.jspf" %>

        <div class="content-container">
            <p>
                Hola, somos tres estudiantes de quinto semestre de la carrera de Ingeniería en Software. 
                Este sitio web es el resultado de nuestro proyecto final para la materia Aplicaciones Web, 
                donde hemos puesto en práctica todos los conocimientos adquiridos durante el curso.
                Nuestro blog está diseñado para los amantes del deporte, un espacio donde cualquiera puede 
                crear y compartir publicaciones relacionadas con sus deportes favoritos, experiencias, noticias 
                o incluso consejos. La idea principal es fomentar una comunidad activa e interactiva, donde todos 
                puedan expresar su pasión por el deporte de manera sencilla y divertida.

                Gracias por visitar nuestra página, y no olviden  crear su propio post o dejar sus comentarios. 
                ¡El deporte nos une!
            </p>

            <h2>Creadores</h2>
            <ul>
                <li>Victor Humberto Encinas Guzmán</li>
                <li>José María Armenta Baca</li>
                <li>José Ángel Huerta Amparán</li>
            </ul>

        </div>
    </body>
</html>
