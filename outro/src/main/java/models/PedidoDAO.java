package models;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import jee.dao.GenericModel;
import models.dto.Pedido;
import models.dto.Produto;
import models.dto.ProdutoPedido;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PedidoDAO extends GenericModel<Pedido> {
    
    @Resource
    UserTransaction ut;

    @PersistenceContext
    private EntityManager em;

    @EJB
    ProdutoDAO produtoDAO;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void save(Pedido obj) throws Exception {

        try {
            this.ut.begin();
        } catch (Exception e) {
            throw new Exception();
        }

        try {

            for (ProdutoPedido lProdutoPedido :  obj.getProdutoPedido()) {

                Produto lProduto = (Produto) em.find(Produto.class, lProdutoPedido.getPk().getProduto().getId());

                if (Integer.parseInt(lProdutoPedido.getQuantidade()) <= Integer.parseInt(lProduto.getQuantidade())) {

                    lProduto.setQuantidade(String.valueOf(Integer.parseInt(lProduto.getQuantidade()) - Integer.parseInt(lProdutoPedido.getQuantidade())));

                    produtoDAO.save(lProduto);
                }
                else {

                    throw new Exception("O produto " + lProduto.getNome() + " não possui estoque(" + lProduto.getQuantidade() + ") suficiente!");
                }
            }

            em.persist(obj);

            this.ut.commit();
        }
        catch (Exception e) {

            try {

                this.ut.rollback();
            }
            catch (Exception e1) {
            }

            throw new Exception(e);
        }
    }
    
    public boolean deleteObj(Long id)
    {
        try{
            Pedido p = (Pedido) em.find(getEntityClass(), id);
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
    protected Class<Pedido> getEntityClass() {
        return Pedido.class;
    }
}