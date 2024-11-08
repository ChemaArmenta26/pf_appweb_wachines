/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.playpost.controladores;

import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Calendar;
import org.itson.apps.playpostdto.PostDTO;
import org.itson.apps.playpostdto.UsuarioDTO;
import org.itson.apps.playpostdto.enums.TipoPost;

/**
 *
 * @author JoseH
 */
@WebServlet(name = "PostControlador", urlPatterns = {"/PostControlador"})
public class PostControlador extends HttpServlet {
    private IPostBO postBO = new PostBO();
    private final String pagPrincipal = "/jsp/pantallaPrincipal.jsp ";
    private final String crearPost = "/jsp/crearPost.jsp";

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
        
        String accion =  request.getParameter("accion");
        
        switch (accion){
            case "mostrarPagPrincipal":
                mostrarPagPrincipal(request, response);
                break;
            case "nuevo":
                nuevo(request, response);
            case "agregar":
                agregar(request, response);
            default:
                throw new AssertionError();
        }
    }
    
    protected void mostrarPagPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        request.setAttribute("posts", postBO.buscarPostPorID(1L));
        request.getRequestDispatcher(pagPrincipal).forward(request, response);
    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        request.setAttribute("post", new PostDTO());
        request.getRequestDispatcher(crearPost).forward(request, response);
    }
    
    protected void agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo(request.getParameter("titulo"));
        postDTO.setContenido(request.getParameter("contenido"));
        postDTO.setFechaHoraCreacion(Calendar.getInstance());
        postDTO.setComentarios(null);
        postDTO.setAnclado(false);
        postDTO.setTipo(TipoPost.COMUN);
        postDTO.setUsuario(null);
        
        Part filePart = request.getPart("imagen"); 
        InputStream fileContent = filePart.getInputStream();
        byte[] imageData = new byte[(int) filePart.getSize()];
        fileContent.read(imageData);
        
        postDTO.setImageData(imageData);
        
        postBO.agregarPost(postDTO);
        
        response.sendRedirect(pagPrincipal);
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
        processRequest(request, response);
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
