
package models.dto;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "cotacoes")
@XmlRootElement(name = "cotacao")
public class Cotacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String cpf;
    private String solicitante;
    //@Temporal(javax.persistence.TemporalType.DATE)
    private Date dataSolicitacao;
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="produtos_cotacoes", joinColumns={@JoinColumn(name="cotacao_id")}, inverseJoinColumns={@JoinColumn(name="produto_id")})
    private Set<Produto> produtos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }
}
