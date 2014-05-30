package models;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jee.dao.GenericModel;
import models.dto.Cotacao;

@Stateless
public class CotacaoDAO extends GenericModel<Cotacao> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public boolean deleteObj(Long id)
    {
        try{
            Cotacao p = (Cotacao) em.find(getEntityClass(), id);
            if (p != null) {
                em.remove(p);
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());            
        }
        
        return false;
    }

    @Override
    protected Class<Cotacao> getEntityClass() {
        return Cotacao.class;
    }
}
