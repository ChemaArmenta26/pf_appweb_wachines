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
public class PostBO implements IPostBO{
    IFacadePost facadePost;
    
    public PostBO(){
        facadePost = new FacadePost();
    }
    
    /**
     * Agrega una nueva publicación al sistema.
     *
     * @param postDTO Publicación a agregar.
     * @return Publicación agregada.
     */
    @Override
    public PostDTO agregarPost(PostDTO postDTO) {
        IFacadeUsuario fUsuario = new FacadeUsuario();
        Usuario usuario = fUsuario.buscarUsuarioPorID(postDTO.getUsuario().getId());
        
        Post post = new Post(postDTO.getFechaHoraCreacion(), postDTO.getTitulo(), postDTO.getContenido(), usuario, postDTO.getTipo(), postDTO.getImagenData());
        
        Post postAgregado = facadePost.agregarPost(post);
        PostDTO postAgregadoDTO = new PostDTO();
        postAgregadoDTO.setTitulo(postAgregado.getTitulo());
        postAgregadoDTO.setContenido(postAgregado.getContenido());
        postAgregadoDTO.setFechaHoraCreacion(postAgregado.getFechaHoraCreacion());
        postAgregadoDTO.setId(postAgregado.getId());
        postAgregadoDTO.setImagenData(postAgregado.getImagenData());
        List<ComentarioDTO> comentariosDTO = new ArrayList<>();
        for (Comentario comentario : postAgregado.getComentarios()) {
            ComentarioDTO comentarioDTO = new ComentarioDTO();
            comentarioDTO.setContenido(comentario.getContenido());
            comentarioDTO.setFechaHora(comentario.getFechaHora());
            comentariosDTO.add(comentarioDTO);
        }
        postAgregadoDTO.setComentarios(comentariosDTO);
        return postAgregadoDTO;
    }

    /**
     * Actualiza la información de una publicación existente.
     *
     * @param postDTO Publicación con los datos actualizados.
     * @return Publicación actualizada.
     */
    @Override
    public PostDTO actualizarPost(PostDTO postDTO) {
        Usuario usuario = new Usuario(postDTO.getUsuario().getNombreCompleto());
        
        Post post = facadePost.buscarPostPorID(postDTO.getId());
        post.setFechaHoraCreacion(postDTO.getFechaHoraCreacion());
        post.setTitulo(postDTO.getTitulo());
        post.setContenido(postDTO.getContenido());
        post.setUsuario(usuario);
        post.setTipo(postDTO.getTipo());
        post.setImagenData(postDTO.getImagenData());
        // Actualización del post en el sistema
        Post postActualizado = facadePost.actualizarPost(post);
        // Conversión de entidad Post a DTO
        PostDTO postActualizadoDTO = new PostDTO();
        postActualizadoDTO.setTitulo(postActualizado.getTitulo());
        postActualizadoDTO.setContenido(postActualizado.getContenido());
        postActualizadoDTO.setFechaHoraCreacion(postActualizado.getFechaHoraCreacion());
        postActualizadoDTO.setId(postActualizado.getId());
        postActualizadoDTO.setImagenData(postActualizado.getImagenData());
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
    }

    /**
     * Ancla una publicación.
     *
     * @param postDTO Publicación a anclar.
     * @return Publicación anclada.
     */
    @Override
    public PostDTO anclarPost(PostDTO postDTO) {
        // Conversión de DTO a entidad Post
        Post post = facadePost.buscarPostPorID(postDTO.getId());
        if (post != null) {
            post.setTipo(TipoPost.ANCLADO); // Anclar el post
            
            // Actualización del post
            Post postAnclado = facadePost.actualizarPost(post);
            
            // Conversión de la entidad Post a DTO
            PostDTO postAncladoDTO = new PostDTO();
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
        // Conversión de DTO a entidad Post
        Post post = facadePost.buscarPostPorID(postDTO.getId());
        if (post != null) {
            // Eliminar el post
            Post postEliminado = facadePost.eliminarPost(post);
            
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
        Post post = facadePost.buscarPostPorID(id);
        if (post != null) {
            // Conversión de entidad Post a DTO
            PostDTO postDTO = new PostDTO();
            
            postDTO.setId(post.getId());
            postDTO.setTipo(post.getTipo());
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
        return null;
    }
    
    /**
     * Consulta todas las publicaciones subidas
     * @return Lista de todas las publicaciones subidas
     */
    @Override
    public List<PostDTO> consultarTodosLosPosts() {
        List<Post> posts = facadePost.consultarTodosLosPosts();
        List<PostDTO> postsDTO  = new ArrayList<>();
        
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                PostDTO postDTO = new PostDTO();

                postDTO.setId(post.getId());
                postDTO.setTipo(post.getTipo());
                postDTO.setTitulo(post.getTitulo());
                postDTO.setContenido(post.getContenido());
                postDTO.setFechaHoraCreacion(post.getFechaHoraCreacion());
                postDTO.setImagenData(post.getImagenData());

                // Procesar los comentarios
                List<ComentarioDTO> comentariosDTO = new ArrayList<>();
                for (Comentario comentario : post.getComentarios()) {
                    ComentarioDTO comentarioDTO = new ComentarioDTO();
                    comentarioDTO.setContenido(comentario.getContenido());
                    comentarioDTO.setFechaHora(comentario.getFechaHora());
                    comentariosDTO.add(comentarioDTO);
                }
                postDTO.setComentarios(comentariosDTO);
                postsDTO.add(postDTO);
            }
        }
        return postsDTO;
    }
}
