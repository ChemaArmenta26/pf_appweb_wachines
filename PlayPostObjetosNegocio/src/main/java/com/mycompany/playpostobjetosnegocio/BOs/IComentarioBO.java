
package com.mycompany.playpostobjetosnegocio.BOs;

import entidades.Comentario;
import org.itson.apps.playpostdto.ComentarioDTO;

/**
 *
 * @author victo
 */
public interface IComentarioBO {
    
    public Comentario agregarComentario(Comentario comentario);
    
    public Comentario agregarComentarioAUnComentario(Comentario comentario, Comentario comentarioNuevo);
    
    public Comentario actualizarComentario(Comentario comentario);
    
    public Comentario eliminarComentario(Comentario comentario);
    
    public Comentario buscarComentarioPorID(Long id);
}
