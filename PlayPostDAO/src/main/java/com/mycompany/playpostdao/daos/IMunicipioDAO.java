/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.playpostdao.daos;

import com.mycompany.playpostdao.entidades.Municipio;
import com.mycompany.playpostdao.excepciones.PersistenciaException;

/**
 *
 * @author PC
 */
public interface IMunicipioDAO {

    public Municipio agregarMunicipio(Municipio municipio) throws PersistenciaException;

    public Municipio actualizarMunicipio(Municipio municipio) throws PersistenciaException;

    public Municipio eliminarMunicipio(Municipio municipio) throws PersistenciaException;

    public Municipio buscarMunicipioPorID(int id) throws PersistenciaException;
}
