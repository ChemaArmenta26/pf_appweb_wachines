package facade;


import entidades.Comentario;

/**
 * Interfaz para la fachada de operaciones sobre entidades de tipo Comentario.
 *
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public interface IFacadeComentario {

    /**
     * Agrega un nuevo comentario al sistema.
     *
     * @param comentario Comentario a agregar.
     * @return Comentario agregado.
     */
    public Comentario agregarComentario(Comentario comentario);

    /**
     * Agrega una respuesta a un comentario existente.
     *
     * @param comentario Comentario al que se le agregará una respuesta.
     * @param comentarioNuevo comentario de respuesta a agregar.
     * @return Comentario principal con la nueva respuesta asociada.
     */
    public Comentario agregarComentarioAUnComentario(Comentario comentario, Comentario comentarioNuevo);

    /**
     * Actualiza la información de un comentario existente.
     *
     * @param comentario Comentario con los nuevos datos a actualizar.
     * @return Comentario actualizado.
     */
    public Comentario actualizarComentario(Comentario comentario);

    /**
     * Elimina un comentario del sistema.
     *
     * @param comentario Comentario a eliminar.
     * @return Comentario eliminado.
     */
    public Comentario eliminarComentario(Comentario comentario);

    /**
     * Busca un comentario específico por su identificador único.
     *
     * @param id Id del comentario a buscar.
     * @return Comentario que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public Comentario buscarComentarioPorID(int id);
}
