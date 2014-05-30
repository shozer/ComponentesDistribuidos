package models.dto;

import java.io.Serializable;
 
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
 
@Embeddable
public class ProdutoPedidoPK implements Serializable {
 
 private static final long serialVersionUID = -5869094934725857817L;
 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 private Produto produto;
 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 private Pedido pedido;

 public boolean equals(Object o) {

    if (this == o)
        return true;

    if (o == null || getClass() != o.getClass())
        return false;

    if (!(o instanceof ProdutoPedidoPK))
        return false;

    ProdutoPedidoPK that = (ProdutoPedidoPK) o;

    if (this.produto != null ? !this.produto.equals(that.produto) : that.produto != null)
        return false;

    if (this.pedido != null ? !this.pedido.equals(that.pedido) : that.pedido != null)
        return false;

    return true;
 }
 
 public int hashCode() {

    int result;

    result = (this.produto != null ? this.produto.hashCode() : 0);
    result = 31 * result + (this.pedido != null ? this.pedido.hashCode() : 0);

    return result;
 }

    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto pProduto) {
        produto = pProduto;
    }

    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pPedido) {
        pedido = pPedido;
    }
}