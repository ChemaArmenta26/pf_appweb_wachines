/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpostdao.daos;

import com.mycompany.playpostdao.conexion.Conexion;
import com.mycompany.playpostdao.entidades.Usuario;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author PC
 */
public class UsuarioDAO implements IUsuarioDAO {

    private EntityManager entityManager;

    public UsuarioDAO() throws PersistenciaException {
        this.entityManager = Conexion.getInstance().crearConexion();
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(usuario);
            transaction.commit();
            return usuario;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Usuario actualizado = entityManager.merge(usuario);
            transaction.commit();
            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario eliminarUsuario(Usuario usuario) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Usuario referencia = entityManager.find(Usuario.class, usuario.getId());
            if (referencia != null) {
                entityManager.remove(referencia);
                transaction.commit();
            }
            return referencia;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarUsuarioPorID(int id) throws PersistenciaException {
        try {
            return entityManager.find(Usuario.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar usuario por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> consultarTodosLosUsuarios() throws PersistenciaException {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar todos los usuarios: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
