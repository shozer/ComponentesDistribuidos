package ws;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import models.CotacaoDAO;
import models.dto.Cotacao;

@Stateless
@WebService(
        portName = "CotacaoPort",
        serviceName = "CotacaoService",
        targetNamespace = "http://localhost/wsdl")
public class CotacaoWS {
    @EJB  
    CotacaoDAO dao;

    public Cotacao save(Cotacao obj) {
        try {
            dao.save(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public Cotacao update(Long id, Cotacao obj) {
        try {
            dao.update(obj);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean delete(Long id) {
        Cotacao c = dao.findById(id);
        try {
            dao.delete(c);
            return true;
        } catch (Exception ex) {
            return false;
        }
        
    }

    public List<Cotacao> findAll() {
        return dao.findAll();
    }
    
    public List<Cotacao> findCpf(String nome) {
        return dao.findAll();
    }
  
    public Cotacao findById(Long id) {
        return dao.findById(id);
    }
}
