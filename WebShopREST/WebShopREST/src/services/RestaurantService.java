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
import dao.BuyerDAO;
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
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		return restaurantDAO.getAllRestaurants();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRestaurant(Restaurant restaurant) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		try {
			restaurantDAO.saveRestaurant(restaurant);
		} catch (IOException e) {
			e.printStackTrace();
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
}
