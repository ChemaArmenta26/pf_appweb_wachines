/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.playpost.controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycompany.playpostobjetosnegocio.BOs.IPostBO;
import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.PostBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Post;
import entidades.Usuario;
import enums.TipoPost;
import java.io.IOException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import org.itson.apps.playpostdto.PostDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "mostrarPagPrincipal":
                    mostrarPagPrincipal(request, response);
                    break;
                case "nuevo":
                    nuevo(request, response);
                    break;
                default:
                    mostrarPagPrincipal(request, response);
            }
        } else {
            mostrarPagPrincipal(request, response);
        }
    }
    
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            manejarJsonRequest(request, response);
        } else {
            String accion = request.getParameter("accion");
            if (accion != null) {
                switch (accion) {
                    case "agregar":
                        agregar(request, response);
                        break;
                    default:
                        mostrarPagPrincipal(request, response);
                }
            }
        }
    }
    
    private void manejarJsonRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String jsonString = jsonBuilder.toString();
        Gson gson = new Gson();
        JsonObject jsonRequest = gson.fromJson(jsonString, JsonObject.class);
        String accion = jsonRequest.get("accion").getAsString();

        if ("filtrarCategoria".equals(accion)) {
            filtrarCategoria(request, response, jsonRequest);
        }
    }

    protected void mostrarPagPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        Post post = new Post();
        post.setTitulo(request.getParameter("titulo"));
        post.setContenido(request.getParameter("descripcion"));
        post.setFechaHoraCreacion(Calendar.getInstance());
        post.setCategoria(request.getParameter("categoria")); 
        post.setComentarios(null);
      
        String tipo = request.getParameter("tipo");
        post.setTipo(tipo.equals("ANCLADO") ? TipoPost.ANCLADO : TipoPost.COMUN);
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        post.setUsuario(usuario);

        SubirImagen upImg = new SubirImagen();

        Part filePart = request.getPart("imagen");
        InputStream fileContent = filePart.getInputStream();

        String fileName = filePart.getSubmittedFileName();

        post.setImagenData(upImg.uploadImage(fileContent, fileName, request));

        postBO.agregarPost(post);

        jsonResponse.addProperty("success", true);
        jsonResponse.addProperty("message", "Post creado exitosamente");

        out.print(jsonResponse.toString());
        out.flush();
    }

    protected void filtrarCategoria(HttpServletRequest request, HttpServletResponse response, JsonObject jsonRequest)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            String categoria = jsonRequest.get("categoria").getAsString();
            List<Post> posts = postBO.consultarTodosLosPosts();
            List<Post> postsFiltrados = posts.stream()
                    .filter(post -> categoria.equals(post.getCategoria()))
                    .collect(Collectors.toList());

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.add("posts", gson.toJsonTree(postsFiltrados));

            out.print(jsonResponse.toString());
        } catch (Exception e) {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("success", false);
            errorResponse.addProperty("message", "Error al filtrar: " + e.getMessage());
            out.print(errorResponse.toString());
        } finally {
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
