/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factoryMethod;

import com.mycompany.playpost.daos.IMunicipioDAO;
import com.mycompany.playpost.daos.MunicipioDAO;
import com.mycompany.playpost.excepciones.PersistenciaException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class FactoryMunicipioDAO implements IFactoryDAO {

    @Override
    public IMunicipioDAO crearDAO() {
        try {
            return new MunicipioDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(FactoryUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
