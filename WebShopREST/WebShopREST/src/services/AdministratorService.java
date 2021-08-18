package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Administrator;
import dao.AdministratorDAO;

@Path("/administrators")
public class AdministratorService {

	@Context
	ServletContext ctx;
	
	public AdministratorService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("administratorDAO") == null) {
			ctx.setAttribute("administratorDAO", new AdministratorDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Administrator> getAdministrators() {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		return administratorDAO.findAll();
	}
}
