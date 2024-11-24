package com.mycompany.playpost.controladores;

import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Estado;
import entidades.Municipio;
import entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import auxiliar.Encriptar;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.PrintWriter;

@WebServlet(name = "EditarPerfilServlet", urlPatterns = {"/EditarPerfilServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class EditarPerfilServlet extends HttpServlet {

    private final IUsuarioBO usuarioBO;
    private final Encriptar encriptador;

    public EditarPerfilServlet() {
        this.usuarioBO = new UsuarioBO();
        this.encriptador = new Encriptar();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("jsp/inicioSesion.jsp");
            return;
        }

        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");

        String nombreCompleto = usuarioActual.getNombreCompleto();

        request.setAttribute("nombreCompleto", nombreCompleto);
        request.setAttribute("usuario", usuarioActual);

        request.getRequestDispatcher("jsp/editarPerfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("uploadAvatar".equals(action)) {
            handleAvatarUpload(request, response);
            return;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("jsp/inicioSesion.jsp");
            return;
        }

        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");

        try {
            // Mantener el avatar actual o usar el nuevo si existe
            String avatarActual = request.getParameter("avatar_filename");
            if (avatarActual == null || avatarActual.isEmpty()) {
                avatarActual = usuarioActual.getAvatar();
            }

            // Actualizar datos básicos
            Estado estado = new Estado(request.getParameter("estado"));
            Municipio municipio = new Municipio(request.getParameter("municipio"), estado);
            String nombreCompleto = request.getParameter("nombreCompleto");
            usuarioActual.setNombreCompleto(nombreCompleto);

            usuarioActual.setTelefono(request.getParameter("telefono"));
            usuarioActual.setGenero(request.getParameter("genero"));
            usuarioActual.setMunicipio(municipio);
            usuarioActual.setCiudad(request.getParameter("ciudad"));
            usuarioActual.setCorreo(request.getParameter("correo"));

            // Conservar el avatar
            System.out.println("Avatar antes de actualizar: " + avatarActual);
            usuarioActual.setAvatar(avatarActual);

            String newAvatarFilename = request.getParameter("avatar_filename");
            if (newAvatarFilename != null && !newAvatarFilename.isEmpty()) {
                System.out.println("Nuevo avatar a establecer: " + newAvatarFilename);
                usuarioActual.setAvatar(newAvatarFilename);
            }

            String contrasenaActual = request.getParameter("contrasena_actual");
            if (contrasenaActual != null && !contrasenaActual.isEmpty()) {
                try {
                    // Verificar contraseña actual
                    String contrasenaActualEncriptada = encriptador.encriptar(contrasenaActual);
                    if (!contrasenaActualEncriptada.equals(usuarioActual.getContrasenia())) {
                        request.setAttribute("mensaje", "La contraseña actual es incorrecta");
                        request.setAttribute("tipoMensaje", "error");
                        request.getRequestDispatcher("jsp/editarPerfil.jsp").forward(request, response);
                        return;
                    }

                    // Verificar que las nuevas contraseñas coincidan
                    String nuevaContrasena = request.getParameter("nueva_contrasena");
                    String confirmarContrasena = request.getParameter("confirmar_contrasena");
                    
                    if (nuevaContrasena.isBlank() || confirmarContrasena.isBlank()) {
                        request.setAttribute("mensaje", "La contraseña nueva no puede estar vacía");
                        request.setAttribute("tipoMensaje", "error");
                        request.getRequestDispatcher("jsp/editarPerfil.jsp").forward(request, response);
                        return;
                    }

                    if (!nuevaContrasena.equals(confirmarContrasena)) {
                        request.setAttribute("mensaje", "Las nuevas contraseñas no coinciden");
                        request.setAttribute("tipoMensaje", "error");
                        request.getRequestDispatcher("jsp/editarPerfil.jsp").forward(request, response);
                        return;
                    }

                    // Actualizar contraseña
                    usuarioActual.setContrasenia(encriptador.encriptar(nuevaContrasena));
                } catch (Exception ex) {
                    request.setAttribute("mensaje", "Error al procesar la contraseña");
                    request.setAttribute("tipoMensaje", "error");
                    request.getRequestDispatcher("jsp/editarPerfil.jsp").forward(request, response);
                    return;
                }
            }

            // Actualizar usuario en la base de datos
            System.out.println("Avatar antes de llamar a actualizarUsuario: " + usuarioActual.getAvatar());
            Usuario usuarioActualizado = usuarioBO.actualizarUsuario(usuarioActual);
            System.out.println("Avatar después de actualizar: " + (usuarioActualizado != null ? usuarioActualizado.getAvatar() : "null"));

            if (usuarioActualizado != null) {
                // Asegurarse que el avatar se mantiene en la sesión
                if (usuarioActualizado.getAvatar() == null && avatarActual != null) {
                    usuarioActualizado.setAvatar(avatarActual);
                }
                session.setAttribute("usuario", usuarioActualizado);
                request.setAttribute("mensaje", "Perfil actualizado exitosamente");
                request.setAttribute("tipoMensaje", "exito");
            } else {
                request.setAttribute("mensaje", "Error al actualizar el perfil");
                request.setAttribute("tipoMensaje", "error");
            }

        } catch (Exception e) {
            System.out.println("Error en la actualización: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al actualizar el perfil: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }

        request.getRequestDispatcher("jsp/editarPerfil.jsp").forward(request, response);
    }

    private void handleAvatarUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Part filePart = request.getPart("avatar");
            String fileName = getSubmittedFileName(filePart);

            if (!fileName.toLowerCase().endsWith(".jpg")
                    && !fileName.toLowerCase().endsWith(".png")
                    && !fileName.toLowerCase().endsWith(".jpeg")) {
                out.println("{\"success\": false, \"message\": \"Tipo de archivo no permitido\"}");
                return;
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            String webappPath = getServletContext().getRealPath("/");
            String projectPath = webappPath.substring(0, webappPath.lastIndexOf("target")) + "src/main/webapp";

            String uploadPath = projectPath + "/img/avatars/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String targetPath = webappPath + "/img/avatars/";
            File targetDir = new File(targetPath);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            filePart.write(uploadPath + uniqueFileName);
            filePart.write(targetPath + uniqueFileName);

            // Actualizar avatar en la base de datos
            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            String avatarPath = "/img/avatars/" + uniqueFileName;
            System.out.println("Estableciendo nuevo avatar: " + avatarPath);
            usuario.setAvatar(avatarPath);

            Usuario usuarioActualizado = usuarioBO.actualizarUsuario(usuario);

            if (usuarioActualizado != null) {
                session.setAttribute("usuario", usuarioActualizado);
                out.println("{\"success\": true, \"filename\": \"" + avatarPath + "\"}");
                System.out.println("Avatar actualizado exitosamente: " + avatarPath);
            } else {
                out.println("{\"success\": false, \"message\": \"Error al actualizar el usuario\"}");
                System.out.println("Error al actualizar el usuario con el nuevo avatar");
            }

        } catch (Exception e) {
            System.out.println("Error al subir avatar: " + e.getMessage());
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
