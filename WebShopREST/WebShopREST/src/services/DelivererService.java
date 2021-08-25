package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Buyer;
import beans.Deliverer;
import dao.AdministratorDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.RestaurantDAO;

@Path("/deliverers")
public class DelivererService {

	@Context
	ServletContext ctx;
	
	public DelivererService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("delivererDAO") == null) {
	    	ctx.setAttribute("delivererDAO", new DelivererDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Deliverer> getDeliverers() {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		return delivererDAO.getDeliverers();
	}
	
	@GET
	@Path("/loggedInDeliverer")
	@Produces(MediaType.APPLICATION_JSON)
	public Deliverer getLoggedInDeliverer() {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		return delivererDAO.getLoggedInDeliverer();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addDeliverer(Deliverer deliverer) {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		
		try {
			delivererDAO.saveDeliverer(deliverer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Deliverer login(@QueryParam("username") String username, @QueryParam("password") String password) {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		if (ctx.getAttribute("administratorDAO") == null) {
	    	ctx.setAttribute("administratorDAO", new AdministratorDAO());
		}
		if (ctx.getAttribute("buyerDAO") == null) {
	    	ctx.setAttribute("buyerDAO", new BuyerDAO());
		}
		if (ctx.getAttribute("managerDAO") == null) {
	    	ctx.setAttribute("managerDAO", new ManagerDAO(restaurantDAO));
		}
		
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		return delivererDAO.login(username, password);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Deliverer> getSearchedDeliverers(@QueryParam("searchName") String name, @QueryParam("searchSurname") String surname, @QueryParam("searchUsername") String username) {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		return delivererDAO.getSearchedDeliverers(name, surname, username);
	}
	
	@PUT
	@Path("/delete/{username}")
	public void deleteDeliverer(@PathParam("username") String username) {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		
		try {
			delivererDAO.deleteDeliverer(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
