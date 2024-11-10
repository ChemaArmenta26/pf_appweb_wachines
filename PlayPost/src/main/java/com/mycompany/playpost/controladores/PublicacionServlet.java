/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.playpost.controladores;

import com.mycompany.playpostobjetosnegocio.BOs.ComentarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.IComentarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import org.itson.apps.playpostdto.PostDTO;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 *
 * @author victo
 */
@WebServlet(name = "PublicacionServlet", urlPatterns = {"/PublicacionServlet"})
public class PublicacionServlet extends HttpServlet {

    private IPostBO postBO = new PostBO();
    private IComentarioBO comentarioBO = new ComentarioBO();
    private IUsuarioBO usuarioBO = new UsuarioBO();
    private final String publicacion = "/jsp/publicacion.jsp";
    private String IdComentarioMayor;

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
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "mostrarPublicacion":
                mostrarPublicacion(request, response);
                break;
            case "agregarComentario":
                agregarComentario(request, response);
                break;
            case "obtenerComentarioHijo":
                obtenerComentarioMayor(request, response);
                break;
            default:
                throw new AssertionError("Acción no soportada: " + accion);
        }
    }

    protected void mostrarPublicacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getParameter("id");

        if (postIdParam != null) {
            long postId = Long.parseLong(postIdParam);
            PostDTO post = postBO.buscarPostPorID(postId);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = sdf.format(post.getFechaHoraCreacion().getTime());

            request.setAttribute("post", post);
            request.setAttribute("fechaFormateada", fechaFormateada);

            RequestDispatcher dispatcher = request.getRequestDispatcher(publicacion);
            dispatcher.forward(request, response);
        } else {
            // Manejo de error si no se proporciona el ID
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de post requerido");
        }
    }

    protected void agregarComentario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String comentarioTexto = request.getParameter("comentario");
        String postId = request.getParameter("postId");
        String usuarioId = request.getParameter("usuarioId");


        Long postIdLong = Long.valueOf(postId);
        Long usuarioIdLong = Long.valueOf(usuarioId);
        PostDTO post = postBO.buscarPostPorID(postIdLong);
        UsuarioDTO usuario = usuarioBO.buscarUsuarioPorID(postIdLong);


//        if (IdComentarioMayor != null && !IdComentarioMayor.isEmpty()) {
//            Long comentarioMayorIdLong = Long.valueOf(IdComentarioMayor);
//            
//            postBO.agregarComentarioHijo(comentarioTexto, comentarioMayorIdLong, usuario, post);
//        } else {

//            postBO.agregarComentario(comentarioTexto, usuario, post);
//        }


        response.sendRedirect("PublicacionServlet?accion=mostrarPublicacion&postId=" + postId);
    
    }

    protected void obtenerComentarioMayor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IdComentarioMayor = request.getParameter("comentarioMayorId");
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
