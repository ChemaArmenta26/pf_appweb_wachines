/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.playpost.controladores;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author JoseH
 */
public class SubirImagen {
    
    @POST
    @Path("/imagen")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadImage(InputStream fileInputStream, String fileDetail, HttpServletRequest request) {

        String uploadDir = request.getServletContext().getRealPath("/imagenesSubidas/"); // Directorio de almacenamiento
        String filePath = null;             // Variable para guardar la ruta como String
        
        try {
            // Construir la ruta completa del archivo
            filePath = uploadDir + File.separator + fileDetail;
            File file = new File(filePath);

            // Crear el directorio si no existe
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            
            // Guardar el archivo en el sistema
            try (FileOutputStream out = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            
            return filePath;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
