<%-- 
    Document   : misPublicaciones
    Created on : 6 dic 2024, 22:23:31
    Author     : JoseH
--%>

<%@ page import="java.util.Calendar, java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="<c:url value='/estilos/misPublicacionesStyle.css'/>">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet">
        <title>Mis Publicaciones</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/BarraNavegacion.jspf" %>

        <div class="contenedor">
            <button id="crearPublicacion">
                <a href="<c:url value='/PostControlador'>
                       <c:param name='accion' value='nuevo'/>
                   </c:url>">Crear publicación
                </a>
            </button>
        </div>
        
        <div class="titulo-seccion">
            <h1>Publicaciones de <span class="nombre-usuario"><c:out value="${sessionScope.usuario.nombreCompleto}"/></span></h1>
        </div>

        <div id="postContainer">
            <c:forEach items="${posts}" var="item" varStatus="status">
                <section class="entrada" data-tipo="${item.tipo}" data-fecha="${fechasFormateadas[status.index]}" data-id="${item.id}">
                    <div class="categoria-icon">
                        <c:choose>
                            <c:when test="${item.categoria == 'SOCCER'}">Fútbol</c:when>
                            <c:when test="${item.categoria == 'BASQUET'}">Básquet</c:when>
                            <c:when test="${item.categoria == 'FOOTBALL'}">Fútbol americano</c:when>
                            <c:when test="${item.categoria == 'BASEBALL'}">Béisbol</c:when>
                        </c:choose>
                    </div>
                    <h2>
                        <a href="<c:url value='/PublicacionServlet?id=${item.id}'/>">
                            <c:out value="${item.titulo}"/>
                        </a>
                    </h2>
                    <h3><c:out value="${fechasFormateadas[status.index]}"/></h3>
                    <c:if test="${not empty item.imagenData && item.imagenData != 'none'}">
                        <div class="imagen-post">
                            <img src="<c:url value='${item.imagenData}'/>" alt="Imagen del post">
                        </div>
                    </c:if>
                    <p class="contenido-breve"><c:out value="${item.contenido}"/></p>
                    <div class="info">
                        <label><img id="iconoComentario" src="<c:url value='/img/material-symbols-light_comment-sharp.png'/>"><c:out value="${item.comentarios != null ? item.comentarios.size() : 0}"/></label>
                        <label id="usuario"><img id="fotoPerfil" src="<c:url value='${not empty item.usuario.avatar ? item.usuario.avatar : "/img/default-avatar.png"}'/>"><c:out value="${item.usuario.nombreCompleto}"/></label>
                    </div>
                </section>
            </c:forEach>
        </div>

        <script src="<c:url value='/js/ordenarPosts.js'/>"></script>
    </body>
</html>
