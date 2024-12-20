package com.mycompany.playpost.controladores;

import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Estado;
import entidades.Municipio;
import entidades.Usuario;
import enums.TipoUsuario;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;

/**
 *
 * @author PC
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        String correo = request.getParameter("username");
        String contrasena = request.getParameter("password");

        try {
            boolean existeAdmin = false;

            if (!usuarioBO.consultarTodosLosUsuarios().isEmpty() || usuarioBO.consultarTodosLosUsuarios() == null) {
                for (Usuario usuario : usuarioBO.consultarTodosLosUsuarios()) {
                    if (usuario.getTipo() == TipoUsuario.ADMOR) {
                        existeAdmin = true;
                    }
                }
            }

            if (existeAdmin == false) {
                Calendar fechaNacimiento = Calendar.getInstance();
                fechaNacimiento.set(2004, Calendar.NOVEMBER, 27);
                usuarioBO.agregarUsuario(new Usuario("PlayPost", "playpost@gmail.com", "AdminPlPo_1214", "6442232775", "Obregón", fechaNacimiento, "Hombre", new Municipio("Cajeme", new Estado("Sonora")), TipoUsuario.ADMOR));
            }

            Usuario usuario = usuarioBO.buscarUsuarioPorCorreoYContrasena(correo, contrasena);

            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                response.sendRedirect(request.getContextPath() + "/PostControlador?accion=mostrarPagPrincipal");
            } else {
                request.setAttribute("errorMessage", "Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("/jsp/inicioSesion.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error en el servidor. Por favor, intente más tarde.");
            request.getRequestDispatcher("/jsp/inicioSesion.jsp").forward(request, response);
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
