/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.playpost.controladores;

import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Post;
import entidades.Usuario;
import enums.TipoPost;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import org.itson.apps.playpostdto.PostDTO;
import org.itson.apps.playpostdto.UsuarioDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author JoseH
 */
@WebServlet(name = "PostControlador", urlPatterns = {"/PostControlador"})
@MultipartConfig
public class PostControlador extends HttpServlet {
    private IPostBO postBO = new PostBO();
    private final String pagPrincipal = "/jsp/pantallaPrincipal.jsp";
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
                break;
            case "agregar":
                agregar(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    protected void mostrarPagPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        List<Post> posts = postBO.consultarTodosLosPosts();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<String> fechasFormateadas = new ArrayList<>();
        for (Post post : posts) {
            String fechaFormateada = sdf.format(post.getFechaHoraCreacion().getTime());
            fechasFormateadas.add(fechaFormateada);
        }

        
        request.setAttribute("posts", posts);
        request.setAttribute("fechasFormateadas", fechasFormateadas);
        request.getRequestDispatcher(pagPrincipal).forward(request, response);
    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        request.setAttribute("post", new PostDTO());
        request.getRequestDispatcher(crearPost).forward(request, response);
    }
    
    protected void agregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Post post = new Post();
        post.setTitulo(request.getParameter("titulo"));
        post.setContenido(request.getParameter("descripcion"));
        post.setFechaHoraCreacion(Calendar.getInstance());
        post.setComentarios(null);
        post.setTipo(TipoPost.COMUN);

        IUsuarioBO usuarioBO = new UsuarioBO();
        Usuario usuario = usuarioBO.buscarUsuarioPorID(1L);
        post.setUsuario(usuario);

        SubirImagen upImg = new SubirImagen();
        
        Part filePart = request.getPart("imagen"); 
        InputStream fileContent = filePart.getInputStream();
        
        String fileName = filePart.getSubmittedFileName();
        
        post.setImagenData(upImg.uploadImage(fileContent, fileName, request));

        postBO.agregarPost(post);

        mostrarPagPrincipal(request, response);
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
