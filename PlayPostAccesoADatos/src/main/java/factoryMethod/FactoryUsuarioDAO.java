/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factoryMethod;

import com.mycompany.playpostdao.daos.IUsuarioDAO;
import com.mycompany.playpostdao.daos.UsuarioDAO;
import com.mycompany.playpostdao.excepciones.PersistenciaException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class FactoryUsuarioDAO implements IFactoryDAO {

    @Override
    public IUsuarioDAO crearDAO(){
        try {
            return new UsuarioDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(FactoryUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}