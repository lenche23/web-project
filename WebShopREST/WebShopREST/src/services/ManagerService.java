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
import beans.Manager;
import dao.AdministratorDAO;
import dao.ArticleDAO;
import dao.BasketDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;

@Path("/managers")
public class ManagerService {
	
	@Context
	ServletContext ctx;
	
	public ManagerService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
		if (ctx.getAttribute("managerDAO") == null) {
	    	ctx.setAttribute("managerDAO", new ManagerDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
		if (ctx.getAttribute("articleDAO") == null) {
	    	ctx.setAttribute("articleDAO", new ArticleDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> getManagers() {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		return managerDAO.getManagers();
	}
	
	@GET
	@Path("/loggedInManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getLoggedInManager() {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		return managerDAO.getLoggedInManager();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addManager(Manager manager) {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		
		try {
			managerDAO.saveManager(manager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> getSearchedManagers(@QueryParam("searchName") String name, @QueryParam("searchSurname") String surname, @QueryParam("searchUsername") String username) {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		return managerDAO.getSearchedManagers(name, surname, username);
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager login(@QueryParam("username") String username, @QueryParam("password") String password) {
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
		
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		return managerDAO.login(username, password);
	}
	
	@PUT
	@Path("/delete/{username}")
	public void deleteManager(@PathParam("username") String username) {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		
		try {
			managerDAO.deleteManager(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/addManager/{managerUsername}/toRestaurant/{restaurantName}")
	public void addManagerToRestaurant(@PathParam("managerUsername") String username, @PathParam("restaurantName") String name) {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		try {
			managerDAO.addManagerToRestaurant(username, name, restaurantDAO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/saveProfileChanges/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveProfileChanges(@PathParam("id") String username, Manager manager) {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		
		try {
			managerDAO.saveProfileChanges(username, manager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
