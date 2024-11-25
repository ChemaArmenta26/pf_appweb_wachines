<%-- 
    Document   : editarPerfil
    Created on : 22 nov 2024, 19:04:54
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayPost - Editar Perfil</title>
        <link rel="stylesheet" href="<c:url value='/estilos/editarPerfilStyle.css'/>">
    </head>
    <body>
        <jsp:include page="fragmentos/BarraNavegacion.jsp" />

        <div class="editar-perfil-container">
            <main class="editar-perfil-box">
                <form action="<c:url value='/EditarPerfilServlet'/>" method="post" enctype="multipart/form-data">
                    <div class="profile-header">
                        <img src="<c:url value='${not empty usuario.avatar ? usuario.avatar : "/img/default-avatar.png"}'/>" 
                             alt="Foto de perfil" 
                             class="profile-picture"
                             id="preview-avatar">

                        <div style="display: none;">
                            <input type="file" 
                                   name="avatar"
                                   id="avatar-input" 
                                   accept="image/*"
                                   onchange="previewImage(this)">
                        </div>

                        <button type="button" class="change-picture-btn" onclick="document.getElementById('avatar-input').click()">
                            Cambiar foto de perfil
                        </button>
                    </div>

                    <h2>Editar Perfil</h2>

                    <c:if test="${not empty mensaje}">
                        <div class="mensaje-${tipoMensaje}">
                            ${mensaje}
                        </div>
                    </c:if>

                    <!-- Campos ocultos para mantener información -->
                    <input type="hidden" name="id" value="${usuario.id}">
                    <input type="hidden" name="avatarActual" value="${usuario.avatar}">

                    <div class="form-group">
                        <input type="text" 
                               name="nombreCompleto" 
                               placeholder="Nombre Completo" 
                               value="${usuario.nombreCompleto}"
                               required>
                    </div>

                    <div class="form-group">
                        <input type="tel" 
                               name="telefono" 
                               placeholder="Teléfono" 
                               value="${usuario.telefono}"
                               required>
                        <select name="genero" required>
                            <option value="masculino" ${usuario.genero eq 'masculino' ? 'selected' : ''}>Masculino</option>
                            <option value="femenino" ${usuario.genero eq 'femenino' ? 'selected' : ''}>Femenino</option>
                            <option value="no-binario" ${usuario.genero eq 'no-binario' ? 'selected' : ''}>No Binario</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <input type="text" 
                               name="estado" 
                               placeholder="Estado" 
                               value="${usuario.municipio.estado.nombre}"
                               required>
                        <input type="text" 
                               name="municipio" 
                               placeholder="Municipio" 
                               value="${usuario.municipio.nombre}"
                               required>
                    </div>

                    <input type="text" 
                           name="ciudad" 
                           placeholder="Ciudad" 
                           value="${usuario.ciudad}"
                           required>
                    <input type="email" 
                           name="correo" 
                           placeholder="Correo" 
                           value="${usuario.correo}"
                           required>

                    <div class="password-section">
                        <h3>Cambiar Contraseña</h3>
                        <input type="password" name="contrasena_actual" placeholder="Contraseña actual">
                        <input type="password" name="nueva_contrasena" placeholder="Nueva contraseña">
                        <input type="password" name="confirmar_contrasena" placeholder="Confirmar nueva contraseña">
                        <p class="password-requirements">La nueva contraseña debe tener al menos 1 letra mayúscula, una minúscula y un número.</p>
                    </div>

                    <div class="button-group">
                        <button type="submit" class="btn-guardar">Guardar Cambios</button>
                        <button type="button" 
                                class="btn-cancelar"
                                onclick="window.location.href = '<c:url value='/PostControlador?accion=mostrarPagPrincipal'/>'">
                            Cancelar
                        </button>
                    </div>
                </form>

                <script>
                    function previewImage(input) {
                        if (input.files && input.files[0]) {
                            var reader = new FileReader();
                            reader.onload = function (e) {
                                document.getElementById('preview-avatar').src = e.target.result;
                            }
                            reader.readAsDataURL(input.files[0]);
                        }
                    }
                </script>
            </main>
        </div>
    </body>
</html>