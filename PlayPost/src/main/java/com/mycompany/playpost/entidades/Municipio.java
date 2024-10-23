/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpost.entidades;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author JoseH
 */
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name ="nombre")
    private String nombre;
    
    @OneToMany(mappedBy = "usuario",  cascade = CascadeType.REMOVE)
    private List<Usuario> usuarios;
    
    @ManyToOne
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    private Estado estado;
}
