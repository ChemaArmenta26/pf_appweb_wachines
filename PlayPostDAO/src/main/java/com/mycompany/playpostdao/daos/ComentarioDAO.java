/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpostdao.daos;

import com.mycompany.playpostdao.conexion.Conexion;
import com.mycompany.playpostdao.entidades.Comentario;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManager;

/**
 *
 * @author PC
 */
public class ComentarioDAO implements IComentarioDAO {

    private EntityManager entityManager;

    public ComentarioDAO() throws PersistenciaException {
        this.entityManager = Conexion.getInstance().crearConexion();
    }

    @Override
    public Comentario agregarComentario(Comentario comentario) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(comentario);
            transaction.commit();
            return comentario;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar comentario: " + e.getMessage(), e);
        }
    }

    @Override
    public Comentario agregarComentarioAUnComentario(Comentario comentario, Comentario comentarioNuevo) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            comentarioNuevo.setComentarioMayor(comentario); 
            entityManager.persist(comentarioNuevo);
            transaction.commit();
            return comentarioNuevo;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar comentario a otro comentario: " + e.getMessage(), e);
        }
    }

    @Override
    public Comentario actualizarComentario(Comentario comentario) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Comentario actualizado = entityManager.merge(comentario);
            transaction.commit();
            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al actualizar comentario: " + e.getMessage(), e);
        }
    }

    @Override
    public Comentario eliminarComentario(Comentario comentario) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Comentario referencia = entityManager.find(Comentario.class, comentario.getId());
            if (referencia != null) {
                entityManager.remove(referencia);
                transaction.commit();
            }
            return referencia;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al eliminar comentario: " + e.getMessage(), e);
        }
    }

    @Override
    public Comentario buscarComentarioPorID(int id) throws PersistenciaException {
        try {
            return entityManager.find(Comentario.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar comentario por ID: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
