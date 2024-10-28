/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.playpost.daos;

import com.mycompany.playpost.entidades.Estado;
import com.mycompany.playpost.entidades.Municipio;
import com.mycompany.playpost.excepciones.PersistenciaException;

/**
 *
 * @author PC
 */
public interface IEstadoDAO {

    public Estado agregarEstado(Estado estado) throws PersistenciaException;

    public Estado agregarMunicipioAEstado(Estado estado, Municipio municipio) throws PersistenciaException;

    public Estado actualizarEstado(Estado estado) throws PersistenciaException;

    public Estado eliminarEstado(Estado estado) throws PersistenciaException;

    public Estado buscarEstadoPorID(int id) throws PersistenciaException;
}
