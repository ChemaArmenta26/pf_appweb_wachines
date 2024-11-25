
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/estilos/barraNavegacionStyle.css'/>">

<header>
    <a href="<c:url value='/PostControlador?accion=mostrarPagPrincipal'/>">
        <img class="imgLogo" src="<c:url value='/img/playpost.png'/>" alt="Logo">
    </a>
    <nav class="navegacion">
        <a href="<c:url value='/PostControlador?accion=mostrarPagPrincipal'/>">Inicio</a>
        <a href="<c:url value='/AdminPostServlet'/>">Gestión de publicaciones</a>
        <a href="#acerca_de" class="navAcercaDe">Acerca de</a>
    </nav>
    <label id="mi_perfil">
        <img id="iconoPerfil" src="<c:url value='/img/iconamoon_profile-circle-bold.png'/>" alt="Perfil">Mi perfil
    </label>
    <ul class="opcionesPerfil">
        <li class="datosUsuario"><c:out value="${usuario.nombreCompleto}"/></li>
        <li><a href="<c:url value='/misPublicaciones.jsp'/>">Mis publicaciones</a></li>
        <li><a href="<c:url value='/EditarPerfilServlet'/>">Editar perfil</a></li>
        <li><a href="#cerrar_sesion">Cerrar sesión</a></li>
        <li class="listAcercaDe"><a href="#acerca_de">Acerca de</a></li>
    </ul>
</header>
