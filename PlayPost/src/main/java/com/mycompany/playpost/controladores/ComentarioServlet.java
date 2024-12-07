package com.mycompany.playpost.controladores;

//import com.mycompany.playpostobjetosnegocio.BOs.ComentarioBO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.playpostobjetosnegocio.BOs.ComentarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.IComentarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Comentario;
import entidades.Post;
import entidades.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static javax.swing.UIManager.put;

/**
 *
 * @author victo
 */
@WebServlet(name = "ComentarioServlet", urlPatterns = {"/ComentarioServlet"})
public class ComentarioServlet extends HttpServlet {

    private IPostBO postBO = new PostBO();
    private IComentarioBO comentarioBO = new ComentarioBO();
    private IUsuarioBO usuarioBO = new UsuarioBO();

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String comentarioTexto = request.getParameter("comentario");
            String postId = request.getParameter("postId");
            String usuarioId = request.getParameter("usuarioId");
            String comentarioMayorId = request.getParameter("comentarioMayorId");

            if (comentarioTexto == null || postId == null || usuarioId == null) {
                throw new ServletException("Parámetros incompletos");
            }

            Post post = postBO.buscarPostPorID(Long.valueOf(postId));
            Usuario usuario = usuarioBO.buscarUsuarioPorID(Long.valueOf(usuarioId));

            Comentario comentarioNuevo = new Comentario();
            comentarioNuevo.setContenido(comentarioTexto);
            comentarioNuevo.setFechaHora(Calendar.getInstance());
            comentarioNuevo.setPost(post);
            comentarioNuevo.setUsuario(usuario);

            if (comentarioMayorId != null && !comentarioMayorId.isEmpty()) {
                Comentario comentarioMayor = comentarioBO.buscarComentarioPorID(Long.valueOf(comentarioMayorId));
                comentarioBO.agregarComentarioAUnComentario(comentarioMayor, comentarioNuevo);
            } else {
                comentarioBO.agregarComentario(comentarioNuevo);
            }

            post = postBO.buscarPostPorID(Long.valueOf(postId));
            Comentario comentarioActualizado = post.getComentarios().stream()
                    .filter(c -> c.getId().equals(comentarioNuevo.getId()))
                    .findFirst()
                    .orElse(comentarioNuevo);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String contextPath = request.getContextPath();
            if (!contextPath.endsWith("/")) {
                contextPath += "/";
            }

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);

            JsonObject comentarioJson = new JsonObject();
            comentarioJson.addProperty("id", comentarioActualizado.getId());
            comentarioJson.addProperty("contenido", comentarioActualizado.getContenido());
            comentarioJson.addProperty("fechaHora", sdf.format(comentarioNuevo.getFechaHora().getTime()));

            JsonObject usuarioJson = new JsonObject();
            usuarioJson.addProperty("id", usuario.getId());
            usuarioJson.addProperty("nombreCompleto", usuario.getNombreCompleto());

            String avatarPath = usuario.getAvatar();
            if (avatarPath != null && !avatarPath.startsWith("http")) {
                if (avatarPath.startsWith("/")) {
                    avatarPath = avatarPath.substring(1);
                }
                avatarPath = contextPath + avatarPath;
            }
            usuarioJson.addProperty("avatar", avatarPath);
            comentarioJson.add("usuario", usuarioJson);

            if (comentarioNuevo.getRespuestas() != null && !comentarioNuevo.getRespuestas().isEmpty()) {
                JsonArray respuestasArray = new JsonArray();
                for (Comentario respuesta : comentarioNuevo.getRespuestas()) {
                    JsonObject respuestaJson = new JsonObject();
                    respuestaJson.addProperty("id", respuesta.getId());
                    respuestaJson.addProperty("contenido", respuesta.getContenido());
                    respuestaJson.addProperty("fechaHora", sdf.format(respuesta.getFechaHora().getTime()));

                    JsonObject usuarioRespuestaJson = new JsonObject();
                    Usuario usuarioRespuesta = respuesta.getUsuario();
                    usuarioRespuestaJson.addProperty("id", usuarioRespuesta.getId());
                    usuarioRespuestaJson.addProperty("nombreCompleto", usuarioRespuesta.getNombreCompleto());

                    String avatarRespuestaPath = usuarioRespuesta.getAvatar();
                    if (avatarRespuestaPath != null && !avatarRespuestaPath.startsWith("http")) {
                        if (avatarRespuestaPath.startsWith("/")) {
                            avatarRespuestaPath = avatarRespuestaPath.substring(1);
                        }
                        avatarRespuestaPath = contextPath + avatarRespuestaPath;
                    }
                    usuarioRespuestaJson.addProperty("avatar", avatarRespuestaPath);
                    respuestaJson.add("usuario", usuarioRespuestaJson);
                    respuestasArray.add(respuestaJson);
                }
                comentarioJson.add("respuestas", respuestasArray);
            }

            jsonResponse.add("comentario", comentarioJson);

            String jsonString = new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(jsonResponse);
            out.print(jsonString);
            out.flush();

        } catch (Exception e) {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("success", false);
            errorResponse.addProperty("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorResponse.toString());
            out.flush();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
