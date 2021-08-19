package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Administrator;
import beans.Manager;
import dao.AdministratorDAO;
import dao.ManagerDAO;

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
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Administrator> getSearchedAdministrators(@QueryParam("searchName") String name, @QueryParam("searchSurname") String surname, @QueryParam("searchUsername") String username) {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		return administratorDAO.getSearchedAdministrators(name, surname, username);
	}
}
