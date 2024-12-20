package conexion;

import daos.IPostDAO;
import daos.IUsuarioDAO;
import daos.PostDAO;
import daos.UsuarioDAO;
import entidades.Estado;
import entidades.Municipio;
import entidades.Post;
import entidades.Usuario;
import enums.TipoPost;
import enums.TipoUsuario;
import excepciones.PersistenciaException;
import java.util.Calendar;

public class pruebaConexion {

    public static void main(String[] args) throws PersistenciaException {
        // Crear una instancia de Calendar con la fecha y hora actual
        Calendar fechaHoraCreacion = Calendar.getInstance();

        // Crear el Estado
        Estado estado = new Estado("Estado de Prueba");

        // Crear el Municipio asociado al Estado
        Municipio municipio = new Municipio("Municipio de Prueba", estado);

        // Crear el Usuario asociado al Municipio
        String nombreCompleto = "Usuario de Prueba";
        String correo = "usuario@prueba.com";
        String contrasenia = "contrasenia123";
        String telefono = "123456789";
        String ciudad = "Ciudad de Prueba";
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.set(1990, Calendar.JANUARY, 1); // Fecha de nacimiento de ejemplo
        String genero = "Masculino";

        Usuario usuario = new Usuario(
                nombreCompleto,
                correo,
                contrasenia,
                telefono,
                ciudad,
                fechaNacimiento,
                genero,
                municipio,
                TipoUsuario.ADMOR
        );

        IUsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.agregarUsuario(usuario);

        System.out.println("Post agregado exitosamente con el usuario asignado.");
    }
}
