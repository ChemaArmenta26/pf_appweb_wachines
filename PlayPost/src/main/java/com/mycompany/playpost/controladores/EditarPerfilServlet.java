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
import enums.TipoUsuario;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;

@WebServlet(name = "EditarPerfilServlet", urlPatterns = {"/EditarPerfilServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
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
        request.setAttribute("usuario", usuarioActual);
        request.getRequestDispatcher("/jsp/editarPerfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("jsp/inicioSesion.jsp");
            return;
        }

        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");
        String avatarOriginal = usuarioActual.getAvatar(); 

        try {
            Calendar fechaNacimiento = usuarioActual.getFechaNacimiento();
            TipoUsuario tipo = usuarioActual.getTipo();
            Long id = usuarioActual.getId();

            Usuario usuarioActualizar = new Usuario();
            usuarioActualizar.setId(id);
            usuarioActualizar.setFechaNacimiento(fechaNacimiento);
            usuarioActualizar.setTipo(tipo);
            usuarioActualizar.setAvatar(avatarOriginal);

            Estado estado = new Estado(request.getParameter("estado"));
            Municipio municipio = new Municipio(request.getParameter("municipio"), estado);

            usuarioActualizar.setNombreCompleto(request.getParameter("nombreCompleto"));
            usuarioActualizar.setTelefono(request.getParameter("telefono"));
            usuarioActualizar.setGenero(request.getParameter("genero"));
            usuarioActualizar.setMunicipio(municipio);
            usuarioActualizar.setCiudad(request.getParameter("ciudad"));
            usuarioActualizar.setCorreo(request.getParameter("correo"));
            usuarioActualizar.setContrasenia(usuarioActual.getContrasenia());
            
            String contrasenaActual = request.getParameter("contrasena_actual");
            if (contrasenaActual != null && !contrasenaActual.isEmpty()) {
                String contrasenaActualEncriptada = encriptador.encriptar(contrasenaActual);
                if (!contrasenaActualEncriptada.equals(usuarioActual.getContrasenia())) {
                    request.setAttribute("mensaje", "La contraseña actual es incorrecta");
                    request.setAttribute("tipoMensaje", "error");
                    request.getRequestDispatcher("/jsp/editarPerfil.jsp").forward(request, response);
                    return;
                }

                String nuevaContrasena = request.getParameter("nueva_contrasena");
                String confirmarContrasena = request.getParameter("confirmar_contrasena");
                if (!nuevaContrasena.isEmpty() && !confirmarContrasena.isEmpty()) {
                    if (!nuevaContrasena.equals(confirmarContrasena)) {
                        request.setAttribute("mensaje", "Las nuevas contraseñas no coinciden");
                        request.setAttribute("tipoMensaje", "error");
                        request.getRequestDispatcher("/jsp/editarPerfil.jsp").forward(request, response);
                        return;
                    }
                    usuarioActualizar.setContrasenia(encriptador.encriptar(nuevaContrasena));
                }
            }
            String avatarActual = request.getParameter("avatarActual");
            System.out.println("Avatar actual antes de la actualización: " + avatarActual);

            usuarioActualizar.setAvatar(avatarActual);

            Part filePart = request.getPart("avatar");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                if (!fileName.toLowerCase().endsWith(".jpg")
                        && !fileName.toLowerCase().endsWith(".png")
                        && !fileName.toLowerCase().endsWith(".jpeg")) {
                    request.setAttribute("mensaje", "Tipo de archivo no permitido");
                    request.setAttribute("tipoMensaje", "error");
                    request.getRequestDispatcher("/jsp/editarPerfil.jsp").forward(request, response);
                    return;
                }

                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String webappPath = getServletContext().getRealPath("/");
                String projectPath = webappPath.substring(0, webappPath.lastIndexOf("target")) + "src/main/webapp";

                String uploadPath = projectPath + "/img/avatars/";
                String targetPath = webappPath + "/img/avatars/";

                File uploadDir = new File(uploadPath);
                File targetDir = new File(targetPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }

                filePart.write(uploadPath + uniqueFileName);
                filePart.write(targetPath + uniqueFileName);

                String avatarPath = "/img/avatars/" + uniqueFileName;
                System.out.println("Nueva ruta de avatar: " + avatarPath);
                usuarioActualizar.setAvatar(avatarPath);
            }

            System.out.println("Avatar antes de actualizar: " + usuarioActualizar.getAvatar());

            Usuario usuarioActualizado = usuarioBO.actualizarUsuario(usuarioActualizar);

            if (usuarioActualizado != null) {
                System.out.println("Avatar después de actualizar: " + usuarioActualizado.getAvatar());
                session.setAttribute("usuario", usuarioActualizado);
                request.setAttribute("mensaje", "Perfil actualizado exitosamente");
                request.setAttribute("tipoMensaje", "exito");
            } else {
                usuarioActualizar.setAvatar(avatarOriginal);
                request.setAttribute("mensaje", "Error al actualizar el perfil");
                request.setAttribute("tipoMensaje", "error");
            }

        } catch (Exception e) {
            System.out.println("Error en la actualización: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al actualizar el perfil: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }

        request.getRequestDispatcher("/jsp/editarPerfil.jsp").forward(request, response);
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
