/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.Conexion;
import entidades.Usuario;
import excepciones.PersistenciaException;
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

            Usuario usuarioExistente = entityManager.find(Usuario.class, usuario.getId());
            if (usuarioExistente != null) {
                // Preservar el avatar si no se está actualizando a uno nuevo
                if (usuario.getAvatar() == null && usuarioExistente.getAvatar() != null) {
                    usuario.setAvatar(usuarioExistente.getAvatar());
                }
            }

            System.out.println("DAO - Avatar antes del merge: " + usuario.getAvatar());
            Usuario actualizado = entityManager.merge(usuario);
            System.out.println("DAO - Avatar después del merge: " + actualizado.getAvatar());

            transaction.commit();

            entityManager.refresh(actualizado);

            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error en DAO actualizarUsuario: " + e.getMessage());
            e.printStackTrace();
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
    public Usuario buscarUsuarioPorID(Long id) throws PersistenciaException {
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

    /**
     * Busca un usuario en la base de datos por correo y contraseña.
     *
     * @param correo El correo del usuario.
     * @param contrasena La contraseña del usuario.
     * @return El usuario correspondiente si se encuentra y null en caso
     * contrario.
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
     */
    @Override
    public Usuario buscarUsuarioPorCorreoYContrasena(String correo, String contrasena) throws PersistenciaException {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :correo AND u.contrasenia = :contrasena",
                    Usuario.class
            );
            query.setParameter("correo", correo);
            query.setParameter("contrasena", contrasena);
            return query.getSingleResult(); // Devuelve el usuario si encuentra uno.
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar usuario por correo y contraseña: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
