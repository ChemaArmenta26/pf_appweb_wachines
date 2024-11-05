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

            List<ComentarioDTO> comentariosDTO = new ArrayList<>();
            for (Comentario comentario : postAgregado.getComentarios()) {
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
            // Conversión de DTO a entidad Post
            TipoPost tipoPost;
            Usuario usuario = new Usuario(postDTO.getUsuario().getNombreCompleto());
            if (postDTO.getTipo() == org.itson.apps.playpostdto.enums.TipoPost.COMUN) {
                tipoPost = com.mycompany.playpostdao.enums.TipoPost.COMUN;
            } else {
                tipoPost = com.mycompany.playpostdao.enums.TipoPost.ANCLADO;
            }

            Post post = new Post(postDTO.getFechaHoraCreacion(), postDTO.getTitulo(), postDTO.getContenido(), usuario, tipoPost);
            post.setAnclado(postDTO.getAnclado());

            // Actualización del post en el sistema
            Post postActualizado = factory.crearDAO().actualizarPost(post);

            // Conversión de entidad Post a DTO
            PostDTO postActualizadoDTO = new PostDTO();
            postActualizadoDTO.setAnclado(postActualizado.getAnclado());
            postActualizadoDTO.setTitulo(postActualizado.getTitulo());
            postActualizadoDTO.setContenido(postActualizado.getContenido());
            postActualizadoDTO.setFechaHoraCreacion(postActualizado.getFechaHoraCreacion());

            // Procesar los comentarios
            List<ComentarioDTO> comentariosDTO = new ArrayList<>();
            for (Comentario comentario : postActualizado.getComentarios()) {
                ComentarioDTO comentarioDTO = new ComentarioDTO();
                comentarioDTO.setContenido(comentario.getContenido());
                comentarioDTO.setFechaHora(comentario.getFechaHora());
                comentariosDTO.add(comentarioDTO);
            }
            postActualizadoDTO.setComentarios(comentariosDTO);

            return postActualizadoDTO;

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
            // Conversión de DTO a entidad Post
            Post post = factory.crearDAO().buscarPostPorID(postDTO.getId());
            if (post != null) {
                post.setAnclado(true); // Anclar el post

                // Actualización del post
                Post postAnclado = factory.crearDAO().actualizarPost(post);

                // Conversión de la entidad Post a DTO
                PostDTO postAncladoDTO = new PostDTO();
                postAncladoDTO.setAnclado(postAnclado.getAnclado());
                postAncladoDTO.setTitulo(postAnclado.getTitulo());
                postAncladoDTO.setContenido(postAnclado.getContenido());
                postAncladoDTO.setFechaHoraCreacion(postAnclado.getFechaHoraCreacion());

                // Procesar los comentarios
                List<ComentarioDTO> comentariosDTO = new ArrayList<>();
                for (Comentario comentario : postAnclado.getComentarios()) {
                    ComentarioDTO comentarioDTO = new ComentarioDTO();
                    comentarioDTO.setContenido(comentario.getContenido());
                    comentarioDTO.setFechaHora(comentario.getFechaHora());
                    comentariosDTO.add(comentarioDTO);
                }
                postAncladoDTO.setComentarios(comentariosDTO);

                return postAncladoDTO;
            }
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
            // Conversión de DTO a entidad Post
            Post post = factory.crearDAO().buscarPostPorID(postDTO.getId());
            if (post != null) {
                // Eliminar el post
                Post postEliminado = factory.crearDAO().eliminarPost(post);

                // Conversión de la entidad Post eliminada a DTO
                PostDTO postEliminadoDTO = new PostDTO();
                postEliminadoDTO.setTitulo(postEliminado.getTitulo());
                postEliminadoDTO.setContenido(postEliminado.getContenido());
                postEliminadoDTO.setFechaHoraCreacion(postEliminado.getFechaHoraCreacion());

                // Procesar los comentarios
                List<ComentarioDTO> comentariosDTO = new ArrayList<>();
                for (Comentario comentario : postEliminado.getComentarios()) {
                    ComentarioDTO comentarioDTO = new ComentarioDTO();
                    comentarioDTO.setContenido(comentario.getContenido());
                    comentarioDTO.setFechaHora(comentario.getFechaHora());
                    comentariosDTO.add(comentarioDTO);
                }
                postEliminadoDTO.setComentarios(comentariosDTO);

                return postEliminadoDTO;
            }
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
    public PostDTO buscarPostPorID(Long id) {
        try {
            Post post = factory.crearDAO().buscarPostPorID(id);
            if (post != null) {
                // Conversión de entidad Post a DTO
                PostDTO postDTO = new PostDTO();
                postDTO.setAnclado(post.getAnclado());
                postDTO.setTitulo(post.getTitulo());
                postDTO.setContenido(post.getContenido());
                postDTO.setFechaHoraCreacion(post.getFechaHoraCreacion());

                // Procesar los comentarios
                List<ComentarioDTO> comentariosDTO = new ArrayList<>();
                for (Comentario comentario : post.getComentarios()) {
                    ComentarioDTO comentarioDTO = new ComentarioDTO();
                    comentarioDTO.setContenido(comentario.getContenido());
                    comentarioDTO.setFechaHora(comentario.getFechaHora());
                    comentariosDTO.add(comentarioDTO);
                }
                postDTO.setComentarios(comentariosDTO);

                return postDTO;
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
