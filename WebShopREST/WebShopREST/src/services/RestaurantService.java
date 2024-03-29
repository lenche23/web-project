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

import beans.Restaurant;
import dao.BasketDAO;
import dao.BuyerDAO;
import dao.CommentDAO;
import dao.RestaurantDAO;

@Path("/restaurants")
public class RestaurantService {	
	
	@Context
	ServletContext ctx;
	
	public RestaurantService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getRestaurants() {
		if (ctx.getAttribute("basketDAO") == null) {
	    	ctx.setAttribute("basketDAO", new BasketDAO());
		}
		
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		return restaurantDAO.getAllRestaurants();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant addRestaurant(Restaurant restaurant) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		try {
			return restaurantDAO.saveRestaurant(restaurant);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getFilteredRestaurants(@QueryParam("filterType") String type, @QueryParam("filterOpen") String open) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		return restaurantDAO.getFilteredRestaurants(type, open);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getSearchedRestaurants(@QueryParam("searchName") String name, @QueryParam("searchLocation") String location, @QueryParam("searchGrade") String grade, @QueryParam("searchType") String type) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		return restaurantDAO.getSearchedRestaurants(name, location, grade, type);
	}
	
	@PUT
	@Path("/delete/{name}")
	public void deleteRestaurant(@PathParam("name") String name) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		try {
			restaurantDAO.deleteRestaurant(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/setViewedRestaurant/{name}")
	public void setViewedRestaurant(@PathParam("name") String name) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		try {
			restaurantDAO.setViewedRestaurant(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/getViewedRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getViewedRestaurant() {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		return restaurantDAO.getViewedRestaurant();
	}
	
	@PUT
	@Path("/update/{name}")
	public void updateGrade(@PathParam("name") String name) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		try {
			restaurantDAO.updateGrade(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
