<%-- 
    Document   : publicacion
    Created on : 9 nov 2024, 9:53:08 p.m.
    Author     : victo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/publicacionStyle.css">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">
    </head>
    <body>
        <jsp:include page="fragmentos/BarraNavegacion.jsp" />

        <main>
            <section class="post">
                <h1 class="tituloPost">${post.titulo}</h1>
                <h3 class="fechaPost">${fechaFormateada}</h3>
                <img class="imgPost"
                     src="https://media.cnn.com/api/v1/images/stellar/prod/cnne-1765256-messi-argentina.jpg?c=16x9&q=h_833,w_1480,c_fill"
                     alt="Lionel Messi en acción">
                <div class="infoPost">
                    <p>${post.contenido}</p>
                </div>
            </section>

            <span class="separador"></span>

            <div class="comentarios">
                <label class="textoIconoComentario"><img class="iconoComentario"
                                                         src="${pageContext.request.contextPath}/img/material-symbols-light_comment-sharp.png">${not empty post.comentarios ? post.comentarios.size() : 0}</label>

                <c:if test="${not empty post.comentarios}">
                    <c:forEach items="${post.comentarios}" var="comentario">
                        <section>
                            <div class="comentarioPost">
                                <label class="usuario"><img class="fotoPerfil" src="${pageContext.request.contextPath}/img/iconoPerfil_rojo.png">${comentario.usuario.nombre}</label>
                                <p>${comentario.contenido}</p>
                                <div class="responderComentario">
                                    <a href="#comentario">
                                        <a href="<c:url value='/ComentarioServlet?comentarioMayorId=${comentario.id}'/>">
                                            <input type="submit" name="responder" id="responder" value="Responder">
                                        </a>
                                </div>

                            </div>
                            <c:if test="${not empty comentario.respuestas}">
                                <c:forEach items="${comentario.respuestas}" var="respuesta">
                                    <div class="comentarioHijoPost">
                                        <label class="usuario"><img class="fotoPerfil" src="../img/iconoPerfil_rojo.png">${respuesta.usuario.nombre}</label>
                                        <p>${respuesta.contenido}</p>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </section>
                    </c:forEach>
                </c:if>

                <div class="contenedorComentario">
                    <form action="ComentarioServlet" method="POST">
                        <textarea name="comentario" class="comentario" id="comentario" placeholder="Escribe tu comentario aquí" required></textarea>
                        <input type="hidden" name="postId" value="${post.id}">
                        <input type="hidden" name="usuarioId" value="${usuario.id}">
                        <input type="submit" name="enviar" id="enviar" value="Publicar">
                    </form>
                </div>
            </div>

        </main>
    </body>
</html>
