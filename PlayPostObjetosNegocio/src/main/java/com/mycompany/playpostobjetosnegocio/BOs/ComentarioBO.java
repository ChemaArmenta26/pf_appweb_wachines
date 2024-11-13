package com.mycompany.playpostobjetosnegocio.BOs;

import entidades.Comentario;
import entidades.Estado;
import entidades.Municipio;
import entidades.Post;
import entidades.Usuario;
import facade.FacadeComentario;
import facade.FacadePost;
import facade.FacadeUsuario;
import facade.IFacadeComentario;
import facade.IFacadePost;
import facade.IFacadeUsuario;
import java.util.ArrayList;
import java.util.List;
import org.itson.apps.playpostdto.ComentarioDTO;
import org.itson.apps.playpostdto.EstadoDTO;
import org.itson.apps.playpostdto.MunicipioDTO;
import org.itson.apps.playpostdto.PostDTO;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 *
 * @author victo
 */
public class ComentarioBO implements IComentarioBO {

    IFacadeComentario facadeComentario;
    IFacadePost facadePost;
    IFacadeUsuario facadeUsuario;

    public ComentarioBO() {
        this.facadeComentario = new FacadeComentario();
        this.facadePost = new FacadePost();
        this.facadeUsuario = new FacadeUsuario();
    }

    @Override
    public Comentario agregarComentario(Comentario comentario) {
        Post post = facadePost.buscarPostPorID(comentario.getPost().getId());
        Usuario usuario = facadeUsuario.buscarUsuarioPorID(comentario.getUsuario().getId());

        Comentario comentarioAgregado = new Comentario(comentario.getFechaHora(), comentario.getContenido(), usuario, post);
        facadeComentario.agregarComentario(comentarioAgregado);

        return comentarioAgregado;
    }

    @Override
    public Comentario agregarComentarioAUnComentario(Comentario comentario, Comentario comentarioNuevo) {

        Comentario comentarioMayor = facadeComentario.buscarComentarioPorID(comentario.getId());

        Post postNuevo = facadePost.buscarPostPorID(comentarioNuevo.getPost().getId());
        Usuario usuarioNuevo = facadeUsuario.buscarUsuarioPorID(comentarioNuevo.getUsuario().getId());

        Comentario comentarioHijo = new Comentario(
                comentarioNuevo.getFechaHora(),
                comentarioNuevo.getContenido(),
                usuarioNuevo,
                postNuevo
        );
        comentarioHijo.setComentarioMayor(comentarioMayor);

        facadeComentario.agregarComentario(comentarioHijo);


        return comentarioHijo;
    }

    @Override
    public Comentario actualizarComentario(Comentario comentarioDTO) {
        Comentario comentarioExistente = facadeComentario.buscarComentarioPorID(comentarioDTO.getId());

        comentarioExistente.setContenido(comentarioDTO.getContenido());
        comentarioExistente.setFechaHora(comentarioDTO.getFechaHora());

        facadeComentario.actualizarComentario(comentarioExistente);

        return comentarioExistente;
    }

    @Override
    public Comentario eliminarComentario(Comentario comentarioDTO) {
        Comentario comentarioAEliminar = facadeComentario.buscarComentarioPorID(comentarioDTO.getId());

        facadeComentario.eliminarComentario(comentarioAEliminar);
        return comentarioAEliminar;
    }

    @Override
    public Comentario buscarComentarioPorID(Long id) {

        Comentario comentario = facadeComentario.buscarComentarioPorID(id);
        return comentario;
    }

}
