package facade;

import com.mycompany.playpostdao.daos.PostDAO;
import com.mycompany.playpostdao.entidades.Comentario;
import com.mycompany.playpostdao.entidades.Post;
import com.mycompany.playpostdao.entidades.Usuario;
import com.mycompany.playpostdao.enums.TipoPost;
import static com.mycompany.playpostdao.enums.TipoPost.COMUN;
import static org.itson.apps.playpostdto.enums.TipoPost.COMUN;
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
     * @param postDTO Publicación a agregar.
     * @return Publicación agregada.
     */
    @Override
    public PostDTO agregarPost(PostDTO postDTO) {
        try {
            TipoPost tipoPost;
            Usuario usuario = new Usuario(postDTO.getUsuario().getNombreCompleto());
            if (postDTO.getTipo() == org.itson.apps.playpostdto.enums.TipoPost.COMUN) {
                tipoPost = com.mycompany.playpostdao.enums.TipoPost.COMUN;;
            } else {
                tipoPost = com.mycompany.playpostdao.enums.TipoPost.ANCLADO;;
            }
            Post post = new Post(postDTO.getFechaHoraCreacion(), postDTO.getTitulo(), postDTO.getContenido(), usuario, tipoPost);
            Post postAgregado = factory.crearDAO().agregarPost(post);
            PostDTO postAgregadoDTO = new PostDTO();
            postAgregadoDTO.setAnclado(postAgregado.getAnclado());
            
            List <ComentarioDTO> comentariosDTO = new ArrayList<>();
            for (Comentario comentario : postAgregado.getComentarios()){
                ComentarioDTO comentarioDTO = new ComentarioDTO();
                comentarioDTO.setContenido(comentario.getContenido());
                comentarioDTO.setFechaHora(comentario.getFechaHora());
                comentariosDTO.add(comentarioDTO);
            }
            
            postAgregadoDTO.setComentarios(comentariosDTO);
            
            return postAgregadoDTO;
            
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de una publicación existente.
     *
     * @param postDTO Publicación con los datos actualizados.
     * @return Publicación actualizada.
     */
    @Override
    public PostDTO actualizarPost(PostDTO postDTO) {
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
     * @param postDTO Publicación a anclar.
     * @return Publicación anclada.
     */
    @Override
    public PostDTO anclarPost(PostDTO postDTO) {
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
     * @param postDTO Publicación a eliminar.
     * @return Publicación eliminada.
     */
    @Override
    public PostDTO eliminarPost(PostDTO postDTO) {
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
    public PostDTO buscarPostPorID(int id) {
        try {
            return factory.crearDAO().buscarPostPorID(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
