package com.mycompany.playpost.controladores;

//import com.mycompany.playpostobjetosnegocio.BOs.ComentarioBO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        try {

            String comentarioTexto = request.getParameter("comentario");
            String postId = request.getParameter("postId");
            String usuarioId = request.getParameter("usuarioId");
            String comentarioMayorId = request.getParameter("comentarioMayorId");

            if (comentarioTexto == null || postId == null || usuarioId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Long postIdLong, usuarioIdLong;
            try {
                postIdLong = Long.valueOf(postId);
                usuarioIdLong = Long.valueOf(usuarioId);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Post post = postBO.buscarPostPorID(postIdLong);
            Usuario usuario = usuarioBO.buscarUsuarioPorID(usuarioIdLong);

            if (post == null || usuario == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Comentario comentarioNuevo = new Comentario();
            comentarioNuevo.setContenido(comentarioTexto);
            comentarioNuevo.setFechaHora(Calendar.getInstance());
            comentarioNuevo.setPost(post);
            comentarioNuevo.setUsuario(usuario);

            if (comentarioMayorId != null && !comentarioMayorId.isEmpty()) {
                try {
                    Long comentarioMayorIdLong = Long.valueOf(comentarioMayorId);
                    Comentario comentarioMayor = comentarioBO.buscarComentarioPorID(comentarioMayorIdLong);

                    if (comentarioMayor == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    comentarioBO.agregarComentarioAUnComentario(comentarioMayor, comentarioNuevo);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } else {
                comentarioBO.agregarComentario(comentarioNuevo);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("success", true);
            jsonResponse.put("comentario", new HashMap<String, Object>() {
                {
                    put("id", comentarioNuevo.getId());
                    put("contenido", comentarioNuevo.getContenido());
                    put("fechaHora", comentarioNuevo.getFechaHora().getTimeInMillis());
                    put("usuario", new HashMap<String, Object>() {
                        {
                            put("id", usuario.getId());
                            put("nombreCompleto", usuario.getNombreCompleto());
                            put("avatar", usuario.getAvatar());
                        }
                    });
                }
            });
            jsonResponse.put("postData", new HashMap<String, Object>() {{
            put("titulo", post.getTitulo());
            put("fechaFormateada", post.getFechaHoraCreacion().getTimeInMillis());
            put("contenido", post.getContenido());
            put("imagenData", post.getImagenData());
            put("comentarios", post.getComentarios().size());
        }});

        String jsonString = gson.toJson(jsonResponse);
        System.out.println("Respuesta JSON del servidor: " + jsonString);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
        response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
