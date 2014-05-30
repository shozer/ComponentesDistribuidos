package api;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
//import jee.api.GenericRest;
import jee.dao.GenericModel;
import models.ProdutoDAO;
import models.dto.Produto;
import utils.Result;
import utils.transform.Render;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
public class Produtos extends GenericRest<Produto> {

    @EJB
    ProdutoDAO dao;

    @Override
    public GenericModel getModel() {
        return dao;
    }
    
    @Path("/findNome/{nome}")
    @GET
    public Response findNome(@PathParam("nome") String nome) {
        List<Produto> produto = new ArrayList<Produto>();
        
        if(nome != null && !"*".equals(nome.trim()))
            produto = (List<Produto>) getModel().find("SELECT produto from produtos produto where lower(nome) like '%" + nome.trim().toLowerCase() + "%'");
        else
            produto = (List<Produto>) getModel().find("SELECT produto from produtos produto");

        return Response.ok(Render.JSON(Result.OK(produto))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Path("/{id}")
    @DELETE
    public Response deleteById(@PathParam("id") long id) {
        dao.deleteObj(id);
        return Response.ok(Render.JSON(Result.OK(getModel().findAll()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Path("/delete/{id}")
    @GET
    public Response deleteId(@PathParam("id") long id) {
        dao.deleteObj(id);
        return Response.ok(Render.JSON(Result.OK(getModel().findAll()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Override
    public Produto getDtoToSave(MultivaluedMap<String, String> form) {
        Produto c = new Produto();
        c.setNome(form.getFirst("produto.nome"));        
        c.setDescricao(form.getFirst("produto.descricao"));
        c.setValor(form.getFirst("produto.valor"));
        c.setQuantidade(form.getFirst("produto.quantidade"));

        return c;
    }

    @Override
    public void setDtoToSave(Produto dto, MultivaluedMap<String, String> form) {
        dto.setNome(form.getFirst("produto.nome"));
        dto.setDescricao(form.getFirst("produto.descricao"));
        dto.setValor(form.getFirst("produto.valor"));
        dto.setQuantidade(form.getFirst("produto.quantidade"));
    }
}