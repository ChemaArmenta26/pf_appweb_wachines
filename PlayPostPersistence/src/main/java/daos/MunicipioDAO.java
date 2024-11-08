package daos;

import conexion.Conexion;
import entidades.Municipio;
import excepciones.PersistenciaException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MunicipioDAO implements IMunicipioDAO {

    private EntityManager entityManager;

    public MunicipioDAO() throws PersistenciaException {
        this.entityManager = Conexion.getInstance().crearConexion();
    }

    @Override
    public Municipio agregarMunicipio(Municipio municipio) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(municipio);
            transaction.commit();
            return municipio;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al agregar municipio: " + e.getMessage(), e);
        }
    }

    @Override
    public Municipio actualizarMunicipio(Municipio municipio) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Municipio actualizado = entityManager.merge(municipio);
            transaction.commit();
            return actualizado;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al actualizar municipio: " + e.getMessage(), e);
        }
    }

    @Override
    public Municipio eliminarMunicipio(Municipio municipio) throws PersistenciaException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Municipio referencia = entityManager.find(Municipio.class, municipio.getId());
            if (referencia != null) {
                entityManager.remove(referencia);
                transaction.commit();
            }
            return referencia;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaException("Error al eliminar municipio: " + e.getMessage(), e);
        }
    }

    @Override
    public Municipio buscarMunicipioPorID(int id) throws PersistenciaException {
        try {
            return entityManager.find(Municipio.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar municipio por ID: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
