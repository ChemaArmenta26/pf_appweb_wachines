/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpostdao.conexion;

import com.mycompany.playpostdao.daos.IPostDAO;
import com.mycompany.playpostdao.daos.PostDAO;
import com.mycompany.playpostdao.entidades.Post;
import com.mycompany.playpostdao.entidades.Usuario;
import com.mycompany.playpostdao.enums.TipoPost;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import java.util.Calendar;

/**
 *
 * @author JoseH
 */
public class pruebaConexion {
    public static void main(String[] args) throws PersistenciaException{
        // Crear una instancia de Calendar con la fecha y hora actual
        Calendar fechaHoraCreacion = Calendar.getInstance();

        // Inicializar el resto de los valores para el post
        String titulo = "Mi primera publicación";
        String contenido = "Este es el contenido de la publicación.";

        // Definir el tipo de post (debes tener un enum o clase llamada TipoPost)
        TipoPost tipo = TipoPost.COMUN; // O el valor que corresponda en tu enum

        // Crear la instancia de Post con los valores definidos
        Post post = new Post(fechaHoraCreacion, titulo, contenido, null, tipo, null);
        
        IPostDAO postDAO = new PostDAO();
        postDAO.agregarPost(post);
    }
}
