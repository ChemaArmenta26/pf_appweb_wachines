/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpostobjetosnegocio.BOs;

import entidades.Comentario;
import entidades.Post;
import entidades.Usuario;
import enums.TipoPost;
import facade.FacadePost;
import facade.FacadeUsuario;
import facade.IFacadePost;
import facade.IFacadeUsuario;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.itson.apps.playpostdto.ComentarioDTO;
import org.itson.apps.playpostdto.PostDTO;

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
                postAgregar.getContenido(),
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
     * @param postDTO Publicación con los datos actualizados.
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
        // Actualización del post en el sistema
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
        // Conversión de DTO a entidad Post
        Post post = facadePost.buscarPostPorID(postAnclar.getId());
        if (post != null) {
            post.setTipo(TipoPost.ANCLADO); // Anclar el post

            // Actualización del post
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
        // Conversión de DTO a entidad Post
        Post post = facadePost.buscarPostPorID(postEliminar.getId());
        if (post != null) {
            // Eliminar el post
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
