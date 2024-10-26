/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpost.entidades;

import com.mycompany.playpost.enums.TipoPost;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author JoseH
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name ="fecha_hora_creacion")
    private LocalDateTime fechaHoracreacion;
    
    @Column (name="titulo")
    private String titulo;
    
    @Column (name="contenido")
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Usuario usuario;
    
    @Column (name="tipo")
    private TipoPost tipo;
    
    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHoracreacion() {
        return fechaHoracreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaHoracreacion(LocalDateTime fechaHoracreacion) {
        this.fechaHoracreacion = fechaHoracreacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    
}