<%-- 
    Document   : BarraNavegacion
    Created on : 7 nov 2024, 21:36:26
    Author     : JoseH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/estilos/barraNavegacionStyle.css">
        <title>JSP Page</title>
    </head>
    <body>
         <header>
        <a href="pantallaPrincipal.html"><img class="imgLogo" src="${pageContext.request.contextPath}/img/playpost.png"></a>
        <nav class="navegacion">
            <a href="#inicio">Inicio</a>
            <a href="pantallaPrincipal.html">Publicaciones</a>
            <a href="gestionPublicaciones.html">Gestión de publicaciones</a>
            <a href="#acerca_de" class="navAcercaDe">Acerca de</a>
        </nav>
        <label href="#mi_perfil"><img id="iconoPerfil" src="${pageContext.request.contextPath}/img/iconamoon_profile-circle-bold.png">Mi perfil
        </label>
        <ul class="opcionesPerfil">
            <li name="datosUsuario">José Ángel Huerta Amparán</li>
            <li name="datosUsuario">Wacho</li>
            <li><a href="misPublicaciones.html">Mis publicaciones</a></li>
            <li><a href="#editar_avatar">Editar avatar</a></li>
            <li><a href="#cerrar_sesion">Cerrar sesión</a></li>
            <li class="listAcercaDe"><a href="#acerca_de">Acerca de</a></li>
        </ul>
    </header>
    </body>
</html>
