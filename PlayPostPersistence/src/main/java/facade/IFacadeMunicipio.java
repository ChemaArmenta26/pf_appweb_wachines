package facade;

import entidades.Municipio;

/**
 * Interfaz para la fachada de operaciones sobre entidades de tipo Municipio.
 * 
 * @autor Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public interface IFacadeMunicipio {

    /**
     * Agrega un nuevo municipio al sistema.
     *
     * @param municipio Municipio a agregar.
     * @return Municipio agregado.
     */
    public Municipio agregarMunicipio(Municipio municipio);

    /**
     * Actualiza la información de un municipio existente.
     *
     * @param municipio Municipio con los datos actualizados.
     * @return Municipio actualizado.
     */
    public Municipio actualizarMunicipio(Municipio municipio);

    /**
     * Elimina un municipio del sistema.
     *
     * @param municipio Municipio a eliminar.
     * @return Municipio eliminado.
     */
    public Municipio eliminarMunicipio(Municipio municipio);

    /**
     * Busca un municipio específico por su identificador único.
     *
     * @param id Id del municipio a buscar.
     * @return Municipio que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public Municipio buscarMunicipioPorID(int id);
}
