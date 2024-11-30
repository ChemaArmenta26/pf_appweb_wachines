package filtros;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Esta clase permite aplicar filtros de autentificación al proyecto para que no
 * se pueda acceder a cualquier URL si no es un usuario vaalidado.
 *
 * @author José Maria Armenta Baca
 * @author Víctor Humberto Encinas Guzmán
 * @author José Ángel Huerta Amparán
 */
@WebFilter("/*")
public class filtro implements Filter {

    private static final String[] URL_PUBLICAS = {
        "registro.jsp",
        "inicioSesion.jsp",
        "LoginServlet",
        "RegistroServlet",
        "estilos/registerStyle.css",
        "estilos/loginStyle.css",
        "img/background.png",
        "img/playpost.png"
    };

    public filtro() {
    }

    /**
     * Verifica que exista una sesión activa y que el usuario esté identificado.
     *
     * @param solicitudHttp Solicitud del cliente.
     * @return True si el usuario está logueado, false en caso contrario.
     */
    private boolean estaLogueado(HttpServletRequest solicitudHttp) {
        HttpSession sesion = solicitudHttp.getSession(false);
        return (sesion != null && sesion.getAttribute("usuario") != null);
    }

    /**
     * Verifica si una URL es privada.
     *
     * @param url URL solicitada.
     * @return True si la URL es privada, false en caso contrario.
     */
    private boolean esURLPrivada(String url) {
        if (url.startsWith("/estilos/") || url.startsWith("/img/")) {
            return false;
        }

        for (String ruta : URL_PUBLICAS) {
            if (url.endsWith(ruta)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene la URL a a partir de la solicitud enviada.
     *
     * @param solicitudHttp Solicitud del cliente.
     * @return Ruta relativa.
     */
    private String obtenerURL(HttpServletRequest solicitudHttp) {
        String URI = solicitudHttp.getRequestURI();
        String ruta = URI.substring(solicitudHttp.getContextPath().length());

        return ruta;
    }

    /**
     * Permite que se intercepten las solicitudes y aplica la lógica de
     * autentificación y redirección.
     *
     * @param solicitud Solicitud del cliente.
     * @param respuesta Respuesta para el cliente.
     * @param cadena Filtros que se realizan si la solicitud es permitida.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error en el procesaimiento de la
     * solicitud.
     */
    @Override
    public void doFilter(ServletRequest solicitud, ServletResponse respuesta, FilterChain cadena)
            throws IOException, ServletException {
        HttpServletRequest solicitudHttp = (HttpServletRequest) solicitud;
        HttpServletResponse respuestaHttp = (HttpServletResponse) respuesta;

        // Headers de caché
        respuestaHttp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        respuestaHttp.setHeader("Pragma", "no-cache");
        respuestaHttp.setHeader("Expires", "0");

        String ruta = this.obtenerURL(solicitudHttp);
        boolean esURLPrivada = this.esURLPrivada(ruta);
        boolean estaLogueado = this.estaLogueado(solicitudHttp);

        if (ruta.endsWith("jsp/inicioSesion.jsp")) {
            cadena.doFilter(solicitud, respuesta);
            return;
        }

        if (!estaLogueado && esURLPrivada) {
            respuestaHttp.sendRedirect(solicitudHttp.getContextPath() + "/jsp/inicioSesion.jsp");
            return;
        } else if (estaLogueado && ruta.endsWith("jsp/inicioSesion.jsp")) {
            respuestaHttp.sendRedirect(solicitudHttp.getContextPath() + "/jsp/pantallaPrincipal.jsp");
            return;
        }

        cadena.doFilter(solicitud, respuesta);
    }
}
