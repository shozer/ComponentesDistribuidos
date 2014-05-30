package models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
 
@Entity(name = "pedidos")
@XmlRootElement(name = "pedido")
public class Pedido implements Serializable {
 
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 @Column(name = "idPedido")
 private Long id;

 private String cpf;
 private String nome;
 private String endereco;
 private Date data;
 private String total;

 //@OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.pedido", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
 @OneToMany(mappedBy = "pk.pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 //@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
 private Set<ProdutoPedido> produtoPedido = new LinkedHashSet<ProdutoPedido>();

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String pEndereco) {
        endereco = pEndereco;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date pData) {
        data = pData;
    }

    public Set<ProdutoPedido> getProdutoPedido() {
        return produtoPedido;
    }

    public void setProdutoPedido(Set<ProdutoPedido> pProdutoPedido) {
        produtoPedido = pProdutoPedido;
    }
}