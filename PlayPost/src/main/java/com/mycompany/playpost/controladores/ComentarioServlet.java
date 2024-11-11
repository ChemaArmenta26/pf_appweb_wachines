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
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Calendar;
import org.itson.apps.playpostdto.ComentarioDTO;
import org.itson.apps.playpostdto.PostDTO;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 *
 * @author victo
 */
@WebServlet(name = "ComentarioServlet", urlPatterns = {"/ComentarioServlet"})
public class ComentarioServlet extends HttpServlet {

    private String IdComentarioMayor;
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
        IdComentarioMayor = request.getParameter("comentarioMayorId");
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
        String comentarioTexto = request.getParameter("comentario");
        String postId = request.getParameter("postId");
        System.out.println(postId);
        String usuarioId = request.getParameter("usuarioId");

        Long postIdLong = Long.valueOf(postId);
        Long usuarioIdLong = Long.valueOf(usuarioId);
        PostDTO post = postBO.buscarPostPorID(postIdLong);
        UsuarioDTO usuario = usuarioBO.buscarUsuarioPorID(usuarioIdLong);


        if (IdComentarioMayor != null) {
            Long comentarioMayorIdLong = Long.valueOf(IdComentarioMayor);
            
            ComentarioDTO comentarioMayor = comentarioBO.buscarComentarioPorID(comentarioMayorIdLong);
            
            ComentarioDTO comentarioNuevo = new ComentarioDTO();
            comentarioNuevo.setContenido(comentarioTexto);
            
            Calendar fecha = Calendar.getInstance();
            comentarioNuevo.setFechaHora(fecha);
            
            comentarioNuevo.setPost(post);
            comentarioNuevo.setUsuario(usuario);
            
            comentarioBO.agregarComentarioAUnComentario(comentarioMayor, comentarioNuevo);
        } else {

            ComentarioDTO comentarioNuevo = new ComentarioDTO();
            comentarioNuevo.setContenido(comentarioTexto);
            
            Calendar fecha = Calendar.getInstance();
            comentarioNuevo.setFechaHora(fecha);
            
            comentarioNuevo.setPost(post);
            comentarioNuevo.setUsuario(usuario);
            comentarioBO.agregarComentario(comentarioNuevo);
        }

        response.sendRedirect("jsp/publicacion.jsp?postId=" + postId);
    
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
