package models.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
//import org.hibernate.annotations.Cascade;

@Entity(name = "produtos")
@XmlRootElement(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProduto")
    private long id;

    private String nome;
    private String descricao;
    private String valor;
    private String quantidade;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.produto", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    //@OneToMany(mappedBy = "pk.produto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    //private List<ProdutoPedido> produtoPedido = new ArrayList<ProdutoPedido>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

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
/*
    public List<ProdutoPedido> getProdutoPedido() {
        return produtoPedido;
    }

    public void setProdutoPedido(List<ProdutoPedido> pProdutoPedido) {
        produtoPedido = pProdutoPedido;
    }
*/    
}