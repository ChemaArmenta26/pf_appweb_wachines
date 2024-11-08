package daos;

import conexion.Conexion;
import entidades.Estado;
import entidades.Municipio;
import excepciones.PersistenciaException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EstadoDAO implements IEstadoDAO {

    private EntityManager entityManager;

    public EstadoDAO() throws PersistenciaException {
        this.entityManager = Conexion.getInstance().crearConexion();
    }

    @Override
    public Estado agregarEstado(Estado estado) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(estado);
            transaction.commit();
            return estado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar estado: " + e.getMessage(), e);
        }
    }

    @Override
    public Estado agregarMunicipioAEstado(Estado estado, Municipio municipio) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            estado.getMunicipios().add(municipio);
            municipio.setEstado(estado);
            entityManager.merge(estado);
            entityManager.persist(municipio);
            transaction.commit();
            return estado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar municipio a estado: " + e.getMessage(), e);
        }
    }

    @Override
    public Estado actualizarEstado(Estado estado) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Estado actualizado = entityManager.merge(estado);
            transaction.commit();
            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al actualizar estado: " + e.getMessage(), e);
        }
    }

    @Override
    public Estado eliminarEstado(Estado estado) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Estado referencia = entityManager.find(Estado.class, estado.getId());
            if (referencia != null) {
                entityManager.remove(referencia);
                transaction.commit();
            }
            return referencia;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al eliminar estado: " + e.getMessage(), e);
        }
    }

    @Override
    public Estado buscarEstadoPorID(int id) throws PersistenciaException {
        try {
            return entityManager.find(Estado.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar estado por ID: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
