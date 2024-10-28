package facade;

import com.mycompany.playpostdao.entidades.Estado;
import com.mycompany.playpostdao.entidades.Municipio;

/**
 * Interfaz para la fachada de operaciones sobre entidades de tipo Estado.
 * 
 * @autor Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public interface IFacadeEstado {

    /**
     * Agrega un nuevo estado al sistema.
     *
     * @param estado Estado a agregar.
     * @return Estado agregado.
     */
    public Estado agregarEstado(Estado estado);

    /**
     * Agrega un municipio a un estado existente.
     *
     * @param estado Estado al que se le agregará un municipio.
     * @param municipio Municipio a agregar.
     * @return Estado con el nuevo municipio asociado.
     */
    public Estado agregarMunicipioAEstado(Estado estado, Municipio municipio);

    /**
     * Actualiza la información de un estado existente.
     *
     * @param estado Estado con los datos actualizados.
     * @return Estado actualizado.
     */
    public Estado actualizarEstado(Estado estado);

    /**
     * Elimina un estado del sistema.
     *
     * @param estado Estado a eliminar.
     * @return Estado eliminado.
     */
    public Estado eliminarEstado(Estado estado);

    /**
     * Busca un estado específico por su identificador único.
     *
     * @param id Id del estado a buscar.
     * @return Estado que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public Estado buscarEstadoPorID(int id);
}
