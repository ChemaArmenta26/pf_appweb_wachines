package facade;

import daos.UsuarioDAO;
import entidades.Usuario;
import excepciones.PersistenciaException;
import factoryMethod.FactoryUsuarioDAO;
import factoryMethod.IFactoryDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa IFacadeUsuario para utilizar los métodos de acceso a
 * datos.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public class FacadeUsuario implements IFacadeUsuario {

    IFactoryDAO<UsuarioDAO> factory;

    /**
     * Método constructor. Permite inicializar la FactoryDAO para crear la
     * instancia necesaria.
     */
    public FacadeUsuario() {
        factory = new FactoryUsuarioDAO();
    }

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param usuario Usuario a agregar.
     * @return Usuario agregado.
     */
    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        try {
            return factory.crearDAO().agregarUsuario(usuario);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario Usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        try {
            System.out.println("Facade - Avatar antes de actualizar: " + usuario.getAvatar());
            Usuario usuarioActualizado = factory.crearDAO().actualizarUsuario(usuario);
            System.out.println("Facade - Avatar después de actualizar: "
                    + (usuarioActualizado != null ? usuarioActualizado.getAvatar() : "null"));
            return usuarioActualizado;
        } catch (PersistenciaException ex) {
            System.out.println("Error en Facade actualizarUsuario: " + ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param usuario Usuario a eliminar.
     * @return Usuario eliminado.
     */
    @Override
    public Usuario eliminarUsuario(Usuario usuario) {
        try {
            return factory.crearDAO().eliminarUsuario(usuario);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param ID Identificador del usuario a buscar.
     * @return Usuario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public Usuario buscarUsuarioPorID(Long ID) {
        try {
            return factory.crearDAO().buscarUsuarioPorID(ID);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Consulta todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    @Override
    public List<Usuario> consultarTodosLosUsuarios() {
        try {
            return factory.crearDAO().consultarTodosLosUsuarios();
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorCorreoYContrasena(String correo, String contrasena) {
        try {
            return factory.crearDAO().buscarUsuarioPorCorreoYContrasena(correo, contrasena);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean existeCorreo(String correo) {
        try {
            return factory.crearDAO().existeCorreo(correo);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
