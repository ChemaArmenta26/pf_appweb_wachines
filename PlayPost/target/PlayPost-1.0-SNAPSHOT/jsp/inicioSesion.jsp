<%-- 
    Document   : inicioSesion
    Created on : 9 nov 2024, 21:08:28
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost - Iniciar Sesión</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/loginStyle.css">
    </head>
    <body>
        <div class="login-container">
            <div class="login-box">
                <h2>Bienvenido a</h2>
                <img src="../img/playpost.png" alt="PlayPost Logo" class="logo">
                <!-- Formulario que envía la información de inicio de sesión al servlet "LoginServlet" mediante POST -->
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <input type="text" name="username" placeholder="Usuario" required>
                    <input type="password" name="password" placeholder="Contraseña" required>
                    <div class="actions">
                        <a href="${pageContext.request.contextPath}/jsp/registro.jsp">¿No tienes una cuenta? <span>Regístrate</span></a>
                    </div>
                    <button type="submit">Iniciar sesión</button>
                </form>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                <p class="error-message"><%= errorMessage %></p>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>

