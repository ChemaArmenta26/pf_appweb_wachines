/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import entidades.Post;
import excepciones.PersistenciaException;

/**
 *
 * @author PC
 */
public interface IPostDAO {

    public Post agregarPost(Post post) throws PersistenciaException;

    public Post actualizarPost(Post post) throws PersistenciaException;

    public Post eliminarPost(Post post) throws PersistenciaException;

    public Post buscarPostPorID(Long id) throws PersistenciaException;

    public Post anclarPost(Post post) throws PersistenciaException;
}
