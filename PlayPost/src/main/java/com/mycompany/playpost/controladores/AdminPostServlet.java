/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.playpost.controladores;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import entidades.Post;
import enums.TipoPost;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoseH
 */
@WebServlet(name = "AdminPostServlet", urlPatterns = {"/AdminPostServlet"})
public class AdminPostServlet extends HttpServlet {
    private IPostBO postBO = new PostBO();
    private final String pagAdmin = "/jsp/gestionPublicaciones.jsp";

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
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        
        String jsonString = jsonBuilder.toString();
        
        Gson gson = new Gson();
        JsonObject jsonRequest = gson.fromJson(jsonString, JsonObject.class);
        
        long id = jsonRequest.get("id").getAsLong();
        String accion = jsonRequest.get("accion").getAsString();
        
        switch (accion){
            case "anclar":
                anclarPost(request, response, id);
                break;
            case "eliminar":
                eliminar(request, response, id);
                break;
            default:
                throw new AssertionError();
        }
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
        request.getRequestDispatcher(pagAdmin).forward(request, response);
    }
    
    protected void anclarPost(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        Post post = postBO.buscarPostPorID(id);
        post.setTipo(TipoPost.ANCLADO);
        postBO.actualizarPost(post);
        
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true);
        jsonResponse.addProperty("message", "Acción realizada correctamente");
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
    
    protected void eliminar(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        Post post = postBO.buscarPostPorID(id);
        postBO.eliminarPost(post);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true);
        jsonResponse.addProperty("message", "Acción realizada correctamente");
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
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
