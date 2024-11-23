<%-- 
    Document   : editarPerfil
    Created on : 22 nov 2024, 19:04:54
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
        <title>PlayPost - Editar Perfil</title>
        <link rel="stylesheet" href="<c:url value='/estilos/editarPerfilStyle.css'/>">
    </head>
    <body>
        <!-- Incluye la navegación -->
        <jsp:include page="fragmentos/BarraNavegacion.jsp" />

        <div class="editar-perfil-container">
            <main class="editar-perfil-box">
                <div class="profile-header">
                    <img src="<c:url value='${not empty usuario.avatar ? usuario.avatar : "/img/default-avatar.png"}'/>" 
                         alt="Foto de perfil" 
                         class="profile-picture"
                         id="preview-avatar">
                    <input type="file" 
                           id="avatar-input" 
                           accept="image/*" 
                           style="display: none;">
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

                <form action="<c:url value='/EditarPerfilServlet'/>" method="post"  enctype="multipart/form-data">
                    <input type="hidden" name="avatar_filename" id="avatar_filename" value="${usuario.avatar}">


                    <div class="form-group">
                        <input type="text" 
                               name="nombre" 
                               placeholder="Nombre" 
                               value="${empty errores ? nombres : param.nombre}"
                               required>
                        <input type="text" 
                               name="apellidos" 
                               placeholder="Apellidos" 
                               value="${empty errores ? apellidos : param.apellidos}"
                               required>
                    </div>

                    <div class="form-group">
                        <input type="tel" 
                               name="telefono" 
                               placeholder="Teléfono" 
                               value="${empty errores ? usuario.telefono : param.telefono}"
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
                               value="${empty errores ? usuario.municipio.estado.nombre : param.estado}"
                               required>
                        <input type="text" 
                               name="municipio" 
                               placeholder="Municipio" 
                               value="${empty errores ? usuario.municipio.nombre : param.municipio}"
                               required>
                    </div>

                    <input type="text" 
                           name="ciudad" 
                           placeholder="Ciudad" 
                           value="${empty errores ? usuario.ciudad : param.ciudad}"
                           required>
                    <input type="email" 
                           name="correo" 
                           placeholder="Correo" 
                           value="${empty errores ? usuario.correo : param.correo}"
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
                    document.getElementById('avatar-input').onchange = function (e) {
                        const file = e.target.files[0];
                        if (file) {
                            const reader = new FileReader();
                            reader.onload = function (e) {
                                document.getElementById('preview-avatar').src = e.target.result;
                            }
                            reader.readAsDataURL(file);

                            const formData = new FormData();
                            formData.append('avatar', file);

                            // Enviar el archivo al servidor
                            fetch('<c:url value="/EditarPerfilServlet?action=uploadAvatar"/>', {
                                method: 'POST',
                                body: formData
                            })
                                    .then(response => response.json())
                                    .then(data => {
                                        if (data.success) {
                                            document.getElementById('avatar_filename').value = data.filename;
                                            console.log("Avatar filename guardado: " + data.filename); // Para debug                                
                                        } else {
                                            alert('Error al subir la imagen: ' + data.message);
                                        }
                                    })
                                    .catch(error => {
                                        console.error('Error:', error);
                                        alert('Error al subir la imagen');
                                    });
                        }
                    };
                </script>
            </main>
        </div>
    </body>
</html>