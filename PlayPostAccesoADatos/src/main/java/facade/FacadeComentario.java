package facade;

import daos.ComentarioDAO;
import entidades.Comentario;
import excepciones.PersistenciaException;
import factoryMethod.FactoryComentarioDAO;
import factoryMethod.IFactoryDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa IFacadeComentario para utilizar los métodos de acceso a
 * datos.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public class FacadeComentario implements IFacadeComentario {

    IFactoryDAO<ComentarioDAO> factory;

    /**
     * Método constructor. Permite inicializar la FactoryDAO para crear la
     * instancia necesaria.
     */
    public FacadeComentario() {
        factory = new FactoryComentarioDAO();
    }

    /**
     * Agrega un nuevo comentario al sistema.
     *
     * @param comentario Comentario a agregar.
     * @return Comentario agregado.
     */
    @Override
    public Comentario agregarComentario(Comentario comentario) {
        try {
            return factory.crearDAO().agregarComentario(comentario);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Agrega una respuesta a un comentario existente.
     *
     * @param comentario Comentario al que se le agregará una respuesta.
     * @param comentarioNuevo comentario de respuesta a agregar.
     * @return Comentario principal con la nueva respuesta asociada.
     */
    @Override
    public Comentario agregarComentarioAUnComentario(Comentario comentario, Comentario comentarioNuevo) {
        try {
            return factory.crearDAO().agregarComentarioAUnComentario(comentario, comentarioNuevo);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de un comentario existente.
     *
     * @param comentario Comentario con los nuevos datos a actualizar.
     * @return Comentario actualizado.
     */
    @Override
    public Comentario actualizarComentario(Comentario comentario) {
        try {
            return factory.crearDAO().actualizarComentario(comentario);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina un comentario del sistema.
     *
     * @param comentario Comentario a eliminar.
     * @return Comentario eliminado.
     */
    @Override
    public Comentario eliminarComentario(Comentario comentario) {
        try {
            return factory.crearDAO().eliminarComentario(comentario);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca un comentario específico por su identificador único.
     *
     * @param id Id del comentario a buscar.
     * @return Comentario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public Comentario buscarComentarioPorID(int id) {
        try {
            return factory.crearDAO().buscarComentarioPorID(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
