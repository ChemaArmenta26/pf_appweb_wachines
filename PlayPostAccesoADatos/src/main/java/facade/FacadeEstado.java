package facade;

import com.mycompany.playpostdao.daos.EstadoDAO;
import com.mycompany.playpostdao.entidades.Estado;
import com.mycompany.playpostdao.entidades.Municipio;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import factoryMethod.FactoryEstadoDAO;
import factoryMethod.IFactoryDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa IFacadeEstado para utilizar los métodos de acceso a
 * datos.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public class FacadeEstado implements IFacadeEstado {

    IFactoryDAO<EstadoDAO> factory;

    /**
     * Método constructor. Permite inicializar la FactoryDAO para crear la
     * instancia necesaria.
     */
    public FacadeEstado() {
        factory = new FactoryEstadoDAO();
    }

    /**
     * Agrega un nuevo estado al sistema.
     *
     * @param estado Estado a agregar.
     * @return Estado agregado.
     */
    @Override
    public Estado agregarEstado(Estado estado) {
        try {
            return factory.crearDAO().agregarEstado(estado);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Agrega un municipio a un estado existente.
     *
     * @param estado Estado al que se le agregará un municipio.
     * @param municipio Municipio a agregar.
     * @return Estado con el nuevo municipio asociado.
     */
    @Override
    public Estado agregarMunicipioAEstado(Estado estado, Municipio municipio) {
        try {
            return factory.crearDAO().agregarMunicipioAEstado(estado, municipio);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de un estado existente.
     *
     * @param estado Estado con los datos actualizados.
     * @return Estado actualizado.
     */
    @Override
    public Estado actualizarEstado(Estado estado) {
        try {
            return factory.crearDAO().actualizarEstado(estado);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina un estado del sistema.
     *
     * @param estado Estado a eliminar.
     * @return Estado eliminado.
     */
    @Override
    public Estado eliminarEstado(Estado estado) {
        try {
            return factory.crearDAO().eliminarEstado(estado);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca un estado específico por su identificador único.
     *
     * @param id Id del estado a buscar.
     * @return Estado que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public Estado buscarEstadoPorID(int id) {
        try {
            return factory.crearDAO().buscarEstadoPorID(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
