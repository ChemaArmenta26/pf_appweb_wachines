package facade;

import com.mycompany.playpostdao.daos.UsuarioDAO;
import com.mycompany.playpostdao.entidades.Estado;
import com.mycompany.playpostdao.entidades.Municipio;
import com.mycompany.playpostdao.entidades.Usuario;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import factoryMethod.FactoryUsuarioDAO;
import factoryMethod.IFactoryDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 * Clase que implementa IFacadeUsuarioDTO para utilizar los métodos de acceso a
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
     * @param usuarioDTO UsuarioDTO a agregar.
     * @return UsuarioDTO agregado.
     */
    @Override
    public UsuarioDTO agregarUsuario(UsuarioDTO usuarioDTO) {
        try {
            Estado estado = new Estado(usuarioDTO.getMunicipio().getEstado().getNombre());
            Municipio municipio = new Municipio(usuarioDTO.getMunicipio().getNombre(), estado);
            Usuario usuario = new Usuario(usuarioDTO.getNombreCompleto(), usuarioDTO.getCorreo(), usuarioDTO.getContrasenia(), usuarioDTO.getTelefono(), usuarioDTO.getCiudad(), usuarioDTO.getFechaNacimiento(), usuarioDTO.getGenero(), municipio);
            
            Usuario usuarioAgregado = factory.crearDAO().agregarUsuario(usuario);
            UsuarioDTO usuarioAgregadoDTO = new UsuarioDTO();
            usuarioAgregadoDTO.setNombreCompleto(usuarioAgregado.getNombreCompleto());
            return usuarioAgregadoDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuarioDTO UsuarioDTO con los datos actualizados.
     * @return UsuarioDTO actualizado.
     */
    @Override
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) {
        try {
            Estado estado = new Estado(usuarioDTO.getMunicipio().getEstado().getNombre());
            Municipio municipio = new Municipio(usuarioDTO.getMunicipio().getNombre(), estado);
            Usuario usuario = new Usuario(usuarioDTO.getNombreCompleto(), usuarioDTO.getCorreo(), usuarioDTO.getContrasenia(), usuarioDTO.getTelefono(), usuarioDTO.getCiudad(), usuarioDTO.getFechaNacimiento(), usuarioDTO.getGenero(), municipio);
            
            Usuario usuarioActualizado =  factory.crearDAO().actualizarUsuario(usuario);
            UsuarioDTO usuarioActualizadoDTO = new UsuarioDTO();
            usuarioActualizadoDTO.setNombreCompleto(usuarioActualizado.getNombreCompleto());
            return usuarioActualizadoDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param usuarioDTO UsuarioDTO a eliminar.
     * @return UsuarioDTO eliminado.
     */
    @Override
    public UsuarioDTO eliminarUsuario(UsuarioDTO usuarioDTO) {
        try {
            Estado estado = new Estado(usuarioDTO.getMunicipio().getEstado().getNombre());
            Municipio municipio = new Municipio(usuarioDTO.getMunicipio().getNombre(), estado);
            Usuario usuario = new Usuario(usuarioDTO.getNombreCompleto(), usuarioDTO.getCorreo(), usuarioDTO.getContrasenia(), usuarioDTO.getTelefono(), usuarioDTO.getCiudad(), usuarioDTO.getFechaNacimiento(), usuarioDTO.getGenero(), municipio);
            
            Usuario usuarioEliminado =  factory.crearDAO().eliminarUsuario(usuario);
            UsuarioDTO usuarioEliminadoDTO = new UsuarioDTO();
            usuarioEliminadoDTO.setNombreCompleto(usuarioEliminado.getNombreCompleto());
            return usuarioEliminadoDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param ID Identificador del usuario a buscar.
     * @return UsuarioDTO que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public UsuarioDTO buscarUsuarioPorID(int ID) {
        try {
            Usuario usuarioEncontrado = factory.crearDAO().buscarUsuarioPorID(ID);
            UsuarioDTO usuarioEncontradoDTO = new UsuarioDTO();
            usuarioEncontradoDTO.setNombreCompleto(usuarioEncontrado.getNombreCompleto());
            return usuarioEncontradoDTO;
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
    public List<UsuarioDTO> consultarTodosLosUsuarios() {
        try {
            List<Usuario> usuariosEncontrados = factory.crearDAO().consultarTodosLosUsuarios();
            List<UsuarioDTO> usuariosEncontradosDTO = new ArrayList<>();
            for(Usuario usuario : usuariosEncontrados){
                UsuarioDTO usuarioEncontradoDTO = new UsuarioDTO();
                usuarioEncontradoDTO.setNombreCompleto(usuario.getNombreCompleto());
                usuariosEncontradosDTO.add(usuarioEncontradoDTO);
            }
            return usuariosEncontradosDTO;
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
