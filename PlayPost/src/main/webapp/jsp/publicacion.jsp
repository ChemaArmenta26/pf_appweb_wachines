<%-- 
    Document   : publicacion
    Created on : 9 nov 2024, 9:53:08 p.m.
    Author     : victo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost</title>
        <link rel="stylesheet" href="<c:url value='/estilos/publicacionStyle.css'/>">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">
    </head>
    <body>
        
        <%@ include file="/WEB-INF/jspf/BarraNavegacion.jspf" %>

        <main>
            <section class="post">
                <h1 class="tituloPost"><c:out value="${post.titulo}"/></h1>
                <h3 class="fechaPost"><c:out value="${fechasFormateadas}"/></h3>
                <img class="imgPost"
                     src="https://media.cnn.com/api/v1/images/stellar/prod/cnne-1765256-messi-argentina.jpg?c=16x9&q=h_833,w_1480,c_fill"
                     alt="Lionel Messi en acción">
                <div class="infoPost">
                    <p><c:out value="${post.contenido}"/></p>
                </div>
            </section>

            <span class="separador"></span>

            <div class="comentarios">
                <label class="textoIconoComentario"><img class="iconoComentario"
                                                         src="<c:url value='/img/material-symbols-light_comment-sharp.png'/>">${fn:length(post.comentarios)}</label>

                <c:if test="${not empty post.comentarios}">
                    <c:forEach items="${post.comentarios}" var="comentario">
                        <section>
                            <div class="comentarioPost">
                                <label class="usuario"><img class="fotoPerfil" src="<c:url value='/img/iconoPerfil_rojo.png'/>">
                                    <c:out value="${comentario.usuario.nombreCompleto}"/>
                                </label>
                                <p><c:out value="${comentario.contenido}"/></p>

                                <div class="responderComentario">
                                    <button type="button" 
                                            onclick="responderComentario('${comentario.id}')" 
                                            class="btnResponder">
                                        Responder
                                    </button>
                                </div>
                                <!--  
                                <div class="formRespuestaHIjo" id="resp${comentario.id}">
                                    <form action="<c:url value='/ComentarioServlet'/>" method="POST">
                                        <textarea name="comentario" 
                                                  class="comentario-respuesta" 
                                                  placeholder="Escribe tu respuesta aquí" 
                                                  required></textarea>
                                        <input type="hidden" name="postId" value="<c:out value='${post.id}'/>">
                                        <input type="hidden" name="usuarioId" value="<c:out value='${usuario.id}'/>">
                                        <input type="hidden" name="comentarioMayorId" value="<c:out value='${comentario.id}'/>">
                                        <div class="btnsRespuesta">
                                            <button type="button" class="cancelar" onclick="responderComentario('${comentario.id}')">
                                                Cancelar
                                            </button>
                                            <input type="submit" value="Responder">
                                        </div>
                                    </form>
                                </div>
                                -->
                            </div>
                            <c:if test="${not empty comentario.respuestas}">
                                <c:forEach items="${comentario.respuestas}" var="respuesta">
                                    <div class="comentarioHijoPost">
                                        <label class="usuario">
                                            <img class="fotoPerfil" src="<c:url value='/img/iconoPerfil_rojo.png'/>">
                                            <c:out value="${respuesta.usuario.nombreCompleto}"/>
                                        </label>
                                        <p><c:out value="${respuesta.contenido}"/></p>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </section>
                    </c:forEach>
                </c:if>

                <div class="contenedorComentario">
                    <form action="<c:url value='/ComentarioServlet'/>" method="POST">
                        <textarea name="comentario" class="comentario" id="comentario" placeholder="Escribe tu comentario aquí" required></textarea>
                        <input type="hidden" name="postId" value="<c:out value='${post.id}'/>">
                        <input type="hidden" name="usuarioId" value="<c:out value='${usuario.id}'/>">
                        <input type="submit" name="enviar" id="enviar" value="Publicar">
                    </form>
                </div>
            </div>

        </main>
    </body>
</html>
