package facade;

import com.mycompany.playpostdao.daos.MunicipioDAO;
import com.mycompany.playpostdao.entidades.Municipio;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import factoryMethod.FactoryMunicipioDAO;
import factoryMethod.IFactoryDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa IFacadeMunicipio para utilizar los métodos de acceso a
 * datos.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public class FacadeMunicipio implements IFacadeMunicipio {

    IFactoryDAO<MunicipioDAO> factory;

    /**
     * Método constructor. Permite inicializar la FactoryDAO para crear la
     * instancia necesaria.
     */
    public FacadeMunicipio() {
        factory = new FactoryMunicipioDAO();
    }

    /**
     * Agrega un nuevo municipio al sistema.
     *
     * @param municipio Municipio a agregar.
     * @return Municipio agregado.
     */
    @Override
    public Municipio agregarMunicipio(Municipio municipio) {
        try {
            return factory.crearDAO().agregarMunicipio(municipio);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información de un municipio existente.
     *
     * @param municipio Municipio con los datos actualizados.
     * @return Municipio actualizado.
     */
    @Override
    public Municipio actualizarMunicipio(Municipio municipio) {
        try {
            return factory.crearDAO().actualizarMunicipio(municipio);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina un municipio del sistema.
     *
     * @param municipio Municipio a eliminar.
     * @return Municipio eliminado.
     */
    @Override
    public Municipio eliminarMunicipio(Municipio municipio) {
        try {
            return factory.crearDAO().eliminarMunicipio(municipio);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca un municipio específico por su identificador único.
     *
     * @param id Id del municipio a buscar.
     * @return Municipio que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    @Override
    public Municipio buscarMunicipioPorID(int id) {
        try {
            return factory.crearDAO().buscarMunicipioPorID(id);
        } catch (PersistenciaException ex) {
            Logger.getLogger(FacadeComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
