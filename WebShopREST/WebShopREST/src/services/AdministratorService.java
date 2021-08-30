package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Administrator;
import dao.AdministratorDAO;
import dao.ArticleDAO;
import dao.BasketDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;

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
	public ArrayList<Administrator> getAdministrators() {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		return administratorDAO.getAllAdministrators();
	}
	
	@GET
	@Path("/loggedInAdministrator")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator getLoggedInAdministrator() {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		return administratorDAO.getLoggedInAdministrator();
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Administrator> getSearchedAdministrators(@QueryParam("searchName") String name, @QueryParam("searchSurname") String surname, @QueryParam("searchUsername") String username) {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		return administratorDAO.getSearchedAdministrators(name, surname, username);
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator login(@QueryParam("username") String username, @QueryParam("password") String password) {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
		if (ctx.getAttribute("managerDAO") == null) {
	    	ctx.setAttribute("managerDAO", new ManagerDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
		if (ctx.getAttribute("articleDAO") == null) {
	    	ctx.setAttribute("articleDAO", new ArticleDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
		if (ctx.getAttribute("administratorDAO") == null) {
	    	ctx.setAttribute("administratorDAO", new AdministratorDAO());
		}
		if (ctx.getAttribute("delivererDAO") == null) {
	    	ctx.setAttribute("delivererDAO", new DelivererDAO());
		}
		if (ctx.getAttribute("buyerDAO") == null) {
	    	ctx.setAttribute("buyerDAO", new BuyerDAO());
		}
		if (ctx.getAttribute("basketDAO") == null) {
	    	ctx.setAttribute("basketDAO", new BasketDAO());
		}
		if (ctx.getAttribute("orderDAO") == null) {
	    	ctx.setAttribute("orderDAO", new OrderDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO"), (BuyerDAO) ctx.getAttribute("buyerDAO"), (ArticleDAO) ctx.getAttribute("articleDAO")));
		}
		
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		return administratorDAO.login(username, password);
	}
	
	@PUT
	@Path("/saveProfileChanges/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveProfileChanges(@PathParam("id") String username, Administrator admin) {
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		
		try {
			administratorDAO.saveProfileChanges(username, admin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/logout")
	public void logout() {
		if(ctx.getAttribute("administratorDAO") != null) {
		     ctx.removeAttribute("administratorDAO");
		}
		if(ctx.getAttribute("articleDAO") != null) {
		     ctx.removeAttribute("articleDAO");
		}
		if(ctx.getAttribute("buyerDAO") != null) {
		     ctx.removeAttribute("buyerDAO");
		}
		if(ctx.getAttribute("delivererDAO") != null) {
		     ctx.removeAttribute("delivererDAO");
		}
		if(ctx.getAttribute("managerDAO") != null) {
		     ctx.removeAttribute("managerDAO");
		}
		if(ctx.getAttribute("restaurantDAO") != null) {
		     ctx.removeAttribute("restaurantDAO");
		}
		if(ctx.getAttribute("basketDAO") != null) {
		     ctx.removeAttribute("basketDAO");
		}
		if(ctx.getAttribute("orderDAO") != null) {
	    	ctx.removeAttribute("orderDAO");
		}
	}
}
