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
            // Crear directorio temporal si no existe
            String tempPath = System.getProperty("java.io.tmpdir");
            File tempDir = new File(tempPath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

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

                // Obtener ruta absoluta del directorio webapps
                String webappPath = request.getServletContext().getRealPath("/");
                File imagesDir = new File(webappPath + "img/posts");

                // Crear directorio si no existe
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();
                }

                // Guardar el archivo
                File destinationFile = new File(imagesDir, uniqueFileName);
                try (InputStream input = filePart.getInputStream(); FileOutputStream output = new FileOutputStream(destinationFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                }

                // Guardar la ruta relativa en el post
                post.setImagenData("/img/posts/" + uniqueFileName);
            }

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
