<%-- 
    Document   : CrearPost
    Created on : 7 nov 2024, 23:09:32
    Author     : JoseH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/crearPublicacion.css">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Incluye la navegación -->
        <jsp:include page="fragmentos/BarraNavegacion.jsp" />

        <h1>Crear publicación</h1>

        <form method="post" action="PostControlador" enctype="multipart/form-data">
                <label class="img-input">
                    <input type="file" id="subir_imagen" name="imagen" accept="image/*">
                </label>
                <button type="submit" name="accion" id="publicar" value="agregar">
                    Publicar
                </button>
            </div>

            <div class="linea_divisora"></div>

            <textarea id="titulo" placeholder="Título" name="titulo"></textarea>

            <textarea id="descripcion" name="descripcion" placeholder="Escribe aquí algo..."></textarea>

        </form>
    </body>
</html>
