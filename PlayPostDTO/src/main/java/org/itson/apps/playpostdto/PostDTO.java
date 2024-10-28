
package org.itson.apps.playpostdto;

import java.util.Calendar;
import java.util.List;
import org.itson.apps.playpostdto.enums.TipoPost;

/**
 *
 * @author victo
 */
public class PostDTO {
    private Calendar fechaHoraCreacion;
    private String titulo;
    private String contenido;
    private UsuarioDTO usuario;
    private TipoPost tipo;
    private Boolean anclado;
    private List<ComentarioDTO> comentarios;
}
