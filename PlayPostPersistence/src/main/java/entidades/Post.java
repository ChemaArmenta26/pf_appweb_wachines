/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.TipoPost;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JoseH
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaHoraCreacion", nullable = false)
    private Calendar fechaHoraCreacion;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name = "tipo", nullable = false)
    private TipoPost tipo;

    @Column(name = "anclado", nullable = true)
    private Boolean anclado;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comentario> comentarios;

    public Post() {
    }

    public Post(Calendar fechaHoracreacion, String titulo, String contenido, Usuario usuario, TipoPost tipo) {
        this.fechaHoraCreacion = fechaHoracreacion;
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoPost getTipo() {
        return tipo;
    }

    public void setTipo(TipoPost tipo) {
        this.tipo = tipo;
    }

    public Boolean getAnclado() {
        return anclado;
    }

    public void setAnclado(Boolean anclado) {
        this.anclado = anclado;
    }

    public Calendar getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void addComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public void setId(Long id) {
        this.id = id;
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

}
