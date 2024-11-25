<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost - Registrarse</title>
        <link rel="stylesheet" href="<c:url value='/estilos/registerStyle.css'/>">
    </head>
    <body>
        <div class="register-container">
            <main class="register-box">
                <h2>¡Regístrate aquí!</h2>

                <c:if test="${not empty mensaje}">
                    <div class="mensaje-${tipoMensaje}">
                        ${mensaje}
                    </div>
                </c:if>
                <!-- Formulario de registro que envía los datos al servlet RegistroServlet -->
                <form action="<c:url value='/RegistroServlet'/>" method="post" onsubmit="return validarFormulario()">
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
                    <input type="password" name="contrasena" id="contrasena" placeholder="Contraseña" required>
                    <p class="password-requirements">Debe tener al menos 1 letra mayúscula, una minúscula y un número.</p>
                    <input type="password" name="confirmar_contrasena" id="confirmar_contrasena" placeholder="Confirmar contraseña" required>
                    <button type="submit">Registrarse</button>
                </form>
            </main>
        </div>

        <script>
            function validarFormulario() {
                var contrasena = document.getElementById('contrasena').value;
                var confirmarContrasena = document.getElementById('confirmar_contrasena').value;

                // Validar que las contraseñas coincidan
                if (contrasena !== confirmarContrasena) {
                    alert('Las contraseñas no coinciden');
                    return false;
                }

                // Validar el formato de la contraseña
                var tieneMayuscula = /[A-Z]/.test(contrasena);
                var tieneMinuscula = /[a-z]/.test(contrasena);
                var tieneNumero = /[0-9]/.test(contrasena);

                if (!tieneMayuscula || !tieneMinuscula || !tieneNumero) {
                    alert('La contraseña debe contener al menos una mayúscula, una minúscula y un número');
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>