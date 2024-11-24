<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost - Registrarse</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/registerStyle.css">
    </head>

    <body>
        <div class="register-container">
            <main class="register-box">
                <h2>¡Regístrate aquí!</h2>

                <!-- Mensaje de error si las contraseñas no coinciden u otros errores -->
                <%
                    String error = request.getParameter("error");
                    if (error != null) {
                %>
                <p style="color: red;"><%= error %></p>
                <%
                    }
                %>

                <!-- Formulario de registro que envía los datos al servlet RegistroServlet -->
                <form action="${pageContext.request.contextPath}/RegistroServlet" method="post">
                    <div class="form-group">
                        <input type="text" name="nombreCompleto" placeholder="Nombre Completo" required>
                    </div>
                    <div class="form-group">
                        <input type="date" name="fecha_nacimiento" placeholder="aaaa/mm/dd" required>
                        <input type="tel" name="telefono" placeholder="Teléfono" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="estado" placeholder="Estado" required>
                        <input type="text" name="municipio" placeholder="Municipio" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="ciudad" placeholder="Ciudad" required>
                        <select name="genero" required>
                            <option value="" disabled selected>Selecciona tu género</option>
                            <option value="masculino">Masculino</option>
                            <option value="femenino">Femenino</option>
                            <option value="no-binario">No Binario</option>
                        </select>
                    </div>
                    <input type="email" name="correo" placeholder="Correo" required>
                    <input type="password" name="contrasena" placeholder="Contraseña" required>
                    <p class="password-requirements">Debe tener al menos 1 letra mayúscula, una minúscula y un número.</p>
                    <input type="password" name="confirmar_contrasena" placeholder="Confirmar contraseña" required>
                    <button type="submit">Registrarse</button>
                </form>
            </main>
        </div>
    </body>

</html>
