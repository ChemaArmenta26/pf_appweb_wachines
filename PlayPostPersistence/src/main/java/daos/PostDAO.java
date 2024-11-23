package daos;

import conexion.Conexion;
import entidades.Post;
import enums.TipoPost;
import excepciones.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class PostDAO implements IPostDAO {

    private EntityManager entityManager;

    public PostDAO() throws PersistenciaException {
        this.entityManager = Conexion.getInstance().crearConexion();
    }

    @Override
    public Post agregarPost(Post post) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(post);
            transaction.commit();
            return post;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar post: " + e.getMessage(), e);
        }
    }

    @Override
    public Post actualizarPost(Post post) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Post actualizado = entityManager.merge(post);
            transaction.commit();
            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al actualizar post: " + e.getMessage(), e);
        }
    }

    @Override
    public Post eliminarPost(Post post) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Post referencia = entityManager.find(Post.class, post.getId());
            if (referencia != null) {
                entityManager.remove(referencia);
                transaction.commit();
            }
            return referencia;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al eliminar post: " + e.getMessage(), e);
        }
    }

    @Override
    public Post buscarPostPorID(Long id) throws PersistenciaException {
        try {
            return entityManager.find(Post.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar post por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Post anclarPost(Post post) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            post.setTipo(TipoPost.ANCLADO);
            Post actualizado = entityManager.merge(post);
            transaction.commit();
            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al anclar post: " + e.getMessage(), e);
        }
    }
    
    
    @Override
    public List<Post> consultarTodosLosPosts() throws PersistenciaException{
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p", Post.class);
            return query.getResultList();
        } catch (Exception e){
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error consultar los posts " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
