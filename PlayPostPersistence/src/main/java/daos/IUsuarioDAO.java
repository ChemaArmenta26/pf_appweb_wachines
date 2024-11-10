/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import entidades.Estado;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author PC
 */
public interface IUsuarioDAO {

    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario actualizarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario eliminarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario buscarUsuarioPorID(Long id) throws PersistenciaException;
    
    public List<Usuario> consultarTodosLosUsuarios() throws PersistenciaException;
    
    public Usuario buscarUsuarioPorCorreoYContrasena(String correo, String contrasena) throws PersistenciaException;


}
