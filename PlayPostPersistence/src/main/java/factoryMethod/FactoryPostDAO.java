/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factoryMethod;

import daos.IPostDAO;
import daos.PostDAO;
import excepciones.PersistenciaException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class FactoryPostDAO implements IFactoryDAO {

    @Override
    public IPostDAO crearDAO(){
        try {
            return new PostDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(FactoryUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
