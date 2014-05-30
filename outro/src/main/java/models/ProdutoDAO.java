package models;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jee.dao.GenericModel;
import models.dto.Produto;

@Stateless
public class ProdutoDAO extends GenericModel<Produto> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public boolean deleteObj(Long id)
    {
        try{
            Produto p = (Produto) em.find(getEntityClass(), id);
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
    protected Class<Produto> getEntityClass() {
        return Produto.class;
    }
}