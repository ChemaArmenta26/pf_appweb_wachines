/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.playpostobjetosnegocio.BOs;

import org.itson.apps.playpostdto.PostDTO;

/**
 *
 * @author JoseH
 */
public interface IPostBO {
    /**
     * Agrega una nueva publicación al sistema.
     *
     * @param postDTO Publicación a agregar.
     * @return Publicación agregada.
     */
    public PostDTO agregarPost(PostDTO postDTO);

    /**
     * Actualiza la información de una publicación existente.
     *
     * @param postDTO Publicación con los datos actualizados.
     * @return Publicación actualizada.
     */
    public PostDTO actualizarPost(PostDTO postDTO);
    
    /**
     * Ancla una publicación.
     *
     * @param postDTO Publicación a anclar.
     * @return Publicación anclada.
     */
    public PostDTO anclarPost(PostDTO postDTO);

    /**
     * Elimina una publicación del sistema.
     *
     * @param postDTO Publicación a eliminar.
     * @return Publicación eliminada.
     */
    public PostDTO eliminarPost(PostDTO postDTO);

    /**
     * Busca una publicación específica por su identificador único.
     *
     * @param id Id de la publicación a buscar.
     * @return Publicación que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public PostDTO buscarPostPorID(Long id);
}
