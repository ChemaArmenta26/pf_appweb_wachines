
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
public class ComentarioBO implements IComentarioBO{

    IFacadeComentario facadeComentario;
    IFacadePost facadePost;
    IFacadeUsuario facadeUsuario;

    public ComentarioBO() {
        this.facadeComentario = new FacadeComentario();
        this.facadePost = new FacadePost();
        this.facadeUsuario = new FacadeUsuario();
    }
    
    @Override
    public ComentarioDTO agregarComentario(ComentarioDTO comentario) {
        Post post = facadePost.buscarPostPorID(comentario.getPost().getId());
        Usuario usuario = facadeUsuario.buscarUsuarioPorID(comentario.getUsuario().getId());

        Comentario comentarioAgregado = new Comentario(comentario.getFechaHora(), comentario.getContenido(), usuario, post);
        facadeComentario.agregarComentario(comentarioAgregado);
        
        ComentarioDTO comentarioAgregadoDTO = new ComentarioDTO();
        comentarioAgregadoDTO.setContenido(comentarioAgregado.getContenido());
        comentarioAgregadoDTO.setFechaHora(comentarioAgregado.getFechaHora());
        
        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo(comentarioAgregado.getPost().getTitulo());
        postDTO.setContenido(comentarioAgregado.getPost().getContenido());
        postDTO.setFechaHoraCreacion(comentarioAgregado.getPost().getFechaHoraCreacion());
        postDTO.setId(comentarioAgregado.getPost().getId());
        postDTO.setImagenData(comentarioAgregado.getPost().getImagenData());
        List<ComentarioDTO> comentariosDTO = new ArrayList<>();
        Post postConComentario = facadePost.buscarPostPorID(comentarioAgregado.getPost().getId());
        List<Comentario> comentarios = postConComentario.getComentarios();
        for (Comentario coment : comentarios) {
            ComentarioDTO comentarioDTO = new ComentarioDTO();
            comentarioDTO.setContenido(coment.getContenido());
            comentarioDTO.setFechaHora(coment.getFechaHora());
            comentariosDTO.add(comentarioDTO);
        }
        postDTO.setComentarios(comentariosDTO);
        
        comentarioAgregadoDTO.setPost(postDTO);
        
        EstadoDTO estadoDTO = new EstadoDTO();
        estadoDTO.setNombre(comentario.getUsuario().getMunicipio().getEstado().getNombre());
        
        MunicipioDTO municipioDTO = new MunicipioDTO();
        municipioDTO.setNombre(comentario.getUsuario().getMunicipio().getNombre());
        municipioDTO.setEstado(estadoDTO);
        
        UsuarioDTO usuarioDTO= new UsuarioDTO();
        usuarioDTO.setNombreCompleto(comentarioAgregado.getUsuario().getNombreCompleto());
        usuarioDTO.setCorreo(comentarioAgregado.getUsuario().getCorreo());
        usuarioDTO.setContrasenia(comentarioAgregado.getUsuario().getContrasenia());
        usuarioDTO.setTelefono(comentarioAgregado.getUsuario().getTelefono());
        usuarioDTO.setCiudad(comentarioAgregado.getUsuario().getCiudad());
        usuarioDTO.setFechaNacimiento(comentarioAgregado.getUsuario().getFechaNacimiento());
        usuarioDTO.setGenero(comentarioAgregado.getUsuario().getGenero());
        usuarioDTO.setMunicipio(municipioDTO);
        usuarioDTO.setTipo(usuarioDTO.getTipo());
        
        comentarioAgregadoDTO.setUsuario(usuarioDTO);
        
        return comentarioAgregadoDTO;
    }

    @Override
    public ComentarioDTO agregarComentarioAUnComentario(ComentarioDTO comentario, ComentarioDTO comentarioNuevo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ComentarioDTO actualizarComentario(ComentarioDTO comentario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ComentarioDTO eliminarComentario(ComentarioDTO comentario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ComentarioDTO buscarComentarioPorID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
