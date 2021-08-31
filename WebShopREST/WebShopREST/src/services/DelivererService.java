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

import beans.Administrator;
import beans.Buyer;
import beans.Deliverer;
import dao.AdministratorDAO;
import dao.ArticleDAO;
import dao.BasketDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
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
	    	ctx.setAttribute("orderDAO", new OrderDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO"), (BuyerDAO) ctx.getAttribute("buyerDAO"), (ArticleDAO) ctx.getAttribute("articleDAO"), (DelivererDAO) ctx.getAttribute("delivererDAO")));
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
	
	@PUT
	@Path("/saveProfileChanges/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveProfileChanges(@PathParam("id") String username, Deliverer deliverer) {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		
		try {
			delivererDAO.saveProfileChanges(username, deliverer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
