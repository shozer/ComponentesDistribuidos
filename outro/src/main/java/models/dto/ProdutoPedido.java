package models.dto;

import java.io.Serializable;
//import javax.persistence.AssociationOverride;
//import javax.persistence.AssociationOverrides;
import javax.persistence.Embeddable;
//import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.Transient;

@Entity()
@Table(name="pedidos_produtos")
//@Entity(name = "produto_pedido")
//@AssociationOverrides({ @AssociationOverride(name = "pk.pedido", joinColumns = @JoinColumn(name = "idPedido")),
//@AssociationOverride(name = "pk.produto", joinColumns = @JoinColumn(name = "idProduto")) })
public class ProdutoPedido implements Serializable {

 //@EmbeddedId
 //private ProdutoPedidoPK pk = new ProdutoPedidoPK();
    
@Embeddable
public static class PK implements Serializable{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="produto_id")
    private Produto produto;
    
    public void setProduto(Produto produto)
    {
        this.produto  = produto;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido  = pedido;
    }
    
    public Produto getProduto()
    {
        return this.produto;
    }

    public Pedido getPedido()
    {
        return this.pedido;
    }
}

@Id
private PK pk = new PK();
 private String quantidade;
 private String valor;
 /*
 @Override
 public boolean equals(Object o) {

     if (this == o)
        return true;

    if (o == null || getClass() != o.getClass())
        return false;
 
    ProdutoPedido that = (ProdutoPedido) o;
 
    if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
        return false;
 
    return true;
 }

 @Transient
 public Produto getProduto() {
 return this.getPk().getProduto();
 }
 
 @Transient
 public Pedido getPedido() {
 return this.getPk().getPedido();
 }
 
 @Override
 public int hashCode() {
 return (getPk() != null ? getPk().hashCode() : 0);
 }

 public ProdutoPedidoPK getPk() {
     return pk;
 }
 public void setPk(ProdutoPedidoPK pPk) {
     pk = pPk;
 }
*/
 
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
    
    public PK getPk() {
        return pk;
    }
}