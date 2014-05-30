package api;

import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
//import jee.api.GenericRest;
import jee.dao.GenericModel;
import models.CotacaoDAO;
import models.dto.Cotacao;
import models.dto.Produto;
import utils.Result;
import utils.transform.Render;

@Path("/cotacoes")
@Produces(MediaType.APPLICATION_JSON)
public class Cotacoes extends GenericRest<Cotacao>{
    @EJB
    CotacaoDAO dao;

    @Override
    public GenericModel getModel() {
        return dao;
    }
    
    @Path("/findCpf/{cpf}")
    @GET
    public Response findCpf(@PathParam("cpf") String cpf) {
        List<Cotacao> cotacao = (List<Cotacao>) getModel().find("SELECT cotacao from cotacoes cotacao where cpf like '%" + cpf + "%'");

        if (cotacao == null) {
            //Registro nao encontrado
            return Response.ok(Render.JSON(Result.ERROR(3))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(Render.JSON(Result.OK(cotacao))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Path("/{id}")
    @DELETE
    public Response deleteId(@PathParam("id") long id) {
        dao.deleteObj(id);
        return Response.ok(Render.JSON(Result.OK(getModel().findAll()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    public Cotacao getDtoToSave(MultivaluedMap<String, String> form) {

        System.err.println("getDtoToSave");

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        
        Cotacao c = new Cotacao();
        c.setCpf(form.getFirst("cotacao.cpf"));        
        c.setSolicitante(form.getFirst("cotacao.solicitante"));
        
        try {
            System.out.println(form.getFirst("cotacao.dataSolicitacao"));
            c.setDataSolicitacao(sdf.parse(form.getFirst("cotacao.dataSolicitacao")));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        
        Set<Produto> produtos = new LinkedHashSet<Produto>();
        Set<String> lista = form.keySet();
        for (String param : lista) {
            if(param.indexOf("[")>-1){
                String val_id = form.getFirst(param);
                Produto p = new Produto();
                p.setId(new Long(val_id));
                produtos.add(p);
            }
        }
        
        c.setProdutos(produtos);
        
        return c;
    }

    @Override
    public void setDtoToSave(Cotacao dto, MultivaluedMap<String, String> form) {
        dto.setCpf(form.getFirst("cotacao.cpf"));
        dto.setSolicitante(form.getFirst("cotacao.solicitante"));
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        
        try {
            System.out.println(form.getFirst("cotacao.dataSolicitacao"));
            dto.setDataSolicitacao(sdf.parse(form.getFirst("cotacao.dataSolicitacao")));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        
        Set<Produto> produtos = new LinkedHashSet<Produto>();
        Set<String> lista = form.keySet();
        
        for (String param : lista) {
            if(param.indexOf("[")>-1){
                String val_id = form.getFirst(param);
                Produto p = new Produto();
                p.setId(new Long(val_id));
                produtos.add(p);
            }
        }
        
        dto.setProdutos(produtos);
    }
}
