package facade;

import com.mycompany.playpostdao.entidades.Usuario;
import java.util.List;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 * Interfaz para la fachada de operaciones sobre entidades de tipo Usuario.
 * 
 * @autor Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public interface IFacadeUsuario {

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param usuario Usuario a agregar.
     * @return Usuario agregado.
     */
    public UsuarioDTO agregarUsuario(UsuarioDTO usuario);

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario Usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuario);

    /**
     * Elimina un usuario del sistema.
     *
     * @param usuario Usuario a eliminar.
     * @return Usuario eliminado.
     */
    public UsuarioDTO eliminarUsuario(UsuarioDTO usuario);

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param ID Identificador del usuario a buscar.
     * @return Usuario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public UsuarioDTO buscarUsuarioPorID(int ID);

    /**
     * Consulta todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    public List<UsuarioDTO> consultarTodosLosUsuarios();
}
