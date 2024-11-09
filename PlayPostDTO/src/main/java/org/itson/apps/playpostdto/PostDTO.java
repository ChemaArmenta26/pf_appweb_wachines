
package org.itson.apps.playpostdto;

import enums.TipoPost;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author victo
 */
public class PostDTO {
    private Long id;
    private Calendar fechaHoraCreacion;
    private String titulo;
    private String contenido;
    private byte[] imagenData;
    private UsuarioDTO usuario;
    private TipoPost tipo;
    private List<ComentarioDTO> comentarios;

    public Calendar getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public TipoPost getTipo() {
        return tipo;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setFechaHoraCreacion(Calendar fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public void setTipo(TipoPost tipo) {
        this.tipo = tipo;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageData() {
        return Base64.getEncoder().encodeToString(imagenData);
    }

    public void setImageData(byte[] imageData) {
        this.imagenData = imageData;
    }
    

    
    
}
