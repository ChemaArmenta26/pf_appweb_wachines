
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
import java.util.Calendar;
import org.itson.apps.playpostdto.ComentarioDTO;
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
        String postIdParam = request.getParameter("id");

            long postId = Long.parseLong(postIdParam);
            PostDTO post = postBO.buscarPostPorID(postId);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = sdf.format(post.getFechaHoraCreacion().getTime());

            request.setAttribute("post", post);
            request.setAttribute("fechaFormateada", fechaFormateada);

            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/publicacion.jsp");
            dispatcher.forward(request, response);
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
