/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import models.ProdutoDAO;
import models.dto.Produto;

/**
 *
 * @author itakenami
 */
@Stateless
@WebService(
        portName = "ProdutoPort",
        serviceName = "ProdutoService",
        targetNamespace = "http://localhost/wsdl")
public class ProdutoWS {

    @EJB  
    ProdutoDAO dao;

    public Produto save(Produto obj) {
        try {
            dao.save(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public Produto update(Long id, Produto obj) {
        try {
            dao.update(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean delete(Long id) {
        Produto c = dao.findById(id);
        try {
            dao.delete(c);
            return true;
        } catch (Exception ex) {
            return false;
        }
        
    }

    public List<Produto> findAll() {
        return dao.findAll();
    }
    
    public List<Produto> findNome(String nome) {
        //List<Produto> produtos = dao.find("nome = " + nome);
        return dao.findAll();
    }
  
    public Produto findById(Long id) {
        return dao.findById(id);
    }
    
}
