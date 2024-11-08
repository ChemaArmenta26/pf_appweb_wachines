package facade;

import com.mycompany.playpostdao.entidades.Usuario;
import java.util.List;

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
    public Usuario agregarUsuario(Usuario usuario);

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario Usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    public Usuario actualizarUsuario(Usuario usuario);

    /**
     * Elimina un usuario del sistema.
     *
     * @param usuario Usuario a eliminar.
     * @return Usuario eliminado.
     */
    public Usuario eliminarUsuario(Usuario usuario);

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param ID Identificador del usuario a buscar.
     * @return Usuario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public Usuario buscarUsuarioPorID(Long ID);

    /**
     * Consulta todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    public List<Usuario> consultarTodosLosUsuarios();
}
