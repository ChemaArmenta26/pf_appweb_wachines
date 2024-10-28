/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.playpost.daos;

import com.mycompany.playpost.entidades.Estado;
import com.mycompany.playpost.entidades.Usuario;
import com.mycompany.playpost.excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author PC
 */
public interface IUsuarioDAO {

    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario actualizarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario eliminarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario buscarUsuarioPorID(int id) throws PersistenciaException;
    
    public List<Usuario> consultarTodosLosUsuarios() throws PersistenciaException;


}
