
package org.itson.apps.playpostdto;

import java.util.List;

/**
 *
 * @author victo
 */
public class EstadoDTO {
    private String nombre;
    private List<MunicipioDTO> municipios;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<MunicipioDTO> municipios) {
        this.municipios = municipios;
    }
    
    
}
