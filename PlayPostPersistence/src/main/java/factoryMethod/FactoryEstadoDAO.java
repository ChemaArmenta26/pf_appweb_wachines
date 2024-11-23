/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factoryMethod;


import daos.EstadoDAO;
import daos.IEstadoDAO;
import excepciones.PersistenciaException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class FactoryEstadoDAO implements IFactoryDAO {

    public IEstadoDAO crearDAO(){
        try {
            return new EstadoDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(FactoryUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
