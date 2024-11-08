package facade;

import com.mycompany.playpostdao.daos.PostDAO;
import com.mycompany.playpostdao.entidades.Comentario;
import com.mycompany.playpostdao.entidades.Post;
import com.mycompany.playpostdao.entidades.Usuario;
import com.mycompany.playpostdao.enums.TipoPost;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import factoryMethod.FactoryPostDAO;
import factoryMethod.IFactoryDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.apps.playpostdto.ComentarioDTO;
import org.itson.apps.playpostdto.PostDTO;

/**
 * Clase que implementa IFacadePost para utilizar los métodos de acceso a datos.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
/**
 * Clase que implementa IFacadePost para utilizar los métodos de acceso a datos.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public class FacadePost implements IFacadePost {

    IFactoryDAO<PostDAO> factory;

    /**
     * Método constructor. Permite inicializar la FactoryDAO para crear la
     * instancia necesaria.
     */
    public FacadePost() {
        factory = new FactoryPostDAO();
    }

    /**
     * Agrega una nueva publicación al sistema.
     *
     * @param post Publicación a agregar.
     * @return Publicación agregada.
     */
    @Override
    public Post agregarPost(Post post) {
        try {
            return factory.crearDAO().agregarPost(post);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de una publicación existente.
     *
     * @param post Publicación con los datos actualizados.
     * @return Publicación actualizada.
     */
    @Override
    public Post actualizarPost(Post post) {
        try {
            return factory.crearDAO().actualizarPost(post);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Ancla una publicación.
     *
     * @param post Publicación a anclar.
     * @return Publicación anclada.
     */
    @Override
    public Post anclarPost(Post post) {
        try {
            return factory.crearDAO().anclarPost(post);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina una publicación del sistema.
     *
     * @param post Publicación a eliminar.
     * @return Publicación eliminada.
     */
    @Override
    public Post eliminarPost(Post post) {
        try {
            return factory.crearDAO().eliminarPost(post);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca una publicación específica por su identificador único.
     *
     * @param id Id de la publicación a buscar.
     * @return Publicación que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public Post buscarPostPorID(Long id) {
        try {
            return factory.crearDAO().buscarPostPorID(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
