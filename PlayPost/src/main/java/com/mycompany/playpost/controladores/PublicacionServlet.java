package com.mycompany.playpost.controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author victo
 */
@WebServlet(name = "PublicacionServlet", urlPatterns = {"/PublicacionServlet"})
public class PublicacionServlet extends HttpServlet {

    private IPostBO postBO = new PostBO();
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

//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Expires", "0");
        try {
            String postIdParam = request.getParameter("id");
            System.out.println("ID de post recibido: " + postIdParam);
            if (postIdParam == null || postIdParam.trim().isEmpty()) {
                throw new ServletException("ID");
            }

            long postId = Long.parseLong(postIdParam);
            Post post = postBO.buscarPostPorID(postId);
            System.out.println("Post encontrado: " + (post != null ? "Sí" : "No"));

            if (post == null) {
                throw new ServletException("Post");
            }

            String aceptarHeader = request.getHeader("Accept");
            boolean isJSON = aceptarHeader != null && aceptarHeader.contains("application/json");

            System.out.println("Solicitando respuesta JSON: " + isJSON);

            if (isJSON) {
                post = postBO.buscarPostPorID(postId);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String contextPath = request.getContextPath();

                if (!contextPath.endsWith("/")) {
                    contextPath += "/";
                }

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("id", post.getId());
                jsonResponse.put("titulo", post.getTitulo());
                jsonResponse.put("contenido", post.getContenido());
                jsonResponse.put("fechaFormateada", sdf.format(post.getFechaHoraCreacion().getTime()));

                String imagenPath = post.getImagenData();
                if (imagenPath != null && !imagenPath.startsWith("http")) {
                    if (imagenPath.startsWith("/")) {
                        imagenPath = imagenPath.substring(1);
                    }
                    imagenPath = contextPath + imagenPath;
                }

                System.out.println("Ruta completa de la imagen: " + imagenPath);
                jsonResponse.put("imagenData", imagenPath);

                List<Map<String, Object>> comentariosLista = new ArrayList<>();
                for (Comentario comentario : post.getComentarios()) {
                    Map<String, Object> comentarioMap = new HashMap<>();
                    comentarioMap.put("id", comentario.getId());
                    comentarioMap.put("contenido", comentario.getContenido());
                    comentarioMap.put("fechaHora", sdf.format(comentario.getFechaHora().getTime()));

                    Map<String, Object> usuarioMap = new HashMap<>();
                    Usuario usuario = comentario.getUsuario();
                    if (usuario != null) {
                        usuarioMap.put("id", usuario.getId());
                        usuarioMap.put("nombreCompleto", usuario.getNombreCompleto());

                        String avatarPath = usuario.getAvatar();
                        if (avatarPath != null && !avatarPath.startsWith("http")) {
                            if (avatarPath.startsWith("/")) {
                                avatarPath = avatarPath.substring(1);
                            }
                            avatarPath = contextPath + avatarPath;
                        }
                        usuarioMap.put("avatar", avatarPath);
                    }
                    comentarioMap.put("usuario", usuarioMap);
                    comentariosLista.add(comentarioMap);
                }
                jsonResponse.put("comentarios", comentariosLista);

                String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(jsonResponse);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
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
