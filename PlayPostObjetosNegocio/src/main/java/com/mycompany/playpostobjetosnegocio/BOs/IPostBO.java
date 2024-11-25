/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.playpostobjetosnegocio.BOs;

import entidades.Post;
import java.util.List;

/**
 *
 * @author JoseH
 */
public interface IPostBO {
    /**
     * Agrega una nueva publicación al sistema.
     *
     * @param post Publicación a agregar.
     * @return Publicación agregada.
     */
    public Post agregarPost(Post post);

    /**
     * Actualiza la información de una publicación existente.
     *
     * @param post Publicación con los datos actualizados.
     * @return Publicación actualizada.
     */
    public Post actualizarPost(Post post);
    
    /**
     * Ancla una publicación.
     *
     * @param post Publicación a anclar.
     * @return Publicación anclada.
     */
    public Post anclarPost(Post post);

    /**
     * Elimina una publicación del sistema.
     *
     * @param post Publicación a eliminar.
     * @return Publicación eliminada.
     */
    public Post eliminarPost(Post post);

    /**
     * Busca una publicación específica por su identificador único.
     *
     * @param id Id de la publicación a buscar.
     * @return Publicación que corresponde al ID proporcionado, o null si no se
     * encuentra.
     */
    public Post buscarPostPorID(Long id);
    
    /**
     * Consulta todas las publicaciones subidas
     * @return Lista de todas las publicaciones subidas
     */
    public List<Post> consultarTodosLosPosts();
}
