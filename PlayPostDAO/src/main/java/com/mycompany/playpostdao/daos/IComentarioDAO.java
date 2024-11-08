/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.playpostdao.daos;

import com.mycompany.playpostdao.entidades.Comentario;
import com.mycompany.playpostdao.excepciones.PersistenciaException;

/**
 *
 * @author PC
 */
public interface IComentarioDAO {

    public Comentario agregarComentario(Comentario comentario) throws PersistenciaException;

    public Comentario agregarComentarioAUnComentario(Comentario comentario, Comentario comentarioNuevo) throws PersistenciaException;

    public Comentario actualizarComentario(Comentario comentario) throws PersistenciaException;

    public Comentario eliminarComentario(Comentario comentario) throws PersistenciaException;

    public Comentario buscarComentarioPorID(int id) throws PersistenciaException;
}