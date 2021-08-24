package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import dao.AdministratorDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.RestaurantDAO;

@Path("/buyers")
public class BuyerService {
	
	@Context
	ServletContext ctx;
	
	public BuyerService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("buyerDAO") == null) {
	    	ctx.setAttribute("buyerDAO", new BuyerDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Buyer> getBuyers() {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return buyerDAO.getBuyers();
	}
	
	@GET
	@Path("/loggedInBuyer")
	@Produces(MediaType.APPLICATION_JSON)
	public Buyer getLoggedInBuyer() {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return buyerDAO.getLoggedInBuyer();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addBuyer(Buyer buyer) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		
		try {
			buyerDAO.saveBuyer(buyer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Buyer> getFilteredBuyers(@QueryParam("buyerType") String type) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return buyerDAO.getFilteredBuyers(type);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Buyer> getSearchedBuyers(@QueryParam("searchName") String name, @QueryParam("searchSurname") String surname, @QueryParam("searchUsername") String username) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return buyerDAO.getSearchedBuyers(name, surname, username);
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Buyer login(@QueryParam("username") String username, @QueryParam("password") String password) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		if (ctx.getAttribute("administratorDAO") == null) {
	    	ctx.setAttribute("administratorDAO", new AdministratorDAO());
		}
		if (ctx.getAttribute("delivererDAO") == null) {
	    	ctx.setAttribute("delivererDAO", new DelivererDAO());
		}
		if (ctx.getAttribute("managerDAO") == null) {
	    	ctx.setAttribute("managerDAO", new ManagerDAO(restaurantDAO));
		}
		
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return buyerDAO.login(username, password);
	}
	
	@PUT
	@Path("/delete/{username}")
	public void deleteBuyer(@PathParam("username") String username) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		
		try {
			buyerDAO.deleteBuyer(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
