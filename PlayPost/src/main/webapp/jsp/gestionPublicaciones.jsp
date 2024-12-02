<%-- 
    Document   : gestionPublicaciones
    Created on : 24 nov 2024, 13:43:32
    Author     : JoseH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PlayPost</title>
        <link rel="stylesheet" href="<c:url value='/estilos/gestionPublicacionesStyle.css'/>">


        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">
        <title>JSP Page</title>
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
                <div class="categoria soccer-btn" data-categoria="SOCCER">
                    <img src="<c:url value='/img/soccer.png'/>" alt="Soccer">
                </div>
                <div class="categoria basquet-btn" data-categoria="BASQUET">
                    <img src="<c:url value='/img/basquet.png'/>" alt="Basquet">
                </div>
                <div class="categoria football-btn" data-categoria="FOOTBALL">
                    <img src="<c:url value='/img/football.png'/>" alt="Football">
                </div>
                <div class="categoria baseball-btn" data-categoria="BASEBALL">
                    <img src="<c:url value='/img/baseball.png'/>" alt="Baseball">
                </div>
        </div>

        <main>
            <c:forEach items="${posts}" var="item" varStatus="status">
                <section class="post">
                    <div class="contenedorPost">
                        <a href="<c:url value='/PublicacionServlet'>
                               <c:param name='id' value='${item.id}'/>
                           </c:url>">
                            <div class="contenido-principal">
                                <div class="infoPost">
                                    <h1 class="tituloPost"><c:out value="${item.titulo}"/></h1>
                                    <h3 class="fechaPost">${fechasFormateadas[status.index]}</h3>
                                    <p class="infoPost"><c:out value="${item.contenido}"/></p>
                                </div>
                                <div class="contenedorImg">
                                    <img class="imgPost"
                                         src="https://media.cnn.com/api/v1/images/stellar/prod/cnne-1765256-messi-argentina.jpg?c=16x9&q=h_833,w_1480,c_fill"
                                         alt="Lionel Messi en acción">
                                </div>
                            </div>
                            <div class="info">
                                <label id="usuario">
                                    <img id="fotoPerfil" src="<c:url value='/img/iconamoon_profile-circle-bold.png'/>">
                                    <c:out value="${item.usuario.nombreCompleto}"/>
                                </label>
                            </div>             
                        </a>
                    </div>

                    <div class="contenedorIconos">
                        <img class="iconoPost eliminar-btn" src="<c:url value= '/img/trashcan.png'/>" data-id="${item.id}" alt="Botón para eliminar el post">
                        <img class="iconoPost anclar-btn" src="<c:url value='/img/pin.png' />" data-id="${item.id}"  alt="Botón para anclar un post">
                    </div>
                </section>
            </c:forEach>
        </main>
                
                <script src="<c:url value='/js/administrarPosts.js'/>"></script>
                <script src="<c:url value='/js/filtrarPorCategoríaAdmin.js'/>"></script>
    </body>
</html>
