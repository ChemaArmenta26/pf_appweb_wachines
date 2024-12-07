<%-- 
    Document   : inicioSesion
    Created on : 9 nov 2024, 21:08:28
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost - Iniciar Sesión</title>
        <link rel="stylesheet" href="<c:url value='/estilos/loginStyle.css'/>">
    </head>
    <body>
        <div class="login-container">
            <div class="login-box">
                <h2>Bienvenido a</h2>
                <img src="<c:url value='/img/playpost.png'/>" alt="PlayPost Logo" class="logo">

                <form action="<c:url value='/LoginServlet'/>" method="post">
                    <input type="text" name="username" placeholder="Usuario" required>
                    <input type="password" name="password" placeholder="Contraseña" required>
                    <div class="actions">
                        <a href="<c:url value='/jsp/registro.jsp'/>">¿No tienes una cuenta? <span>Regístrate</span></a>
                    </div>
                    <button type="submit">Iniciar sesión</button>
                </form>

                <c:if test="${not empty errorMessage}">
                    <p class="error-message"><c:out value="${errorMessage}"/></p>
                </c:if>
            </div>
        </div>
        <script src="<c:url value='/js/login.js'/>"></script>
    </body>
</html>

