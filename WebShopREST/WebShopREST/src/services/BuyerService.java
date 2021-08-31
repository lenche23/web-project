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

import beans.Administrator;
import beans.Buyer;
import dao.AdministratorDAO;
import dao.ArticleDAO;
import dao.BasketDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
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
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
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
		if (ctx.getAttribute("articleDAO") == null) {
	    	ctx.setAttribute("articleDAO", new ArticleDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
		if (ctx.getAttribute("basketDAO") == null) {
	    	ctx.setAttribute("basketDAO", new BasketDAO());
		}
		if (ctx.getAttribute("buyerDAO") == null) {
	    	ctx.setAttribute("buyerDAO", new BuyerDAO());
		}
		if (ctx.getAttribute("orderDAO") == null) {
	    	ctx.setAttribute("orderDAO", new OrderDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO"), (BuyerDAO) ctx.getAttribute("buyerDAO"), (ArticleDAO) ctx.getAttribute("articleDAO"), (DelivererDAO) ctx.getAttribute("delivererDAO")));
		}
		
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
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
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
		if (ctx.getAttribute("articleDAO") == null) {
	    	ctx.setAttribute("articleDAO", new ArticleDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
		if (ctx.getAttribute("basketDAO") == null) {
	    	ctx.setAttribute("basketDAO", new BasketDAO());
		}
		if (ctx.getAttribute("buyerDAO") == null) {
	    	ctx.setAttribute("buyerDAO", new BuyerDAO());
		}
		if (ctx.getAttribute("orderDAO") == null) {
	    	ctx.setAttribute("orderDAO", new OrderDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO"), (BuyerDAO) ctx.getAttribute("buyerDAO"), (ArticleDAO) ctx.getAttribute("articleDAO"), (DelivererDAO) ctx.getAttribute("delivererDAO")));
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
	
	@PUT
	@Path("/saveProfileChanges/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveProfileChanges(@PathParam("id") String username, Buyer buyer) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		
		try {
			buyerDAO.saveProfileChanges(username, buyer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/addPoints")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addPoints(String price) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		
		try {
			double priceDouble = Double.parseDouble(price.split(":")[1].split("}")[0]);
			buyerDAO.addPoints(priceDouble);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/removePoints")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removePoints(String price) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		
		try {
			double priceDouble = Double.parseDouble(price.split(":")[1].split("}")[0]);
			buyerDAO.removePoints(priceDouble);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/getBuyersByUsername")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Buyer> getBuyersByUsername(@QueryParam("searchUsername") String username) {
		BuyerDAO buyerDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return buyerDAO.getBuyersByUsername(username);
	}
}
