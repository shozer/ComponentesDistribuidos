package api;

import java.util.Set;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import jee.dao.GenericModel;
import models.PedidoDAO;
import models.ProdutoDAO;
import models.dto.Produto;
import models.dto.Pedido;
import models.dto.ProdutoPedido;
//import models.dto.ProdutoPedidoPK;
import utils.Result;
import utils.transform.Exclude;
import utils.transform.Render;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
public class Pedidos extends GenericRest<Pedido> {

    @EJB
    PedidoDAO dao;
    
    @EJB
    ProdutoDAO dao_produto;
    
    @Override
    public GenericModel getModel() {
        return dao;
    }

    @Override
    @Path("/")
    @GET
    public Response listAll()
    {
        List<Pedido> found = dao.findAll();
        
        Exclude e = new Exclude().exclude("pedido");
        
        try {
            //return Response.ok(Render.JSON(Result.OK(found))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
            return Response.ok(Render.JSON(Result.OK(found),e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
            return Response.ok(Render.JSON(Result.SYSERROR(ex.getMessage()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }
    
    @Path("/findNome/{nome}")
    @GET
    public Response findNome(@PathParam("nome") String nome) {
        List<Pedido> pedido = new ArrayList<Pedido>();
        
        if(nome != null && !"*".equals(nome.trim()))
            pedido = (List<Pedido>) getModel().find("SELECT pedido from pedidos pedido where lower(nome) like '%" + nome.trim().toLowerCase() + "%'");
        else
            pedido = (List<Pedido>) getModel().find("SELECT pedido from pedidos pedido");

        Exclude e = new Exclude().exclude("pedido");
        return Response.ok(Render.JSON(Result.OK(pedido),e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Override
    @Path("/{id}")
    @GET
    public Response findId(@PathParam("id") long id) {

        Pedido obj = (Pedido) getModel().findById(id);

        Exclude e = new Exclude().exclude("pedido");
        if (obj == null) {
            //Registro nao encontrado
            return Response.ok(Render.JSON(Result.ERROR(3))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(Render.JSON(Result.OK(obj), e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Path("/delete/{id}")
    @GET
    public Response deleteId(@PathParam("id") long id) {
        dao.deleteObj(id);
        return Response.ok(Render.JSON(Result.OK(getModel().findAll()))).type("application/json").header("Access-Control-Allow-Origin", "*").build();
    }
    
    @Override
    public Pedido getDtoToSave(MultivaluedMap<String, String> form) {

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        Pedido c = new Pedido();
    
        c.setCpf(form.getFirst("pedido.cpf"));
        c.setNome(form.getFirst("pedido.nome"));
        c.setEndereco(form.getFirst("pedido.endereco"));

         try {
             c.setData(sdf.parse(form.getFirst("pedido.data")));
        }
         catch (ParseException ex) {

             System.out.println(ex.getMessage());
        }

        Set<ProdutoPedido> produtosPedido = new LinkedHashSet<ProdutoPedido>();
        
        int qtd_item = Integer.parseInt(form.getFirst("pedido.qtd_item"));
        
        for (int i = 0; i < qtd_item; i++) {
            ProdutoPedido item = new ProdutoPedido();
            Long pro_id = new Long(form.getFirst("pedido.produto.id["+ String.valueOf(i) +"]"));
            int qtd_pro = Integer.parseInt(form.getFirst("pedido.produto.quantidade["+ String.valueOf(i) +"]"));

            Produto pro = dao_produto.findById(pro_id);
            item.setQuantidade(String.valueOf(qtd_pro));
            item.setValor(String.valueOf(Integer.parseInt(pro.getValor()) * qtd_pro));
            item.getPk().setProduto(pro);
            item.getPk().setPedido(c);

            produtosPedido.add(item);
        }

        c.setProdutoPedido(produtosPedido);

        return c;
    }

    @Override
    public void setDtoToSave(Pedido dto, MultivaluedMap<String, String> form) {

        dto.setCpf(form.getFirst("pedido.cpf"));
        dto.setNome(form.getFirst("pedido.nome"));
        dto.setEndereco(form.getFirst("pedido.endereco"));

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        try {

            dto.setData(sdf.parse(form.getFirst("pedido.data")));
        }
        catch (ParseException ex) {

            System.out.println(ex.getMessage());
        }

        Set<ProdutoPedido> produtosPedido = new LinkedHashSet<ProdutoPedido>();

        int qtd_item = Integer.parseInt(form.getFirst("pedido.qtd_item"));
        
        for (int i = 0; i < qtd_item; i++) {
            ProdutoPedido item = new ProdutoPedido();
            Long pro_id = new Long(form.getFirst("pedido.produto.id["+ String.valueOf(i) +"]"));
            int qtd_pro = Integer.parseInt(form.getFirst("pedido.produto.quantidade["+ String.valueOf(i) +"]"));

            Produto pro = dao_produto.findById(pro_id);
            item.setQuantidade(String.valueOf(qtd_pro));
            item.setValor(String.valueOf(Integer.parseInt(pro.getValor()) * qtd_pro));
            item.getPk().setProduto(pro);
            item.getPk().setPedido(dto);
            produtosPedido.add(item);
        }

        dto.setProdutoPedido(produtosPedido);
    }
    
    @Override
    @Path("/")
    @POST
    public Response save(MultivaluedMap<String, String> form) {
        
        Pedido obj = getDtoToSave(form);
        Exclude e = new Exclude().exclude("pedido");
        
        try {
            getModel().save(obj);
            return Response.ok(Render.JSON(Result.OK(obj),e)).type("application/json").header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception ex) {
System.out.println("######################################ERRO!!!###########################################");
            return Response.serverError().type("application/json").header("Access-Control-Allow-Origin", "*").build();
        }
    }
}