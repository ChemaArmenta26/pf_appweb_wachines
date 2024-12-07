package com.mycompany.playpost.controladores;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Comentario;
import entidades.Post;
import entidades.Usuario;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 *
 * @author victo
 */
@WebServlet(name = "PublicacionServlet", urlPatterns = {"/PublicacionServlet"})
public class PublicacionServlet extends HttpServlet {

    private IPostBO postBO = new PostBO();
    private IUsuarioBO usuarioBO = new UsuarioBO();

    @Override
    public void init() throws ServletException {
        reiniciarConexiones();
    }

    private synchronized void reiniciarConexiones() {
        // Cerrar el BO anterior si existe
        if (postBO != null) {
            try {
                ((PostBO) postBO).cerrar(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        postBO = new PostBO();
    }
    
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

    private String getContextPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.endsWith("/") ? contextPath : contextPath + "/";
    }

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

        System.out.println("PublicacionServlet - Iniciando doGet");

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        try {
        String postIdParam = request.getParameter("id");
        if (postIdParam == null || postIdParam.trim().isEmpty()) {
            throw new ServletException("ID");
        }
        
        long postId = Long.parseLong(postIdParam);
        Post post = postBO.buscarPostPorID(postId);

        if (post == null) {
            throw new ServletException("Post");
        }

        String aceptarHeader = request.getHeader("Accept");
        boolean isJSON = aceptarHeader != null && aceptarHeader.contains("application/json");

            System.out.println("Solicitando respuesta JSON: " + isJSON);

            if (isJSON) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String contextPath = request.getContextPath();
                if (!contextPath.endsWith("/")) {
                    contextPath += "/";
                }

                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("id", post.getId());
                jsonResponse.addProperty("titulo", post.getTitulo());
                jsonResponse.addProperty("contenido", post.getContenido());
                jsonResponse.addProperty("fechaFormateada", sdf.format(post.getFechaHoraCreacion().getTime()));

                String imagenPath = post.getImagenData();
                if (imagenPath != null && !imagenPath.startsWith("http")) {
                    if (imagenPath.startsWith("/")) {
                        imagenPath = imagenPath.substring(1);
                    }
                    imagenPath = contextPath + imagenPath;
                }
                jsonResponse.addProperty("imagenData", imagenPath);

                JsonArray comentariosArray = new JsonArray();
                for (Comentario comentario : post.getComentarios()) {
                    JsonObject comentarioJson = new JsonObject();
                    comentarioJson.addProperty("id", comentario.getId());
                    comentarioJson.addProperty("contenido", comentario.getContenido());
                    comentarioJson.addProperty("fechaHora", sdf.format(comentario.getFechaHora().getTime()));

                    JsonObject usuarioJson = new JsonObject();
                    Usuario usuario = comentario.getUsuario();
                    if (usuario != null) {
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
                    }
                    comentarioJson.add("usuario", usuarioJson);

                    JsonArray respuestasArray = new JsonArray();
                    if (comentario.getRespuestas() != null) {
                        for (Comentario respuesta : comentario.getRespuestas()) {
                            JsonObject respuestaJson = new JsonObject();
                            respuestaJson.addProperty("id", respuesta.getId());
                            respuestaJson.addProperty("contenido", respuesta.getContenido());
                            respuestaJson.addProperty("fechaHora", sdf.format(respuesta.getFechaHora().getTime()));

                            JsonObject usuarioRespuestaJson = new JsonObject();
                            Usuario usuarioRespuesta = respuesta.getUsuario();
                            if (usuarioRespuesta != null) {
                                usuarioRespuestaJson.addProperty("id", usuarioRespuesta.getId());
                                usuarioRespuestaJson.addProperty("nombreCompleto", usuarioRespuesta.getNombreCompleto());

                                String avatarPath = usuarioRespuesta.getAvatar();
                                if (avatarPath != null && !avatarPath.startsWith("http")) {
                                    if (avatarPath.startsWith("/")) {
                                        avatarPath = avatarPath.substring(1);
                                    }
                                    avatarPath = contextPath + avatarPath;
                                }
                                usuarioRespuestaJson.addProperty("avatar", avatarPath);
                            }
                            respuestaJson.add("usuario", usuarioRespuestaJson);
                            respuestasArray.add(respuestaJson);
                        }
                    }
                    comentarioJson.add("respuestas", respuestasArray);
                    comentariosArray.add(comentarioJson);
                }
                jsonResponse.add("comentarios", comentariosArray);

                String jsonString = new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(jsonResponse);
                response.getWriter().write(jsonString);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                request.setAttribute("post", post);
                request.setAttribute("fechasFormateadas", sdf.format(post.getFechaHoraCreacion().getTime()));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/publicacion.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServletException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("ID") || errorMessage.equals("Post")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error en PublicacionServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
        processRequest(request, response);
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
