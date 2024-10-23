/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpost.conexion;

import com.mycompany.playpost.excepciones.PersistenciaException;
import javax.persistence.EntityManager;

/**
 *
 * @author JoseH
 */
public interface IConexion {
    
    public EntityManager crearConexion() throws PersistenciaException;
}