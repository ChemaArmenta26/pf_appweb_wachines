
package com.mycompany.playpostobjetosnegocio.BOs;

import entidades.Post;
import entidades.Usuario;
import enums.TipoPost;
import facade.FacadePost;
import facade.FacadeUsuario;
import facade.IFacadePost;
import facade.IFacadeUsuario;
import java.util.List;

/**
 *
 * @author JoseH
 */
public class PostBO implements IPostBO {

    IFacadePost facadePost;

    public PostBO() {
        facadePost = new FacadePost();
    }

    /**
     * Agrega una nueva publicación al sistema.
     *
     * @param postAgregar Publicación a agregar.
     * @return Publicación agregada.
     */
    @Override
    public Post agregarPost(Post postAgregar) {
        IFacadeUsuario fUsuario = new FacadeUsuario();
        Usuario usuario = fUsuario.buscarUsuarioPorID(postAgregar.getUsuario().getId());

        Post post = new Post(postAgregar.getFechaHoraCreacion(),
                postAgregar.getTitulo(),
                postAgregar.getContenido(), postAgregar.getCategoria(),
                usuario,
                postAgregar.getTipo(),
                postAgregar.getImagenData()
        );

        Post postAgregado = facadePost.agregarPost(post);
        return postAgregado;
    }

    /**
     * Actualiza la información de una publicación existente.
     *
     * @param postActualizar Publicación a actualizar
     * @return Publicación actualizada.
     */
    @Override
    public Post actualizarPost(Post postActualizar) {
        Usuario usuario = new Usuario(postActualizar.getUsuario().getNombreCompleto());

        Post post = facadePost.buscarPostPorID(postActualizar.getId());
        post.setFechaHoraCreacion(postActualizar.getFechaHoraCreacion());
        post.setTitulo(postActualizar.getTitulo());
        post.setContenido(postActualizar.getContenido());
        post.setUsuario(usuario);
        post.setTipo(postActualizar.getTipo());
        post.setImagenData(postActualizar.getImagenData());

        Post postActualizado = facadePost.actualizarPost(post);
        return postActualizado;
    }

    /**
     * Ancla una publicación.
     *
     * @param postAnclar Publicación a anclar.
     * @return Publicación anclada.
     */
    @Override
    public Post anclarPost(Post postAnclar) {
        Post post = facadePost.buscarPostPorID(postAnclar.getId());
        if (post != null) {
            post.setTipo(TipoPost.ANCLADO);

            Post postAnclado = facadePost.actualizarPost(post);
            return postAnclado;
        }
        return null;
    }

    /**
     * Elimina una publicación del sistema.
     *
     * @param postEliminar Publicación a eliminar.
     * @return Publicación eliminada.
     */
    @Override
    public Post eliminarPost(Post postEliminar) {
        Post post = facadePost.buscarPostPorID(postEliminar.getId());
        if (post != null) {
            Post postEliminado = facadePost.eliminarPost(post);
            return postEliminado;
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
        Post post = facadePost.buscarPostPorID(id);
        if (post != null) {
            return post;
        }
        return null;
    }

    /**
     * Consulta todas las publicaciones subidas
     *
     * @return Lista de todas las publicaciones subidas
     */
    @Override
    public List<Post> consultarTodosLosPosts() {
        List<Post> posts = facadePost.consultarTodosLosPosts();

        return posts;
    }
}
