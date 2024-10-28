
package org.itson.apps.playpostdto;

import java.util.List;

/**
 *
 * @author victo
 */
public class MunicipioDTO {
    private String nombre;
    private List<UsuarioDTO> usuarios;
    private EstadoDTO estado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public EstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }
    
    
}
