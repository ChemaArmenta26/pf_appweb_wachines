/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factoryMethod;

import com.mycompany.playpost.daos.ComentarioDAO;
import com.mycompany.playpost.daos.IComentarioDAO;
import com.mycompany.playpost.excepciones.PersistenciaException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class FactoryComentarioDAO implements IFactoryDAO {

    @Override
    public IComentarioDAO crearDAO() {
        try {
            return new ComentarioDAO();
        } catch (PersistenciaException ex) {
            Logger.getLogger(FactoryUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
