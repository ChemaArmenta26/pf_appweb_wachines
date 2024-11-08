<%-- 
    Document   : pantallaPrincipal
    Created on : 5 nov 2024, 1:00:21
    Author     : JoseH
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/estilos/navegacionStyle.css">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">
        
        <title>Pantalla Principal</title>
    </head>
    <body>
       <!-- Incluye la navegación -->
        <jsp:include page="fragmentos/BarraNavegacion.jsp" />

    <div class="contenedor">
        <button id="crearPublicacion"><a href="PostControlador?accion=nuevo">Crear publicación</a></button>
        <a href="#categoriaFutbol">
            <div class="categoria">
                <img src="${pageContext.request.contextPath}/img/soccer.png" alt="Soccer">
            </div>
        </a>
        <a href="#categoriaBasquet">
            <div class="categoria">
                <img src="${pageContext.request.contextPath}/img/basquet.png" alt="Basquet">
            </div>
        </a>
        <a href="#categoriaFutbolAmericano">
            <div class="categoria">
                <img src="${pageContext.request.contextPath}/img/football.png" alt="Football">
            </div>
        </a>
        <a href="#categoriaBeisbol">
            <div class="categoria">
                <img src="${pageContext.request.contextPath}/img/baseball.png" alt="Baseball">
            </div>
        </a>
    </div>



    <section class="entrada">
        <h2> <a href="publicacion.html">${posts.titulo}</a></h2>
        <h3>${posts.fechaHoraCreacion}</h3>
        <img src="data:image/jpeg;base64,${posts.imageData}">
        <p class="contenido-breve">${posts.contenido}</p>
        <div class="info">
            <label><img id="iconoComentario" src="${pageContext.request.contextPath}/img/material-symbols-light_comment-sharp.png">${posts.comentarios.size()}</label>
            <label id="usuario"><img id="fotoPerfil" src="${pageContext.request.contextPath}/img/iconamoon_profile-circle-bold.png">${posts.usuario}</label>
        </div>
    </section>
            
    </body>
</html>