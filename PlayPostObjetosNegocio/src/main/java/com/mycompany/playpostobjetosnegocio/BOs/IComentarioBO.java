
package com.mycompany.playpostobjetosnegocio.BOs;

import org.itson.apps.playpostdto.ComentarioDTO;

/**
 *
 * @author victo
 */
public interface IComentarioBO {
    
    public ComentarioDTO agregarComentario(ComentarioDTO comentario);
    
    public ComentarioDTO agregarComentarioAUnComentario(ComentarioDTO comentario, ComentarioDTO comentarioNuevo);
    
    public ComentarioDTO actualizarComentario(ComentarioDTO comentario);
    
    public ComentarioDTO eliminarComentario(ComentarioDTO comentario);
    
    public ComentarioDTO buscarComentarioPorID(int id);
}
