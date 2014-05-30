package api;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import jee.api.GenericRest;
import jee.dao.GenericModel;
import models.CargoDao;
import models.dto.Cargo;

@Path("/cargos")
@Produces(MediaType.APPLICATION_JSON)
public class Cargos extends GenericRest<Cargo> {

    @EJB
    CargoDao dao;

    @Override
    public GenericModel getModel() {
        return dao;
    }

    @Override
    public Cargo getDtoToSave(MultivaluedMap<String, String> form) {
        Cargo c = new Cargo();
        c.setNome(form.getFirst("cargo.nome"));
        return c;
    }

    @Override
    public void setDtoToSave(Cargo dto, MultivaluedMap<String, String> form) {
        dto.setNome(form.getFirst("cargo.nome"));
    }
}