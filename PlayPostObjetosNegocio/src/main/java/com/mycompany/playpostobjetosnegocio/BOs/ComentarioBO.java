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
        estadoDTO.setNombre(usuario.getMunicipio().getEstado().getNombre());

        MunicipioDTO municipioDTO = new MunicipioDTO();
        municipioDTO.setNombre(comentario.getUsuario().getMunicipio().getNombre());
        municipioDTO.setEstado(estadoDTO);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
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

        ComentarioDTO comentarioHijoDTO = new ComentarioDTO();
        comentarioHijoDTO.setContenido(comentarioHijo.getContenido());
        comentarioHijoDTO.setFechaHora(comentarioHijo.getFechaHora());

        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo(comentarioHijo.getPost().getTitulo());
        postDTO.setContenido(comentarioHijo.getPost().getContenido());
        postDTO.setFechaHoraCreacion(comentarioHijo.getPost().getFechaHoraCreacion());
        postDTO.setId(comentarioHijo.getPost().getId());
        postDTO.setImagenData(comentarioHijo.getPost().getImagenData());

        List<ComentarioDTO> comentariosDTO = new ArrayList<>();
        Post postConComentarios = facadePost.buscarPostPorID(comentarioHijo.getPost().getId());
        List<Comentario> comentarios = postConComentarios.getComentarios();
        for (Comentario coment : comentarios) {
            ComentarioDTO comentarioDTO = new ComentarioDTO();
            comentarioDTO.setContenido(coment.getContenido());
            comentarioDTO.setFechaHora(coment.getFechaHora());
            comentariosDTO.add(comentarioDTO);
        }
        postDTO.setComentarios(comentariosDTO);

        comentarioHijoDTO.setPost(postDTO);

        EstadoDTO estadoDTO = new EstadoDTO();
        estadoDTO.setNombre(comentarioNuevo.getUsuario().getMunicipio().getEstado().getNombre());

        MunicipioDTO municipioDTO = new MunicipioDTO();
        municipioDTO.setNombre(comentarioNuevo.getUsuario().getMunicipio().getNombre());
        municipioDTO.setEstado(estadoDTO);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreCompleto(comentarioHijo.getUsuario().getNombreCompleto());
        usuarioDTO.setCorreo(comentarioHijo.getUsuario().getCorreo());
        usuarioDTO.setContrasenia(comentarioHijo.getUsuario().getContrasenia());
        usuarioDTO.setTelefono(comentarioHijo.getUsuario().getTelefono());
        usuarioDTO.setCiudad(comentarioHijo.getUsuario().getCiudad());
        usuarioDTO.setFechaNacimiento(comentarioHijo.getUsuario().getFechaNacimiento());
        usuarioDTO.setGenero(comentarioHijo.getUsuario().getGenero());
        usuarioDTO.setMunicipio(municipioDTO);
        usuarioDTO.setTipo(usuarioDTO.getTipo());

        comentarioHijoDTO.setUsuario(usuarioDTO);

        return comentarioHijoDTO;
    }

    @Override
    public ComentarioDTO actualizarComentario(ComentarioDTO comentarioDTO) {
        Comentario comentarioExistente = facadeComentario.buscarComentarioPorID(comentarioDTO.getId());

        comentarioExistente.setContenido(comentarioDTO.getContenido());
        comentarioExistente.setFechaHora(comentarioDTO.getFechaHora());

        facadeComentario.actualizarComentario(comentarioExistente);

        ComentarioDTO comentarioActualizadoDTO = new ComentarioDTO();
        comentarioActualizadoDTO.setId(comentarioExistente.getId());
        comentarioActualizadoDTO.setContenido(comentarioExistente.getContenido());
        comentarioActualizadoDTO.setFechaHora(comentarioExistente.getFechaHora());

        return comentarioActualizadoDTO;
    }

    @Override
    public ComentarioDTO eliminarComentario(ComentarioDTO comentarioDTO) {
        Comentario comentarioAEliminar = facadeComentario.buscarComentarioPorID(comentarioDTO.getId());

        facadeComentario.eliminarComentario(comentarioAEliminar);

        ComentarioDTO comentarioEliminadoDTO = new ComentarioDTO();
        comentarioEliminadoDTO.setId(comentarioAEliminar.getId());
        comentarioEliminadoDTO.setContenido(comentarioAEliminar.getContenido());
        comentarioEliminadoDTO.setFechaHora(comentarioAEliminar.getFechaHora());

        return comentarioEliminadoDTO;
    }

    @Override
    public ComentarioDTO buscarComentarioPorID(Long id) {

        Comentario comentario = facadeComentario.buscarComentarioPorID(id);

        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setContenido(comentario.getContenido());
        comentarioDTO.setFechaHora(comentario.getFechaHora());

        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo(comentario.getPost().getTitulo());
        postDTO.setContenido(comentario.getPost().getContenido());
        postDTO.setFechaHoraCreacion(comentario.getPost().getFechaHoraCreacion());
        postDTO.setId(comentario.getPost().getId());
        postDTO.setImagenData(comentario.getPost().getImagenData());
        List<ComentarioDTO> comentariosDTO = new ArrayList<>();
        Post postConComentario = facadePost.buscarPostPorID(comentario.getPost().getId());
        List<Comentario> comentarios = postConComentario.getComentarios();
        for (Comentario coment : comentarios) {
            ComentarioDTO comentarioPostDTO = new ComentarioDTO();
            comentarioPostDTO.setContenido(coment.getContenido());
            comentarioPostDTO.setFechaHora(coment.getFechaHora());
            comentariosDTO.add(comentarioDTO);
        }
        postDTO.setComentarios(comentariosDTO);

        comentarioDTO.setPost(postDTO);

        EstadoDTO estadoDTO = new EstadoDTO();
        estadoDTO.setNombre(comentario.getUsuario().getMunicipio().getEstado().getNombre());

        MunicipioDTO municipioDTO = new MunicipioDTO();
        municipioDTO.setNombre(comentario.getUsuario().getMunicipio().getNombre());
        municipioDTO.setEstado(estadoDTO);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreCompleto(comentario.getUsuario().getNombreCompleto());
        usuarioDTO.setCorreo(comentario.getUsuario().getCorreo());
        usuarioDTO.setContrasenia(comentario.getUsuario().getContrasenia());
        usuarioDTO.setTelefono(comentario.getUsuario().getTelefono());
        usuarioDTO.setCiudad(comentario.getUsuario().getCiudad());
        usuarioDTO.setFechaNacimiento(comentario.getUsuario().getFechaNacimiento());
        usuarioDTO.setGenero(comentario.getUsuario().getGenero());
        usuarioDTO.setMunicipio(municipioDTO);
        usuarioDTO.setTipo(usuarioDTO.getTipo());

        comentarioDTO.setUsuario(usuarioDTO);

        return comentarioDTO;
    }

}
