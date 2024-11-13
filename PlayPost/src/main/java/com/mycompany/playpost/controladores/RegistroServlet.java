/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.playpost.controladores;

import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Estado;
import entidades.Municipio;
import entidades.Usuario;
import enums.TipoUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.itson.apps.playpostdto.EstadoDTO;
import org.itson.apps.playpostdto.MunicipioDTO;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 *
 * @author PC
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistroServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistroServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        // Recibe los parámetros del formulario
        Usuario usuario = new Usuario();
        Municipio municipio = new Municipio();
        Estado estado = new Estado();

        String fechaNacimientoStr = request.getParameter("fecha_nacimiento");

        try {

            LocalDate fechaNacimientoLocal = LocalDate.parse(fechaNacimientoStr);

            // Convertir LocalDate a Calendar
            Calendar fechaNacimientoCal = Calendar.getInstance();
            fechaNacimientoCal.set(fechaNacimientoLocal.getYear(), fechaNacimientoLocal.getMonthValue() - 1, fechaNacimientoLocal.getDayOfMonth());

            usuario.setFechaNacimiento(fechaNacimientoCal);
        } catch (DateTimeParseException e) {
            response.sendRedirect("registro.jsp?error=Formato de fecha inválido");
            return;
        }

        estado.setNombre(request.getParameter("estado"));

        municipio.setEstado(estado);
        municipio.setNombre(request.getParameter("municipio"));

        usuario.setNombreCompleto(request.getParameter("nombre").concat(" ").concat(request.getParameter("apellidos")));
        usuario.setTelefono(request.getParameter("telefono"));
        usuario.setMunicipio(municipio);
        usuario.setCiudad(request.getParameter("ciudad"));
        usuario.setGenero(request.getParameter("genero"));
        usuario.setCorreo(request.getParameter("correo"));
        usuario.setTipo(TipoUsuario.NORMAL);

        String contrasena = request.getParameter("contrasena");
        String confirmarContrasena = request.getParameter("confirmar_contrasena");

        // Validar la contraseña y otros datos según sea necesario
        if (!contrasena.equals(confirmarContrasena)) {
            // Redirige a un mensaje de error o muestra un mensaje en la página
            response.sendRedirect("registro.jsp?error=Contraseñas no coinciden");
            return;
        }
        usuario.setContrasenia(contrasena);

        usuarioBO.agregarUsuario(usuario);

        response.sendRedirect("jsp/inicioSesion.jsp"); // Redirige tras un registro exitoso
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
