/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpost.entidades;

import java.time.LocalDate;

/**
 *
 * @author JoseH
 */
public class UsuarioAdmor extends Usuario{
    
    public UsuarioAdmor(String nombreCompleto, String correo, String contrasenia, String telefono, String avatar, String ciudad, LocalDate fechaNacimiento, String genero, Municipio municipio) {
        super(nombreCompleto, correo, contrasenia, telefono, avatar, ciudad, fechaNacimiento, genero, municipio);
    }
}
