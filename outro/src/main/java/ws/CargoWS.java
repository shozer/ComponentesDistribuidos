/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import models.CargoDao;
import models.dto.Cargo;

/**
 *
 * @author itakenami
 */
@Stateless
@WebService(
        portName = "CargoPort",
        serviceName = "CargoService",
        targetNamespace = "http://localhost/wsdl")
public class CargoWS {

    @EJB  
    CargoDao dao;

    public Cargo save(Cargo obj) {
        try {
            dao.save(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public Cargo update(Long id, Cargo obj) {
        try {
            dao.update(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean delete(Long id) {
        Cargo c = dao.findById(id);
        try {
            dao.delete(c);
            return true;
        } catch (Exception ex) {
            return false;
        }
        
    }

    public List<Cargo> findAll() {
        return dao.findAll();
    }

    public Cargo findById(Long id) {
        return dao.findById(id);
    }
    
}
