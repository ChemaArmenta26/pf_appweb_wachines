/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package auxiliar;

import com.mycompany.playpostobjetosnegocio.BOs.IUsuarioBO;
import com.mycompany.playpostobjetosnegocio.BOs.UsuarioBO;
import entidades.Estado;
import entidades.Municipio;
import entidades.Usuario;
import enums.TipoUsuario;
import java.util.Calendar;
import org.itson.apps.playpostdto.EstadoDTO;
import org.itson.apps.playpostdto.MunicipioDTO;
import org.itson.apps.playpostdto.UsuarioDTO;

/**
 *
 * @author PC
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         // Crear una instancia de Calendar con la fecha y hora actual
        Calendar fechaHoraCreacion = Calendar.getInstance();

        // Crear el Estado
        EstadoDTO estado = new EstadoDTO();
        estado.setNombre("Estado de Prueba");

        // Crear el Municipio asociado al Estado
        MunicipioDTO municipioDTO = new MunicipioDTO();
        municipioDTO.setNombre("Municipio de Prueba");
        municipioDTO.setEstado(estado);

        // Crear el Usuario asociado al Municipio
        String nombreCompleto = "Usuario de Prueba";
        String correo = "usuario@prueba.com";
        String contrasenia = "contrasenia123";
        String telefono = "123456789";
        String ciudad = "Ciudad de Prueba";
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.set(1990, Calendar.JANUARY, 1); // Fecha de nacimiento de ejemplo
        String genero = "Masculino";

        UsuarioDTO usuario = new UsuarioDTO();
               usuario.setNombreCompleto(nombreCompleto);
               usuario.setCorreo(correo);
               usuario.setContrasenia(contrasenia);
               usuario.setCorreo(correo);
               usuario.setTelefono(telefono);
               usuario.setCiudad(ciudad);
               usuario.setFechaNacimiento(fechaNacimiento);
               usuario.setGenero(genero);
               usuario.setMunicipio(municipioDTO);
               usuario.setTipo(TipoUsuario.NORMAL);

        IUsuarioBO usuarioBO = new UsuarioBO();
        
        usuarioBO.agregarUsuario(usuario);
    }
    
}
