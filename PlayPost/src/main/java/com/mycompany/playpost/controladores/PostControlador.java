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
import java.io.File;
import java.io.FileOutputStream;
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
                case "filtrarCategoria":
                    filtrarCategoria(request, response);
                    break;
                case "misPublicaciones":
                    mostrarMisPublicaciones(request, response);
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

        try {
            // Crear el objeto Post
            Post post = new Post();
            post.setTitulo(request.getParameter("titulo"));
            post.setContenido(request.getParameter("descripcion"));
            post.setFechaHoraCreacion(Calendar.getInstance());
            post.setCategoria(request.getParameter("categoria"));
            post.setComentarios(null);

            // Establecer el tipo de post
            String tipo = request.getParameter("tipo");
            post.setTipo(tipo.equals("ANCLADO") ? TipoPost.ANCLADO : TipoPost.COMUN);

            // Obtener el usuario actual desde la sesión
            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            post.setUsuario(usuario);

            // Manejar la subida de la imagen
            Part filePart = request.getPart("imagen");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();

                // Validar tipo de archivo
                if (!fileName.toLowerCase().endsWith(".jpg")
                        && !fileName.toLowerCase().endsWith(".png")
                        && !fileName.toLowerCase().endsWith(".jpeg")) {
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Tipo de archivo no permitido");
                    out.print(jsonResponse.toString());
                    return;
                }

                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

                // Obtener rutas de desarrollo y producción
                String webappPath = request.getServletContext().getRealPath("/");
                String projectPath = webappPath.substring(0, webappPath.lastIndexOf("target")) + "src/main/webapp";

                String uploadPath = projectPath + "/img/posts/";
                String targetPath = webappPath + "/img/posts/";

                // Crear directorios si no existen
                File uploadDir = new File(uploadPath);
                File targetDir = new File(targetPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }

                // Guardar la imagen en ambas rutas usando InputStream
                try (InputStream input = filePart.getInputStream(); FileOutputStream devOutput = new FileOutputStream(new File(uploadPath, uniqueFileName)); FileOutputStream prodOutput = new FileOutputStream(new File(targetPath, uniqueFileName))) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        devOutput.write(buffer, 0, bytesRead);
                        prodOutput.write(buffer, 0, bytesRead);
                    }
                }

                // Guardar la ruta relativa en el objeto post
                String relativePath = "/img/posts/" + uniqueFileName;
                post.setImagenData(relativePath);
            } else {
                post.setImagenData("none");
            }

            // Guardar el post utilizando la capa de negocio
            postBO.agregarPost(post);
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Post creado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error al crear el post: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private void filtrarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String categoria = request.getParameter("categoria");
            List<Post> posts = postBO.consultarTodosLosPosts();
            List<Post> postsFiltrados = new ArrayList<>();
            for (Post post : posts) {
                if (categoria.equals(post.getCategoria())) {
                    postsFiltrados.add(post);
                }
            }
            // Formatear fechas para los posts filtrados
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<String> fechasFormateadas = new ArrayList<>();
            for (Post post : postsFiltrados) {
                String fechaFormateada = sdf.format(post.getFechaHoraCreacion().getTime());
                fechasFormateadas.add(fechaFormateada);
            }
            // Establecer los atributos y redirigir
            request.setAttribute("posts", postsFiltrados);
            request.setAttribute("fechasFormateadas", fechasFormateadas);
            request.getRequestDispatcher(pagPrincipal).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al filtrar los posts: " + e.getMessage());
            request.getRequestDispatcher(pagPrincipal).forward(request, response);
        }
    }

    protected void mostrarMisPublicaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");

        if (usuarioActual != null) {
            List<Post> todosLosPosts = postBO.consultarTodosLosPosts();
            List<Post> misPublicaciones = todosLosPosts.stream()
                    .filter(post -> post.getUsuario().getId().equals(usuarioActual.getId()))
                    .collect(Collectors.toList());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<String> fechasFormateadas = new ArrayList<>();
            for (Post post : misPublicaciones) {
                String fechaFormateada = sdf.format(post.getFechaHoraCreacion().getTime());
                fechasFormateadas.add(fechaFormateada);
            }

            request.setAttribute("posts", misPublicaciones);
            request.setAttribute("fechasFormateadas", fechasFormateadas);
            request.getRequestDispatcher("/jsp/misPublicaciones.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
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
    }

}

