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

                <div class="post-image-container">
                    <img class="imgPost" 
                         src="<c:url value='${empty post.imagenData ? "/img/default-avatar.png" : post.imagenData}'/>" 
                         alt="<c:out value='${post.titulo}'/>"
                         onerror="this.onerror=null; this.src='<c:url value='/img/default-avatar.png'/>';">
                </div>

                <div class="infoPost">
                    <p><c:out value="${post.contenido}"/></p>
                </div>
            </section>

            <span class="separador"></span>

            <div class="comentarios">
                <label class="textoIconoComentario">
                    <img class="iconoComentario" src="<c:url value='/img/material-symbols-light_comment-sharp.png'/>">
                    <span id="contador-comentarios"><c:out value="${fn:length(post.comentarios)}"/></span>
                </label>

                <div id="comentariosContenedor">

                </div>

                <div class="contenedorComentario">
                    <form id="form-comentario" method="POST">
                        <textarea name="comentario" class="comentario" 
                                  placeholder="Escribe tu comentario aquí" required></textarea>
                        <input type="hidden" name="postId" value="<c:out value='${post.id}'/>">
                        <input type="hidden" name="usuarioId" value="<c:out value='${usuario.id}'/>">
                        <button type="submit">Publicar</button>
                    </form>
                </div>
            </div>
        </main>


        <template id="comentario-template">
            <section class="comentarioContenedor">
                <div class="comentarioPost">
                    <label class="usuario">
                        <img class="fotoPerfil" src="" alt="Foto de perfil">
                        <span class="nombre-usuario"></span>
                    </label>
                    <p class="contenido-comentario"></p>
                    <div class="responderComentario">
                        <button type="button" class="btnResponder">Responder</button>
                    </div>
                    <div class="formRespuestaHijo" style="display:none">
                        <form class="form-respuesta">
                            <textarea name="comentario" class="comentarioRespuesta" 
                                      placeholder="Escribe tu respuesta aquí" required></textarea>
                            <div class="btnsRespuesta">
                                <button type="button" class="cancelar">Cancelar</button>
                                <button type="submit">Responder</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="respuestasContenedor"></div>
            </section>
        </template>

        <template id="respuesta-template">
            <div class="comentarioHijoPost">
                <label class="usuario">
                    <img class="fotoPerfil" src="" alt="Foto de perfil">
                    <span class="nombre-usuario"></span>
                </label>
                <p class="contenido-comentario"></p>
            </div>
        </template>

        <script src="<c:url value='/js/publicacion.js'/>"></script>
    </body>
</html>
