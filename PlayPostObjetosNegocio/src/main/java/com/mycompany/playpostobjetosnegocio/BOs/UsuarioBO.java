/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpostobjetosnegocio.BOs;


import entidades.Estado;
import entidades.Municipio;
import entidades.Usuario;
import enums.TipoUsuario;
import facade.FacadeComentario;
import facade.FacadeUsuario;
import facade.IFacadeUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 *
 * @author JoseH
 */
public class UsuarioBO implements IUsuarioBO{
    IFacadeUsuario facadeUsuario;
    
    
    public UsuarioBO(){
        facadeUsuario = new FacadeUsuario();
    }
    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param usuarioDTO Usuario a agregar.
     * @return Usuario agregado.
     */
    @Override
    public UsuarioDTO agregarUsuario(UsuarioDTO usuarioDTO){
        Estado estado = new Estado(usuarioDTO.getMunicipio().getEstado().getNombre());
        Municipio municipio = new Municipio(usuarioDTO.getMunicipio().getNombre(), estado);
        Usuario usuario = new Usuario(usuarioDTO.getNombreCompleto(), usuarioDTO.getCorreo(), usuarioDTO.getContrasenia(), usuarioDTO.getTelefono(), usuarioDTO.getCiudad(), usuarioDTO.getFechaNacimiento(), usuarioDTO.getGenero(), municipio, usuarioDTO.getTipo());
        Usuario usuarioAgregado = facadeUsuario.agregarUsuario(usuario);
        UsuarioDTO usuarioAgregadoDTO = new UsuarioDTO();
        usuarioAgregadoDTO.setNombreCompleto(usuarioAgregado.getNombreCompleto());
        return usuarioAgregadoDTO;
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuarioDTO Usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    @Override
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO){
        Estado estado = new Estado(usuarioDTO.getMunicipio().getEstado().getNombre());
        Municipio municipio = new Municipio(usuarioDTO.getMunicipio().getNombre(), estado);
        
        Usuario usuario = facadeUsuario.buscarUsuarioPorID(usuarioDTO.getId());
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setCiudad(usuarioDTO.getCiudad());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setGenero(usuarioDTO.getGenero());
        usuario.setMunicipio(municipio);
        usuario.setTipo(usuarioDTO.getTipo());
        Usuario usuarioActualizado =  facadeUsuario.actualizarUsuario(usuario);
        UsuarioDTO usuarioActualizadoDTO = new UsuarioDTO();
        usuarioActualizadoDTO.setNombreCompleto(usuarioActualizado.getNombreCompleto());
        return usuarioActualizadoDTO;
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param usuarioDTO Usuario a eliminar.
     * @return Usuario eliminado.
     */
    @Override
    public UsuarioDTO eliminarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = facadeUsuario.buscarUsuarioPorID(usuarioDTO.getId());
        Usuario usuarioEliminado =  facadeUsuario.eliminarUsuario(usuario);
        UsuarioDTO usuarioEliminadoDTO = new UsuarioDTO();
        usuarioEliminadoDTO.setNombreCompleto(usuarioEliminado.getNombreCompleto());
        return usuarioEliminadoDTO;
    }

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param ID Identificador del usuario a buscar.
     * @return Usuario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public UsuarioDTO buscarUsuarioPorID(Long ID){
        Usuario usuarioEncontrado = facadeUsuario.buscarUsuarioPorID(ID);
        UsuarioDTO usuarioEncontradoDTO = new UsuarioDTO();
        usuarioEncontradoDTO.setNombreCompleto(usuarioEncontrado.getNombreCompleto());
        usuarioEncontradoDTO.setId(usuarioEncontrado.getId());
        return usuarioEncontradoDTO;
    }

    /**
     * Consulta todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    @Override
    public List<UsuarioDTO> consultarTodosLosUsuarios(){
        List<Usuario> usuariosEncontrados = facadeUsuario.consultarTodosLosUsuarios();
        List<UsuarioDTO> usuariosEncontradosDTO = new ArrayList<>();
        for(Usuario usuario : usuariosEncontrados){
            UsuarioDTO usuarioEncontradoDTO = new UsuarioDTO();
            usuarioEncontradoDTO.setNombreCompleto(usuario.getNombreCompleto());
            usuariosEncontradosDTO.add(usuarioEncontradoDTO);
        }
        return usuariosEncontradosDTO;
    }
}
