/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import models.PedidoDAO;
import models.dto.Pedido;

/**
 *
 * @author itakenami
 */
@Stateless
@WebService(
        portName = "PedidoPort",
        serviceName = "PedidoService",
        targetNamespace = "http://localhost/wsdl")
public class PedidoWS {

    @EJB  
    PedidoDAO dao;

    public Pedido save(Pedido obj) {
        try {
            dao.save(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public Pedido update(Long id, Pedido obj) {
        try {
            dao.update(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean delete(Long id) {

        Pedido c = dao.findById(id);

        try {
            dao.delete(c);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<Pedido> findAll() {
        return dao.findAll();
    }

    public Pedido findById(Long id) {
        return dao.findById(id);
    }
}