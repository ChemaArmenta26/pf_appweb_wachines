/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpostobjetosnegocio.BOs;

import auxiliar.Encriptar;
import entidades.Estado;
import entidades.Municipio;
import entidades.Usuario;
import facade.FacadeUsuario;
import facade.IFacadeUsuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoseH
 */
public class UsuarioBO implements IUsuarioBO {

    IFacadeUsuario facadeUsuario;
    Encriptar encriptador;

    public UsuarioBO() {
        facadeUsuario = new FacadeUsuario();
        encriptador = new Encriptar();
    }

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param usuarioAgregar Usuario a agregar.
     * @return Usuario agregado.
     */
    @Override
    public Usuario agregarUsuario(Usuario usuarioAgregar) {
        try {
            Estado estado = new Estado(usuarioAgregar.getMunicipio().getEstado().getNombre());
            Municipio municipio = new Municipio(usuarioAgregar.getMunicipio().getNombre(), estado);
            Usuario usuario = new Usuario(usuarioAgregar.getNombreCompleto(), usuarioAgregar.getCorreo(), encriptador.encriptar(usuarioAgregar.getContrasenia()), usuarioAgregar.getTelefono(), usuarioAgregar.getCiudad(), usuarioAgregar.getFechaNacimiento(), usuarioAgregar.getGenero(), municipio, usuarioAgregar.getTipo());
            Usuario usuarioAgregado = facadeUsuario.agregarUsuario(usuario);
            return usuarioAgregado;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuarioActualizar Usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    @Override
    public Usuario actualizarUsuario(Usuario usuarioActualizar) {
        try {
            String avatarActual = usuarioActualizar.getAvatar();
            System.out.println("UsuarioBO - Avatar antes de actualizar: " + avatarActual);

            Estado estado = new Estado(usuarioActualizar.getMunicipio().getEstado().getNombre());
            Municipio municipio = new Municipio(usuarioActualizar.getMunicipio().getNombre(), estado);

            Usuario usuario = facadeUsuario.buscarUsuarioPorID(usuarioActualizar.getId());
            usuario.setNombreCompleto(usuarioActualizar.getNombreCompleto());
            usuario.setCorreo(usuarioActualizar.getCorreo());
            usuario.setContrasenia(usuarioActualizar.getContrasenia());
            usuario.setTelefono(usuarioActualizar.getTelefono());
            usuario.setCiudad(usuarioActualizar.getCiudad());
            usuario.setFechaNacimiento(usuarioActualizar.getFechaNacimiento());
            usuario.setGenero(usuarioActualizar.getGenero());
            usuario.setMunicipio(municipio);
            usuario.setTipo(usuarioActualizar.getTipo());

            if (avatarActual == null && usuario != null) {
                usuarioActualizar.setAvatar(usuario.getAvatar());
            }

            Usuario usuarioActualizado = facadeUsuario.actualizarUsuario(usuarioActualizar);

            // Si se perdió el avatar durante la actualización, volverlo a poner
            if (usuarioActualizado != null && usuarioActualizado.getAvatar() == null && avatarActual != null) {
                usuarioActualizado.setAvatar(avatarActual);
                usuarioActualizado = facadeUsuario.actualizarUsuario(usuarioActualizado);
            }

            System.out.println("UsuarioBO - Avatar después de actualizar: "
                    + (usuarioActualizado != null ? usuarioActualizado.getAvatar() : "null"));
            return usuarioActualizado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param usuarioEliminar Usuario a eliminar.
     * @return Usuario eliminado.
     */
    @Override
    public Usuario eliminarUsuario(Usuario usuarioEliminar) {
        Usuario usuario = facadeUsuario.buscarUsuarioPorID(usuarioEliminar.getId());
        Usuario usuarioEliminado = facadeUsuario.eliminarUsuario(usuario);
        return usuarioEliminado;
    }

    /**
     * Busca un usuario específico por su identificador único.
     *
     * @param ID Identificador del usuario a buscar.
     * @return Usuario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public Usuario buscarUsuarioPorID(Long ID) {
        Usuario usuarioEncontrado = facadeUsuario.buscarUsuarioPorID(ID);
        return usuarioEncontrado;
    }

    /**
     * Consulta todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    @Override
    public List<Usuario> consultarTodosLosUsuarios() {
        List<Usuario> usuariosEncontrados = facadeUsuario.consultarTodosLosUsuarios();
        return usuariosEncontrados;
    }

    @Override
    public Usuario buscarUsuarioPorCorreoYContrasena(String correo, String contrasena) {
        try {
            Usuario usuarioBuscar = facadeUsuario.buscarUsuarioPorCorreoYContrasena(correo, encriptador.encriptar(contrasena));
            return usuarioBuscar;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean existeCorreo(String correo) {
        try {
            return facadeUsuario.existeCorreo(correo);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
