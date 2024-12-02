/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.TipoPost;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JoseH
 */
@Entity
public class Post implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name= "id", nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaHoraCreacion", nullable = false)
    private Calendar fechaHoraCreacion;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "contenido", nullable = false)
    private String contenido;
    
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name = "tipo", nullable = false)
    private TipoPost tipo;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comentario> comentarios;
    
    @Lob
    @Column (name = "imagen", nullable = false)
    private String imagenData;

    public Post() {
    }

    public Post(Calendar fechaHoracreacion, String titulo, String contenido, String categoria, Usuario usuario, TipoPost tipo, String imagenData) {
        this.fechaHoraCreacion = fechaHoracreacion;
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
        this.tipo = tipo;
        this.imagenData = imagenData;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getImagenData() {
        return imagenData;
    }

    public void setImagenData(String imagenData) {
        this.imagenData = imagenData;
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
