/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.apps.playpostdto;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author victo
 */
public class ComentarioDTO {
    private Long id;
    private Calendar fechaHora;
    private String contenido;
    private UsuarioDTO usuario;
    private PostDTO post;
    private ComentarioDTO comentarioMayor;
    private List<ComentarioDTO> respuestas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Calendar getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Calendar fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public ComentarioDTO getComentarioMayor() {
        return comentarioMayor;
    }

    public void setComentarioMayor(ComentarioDTO comentarioMayor) {
        this.comentarioMayor = comentarioMayor;
    }

    public List<ComentarioDTO> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<ComentarioDTO> respuestas) {
        this.respuestas = respuestas;
    }
    
    
}
