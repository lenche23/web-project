package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Restaurant;
import dao.RestaurantDAO;

@Path("/restaurants")
public class RestaurantService {	
	
	@Context
	HttpServletRequest request;
	
	public RestaurantService() {
	}
	
	@PostConstruct
	public void init() {
		if (request.getAttribute("restaurantDAO") == null) {
	    	request.getSession().setAttribute("restaurantDAO", new RestaurantDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getRestaurants() {
		RestaurantDAO restaurantDAO = (RestaurantDAO) request.getSession().getAttribute("restaurantDAO");
		return restaurantDAO.getAllRestaurants();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRestaurant(Restaurant restaurant) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) request.getSession().getAttribute("restaurantDAO");
		
		try {
			restaurantDAO.saveRestaurant(restaurant);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/filterByType")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getRestaurantsByType(@QueryParam("filterType") String type) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) request.getSession().getAttribute("restaurantDAO");
		return restaurantDAO.getRestaurantsByType(type);
	}
	
	@GET
	@Path("/filterOpen")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getOpenRestaurants() {
		RestaurantDAO restaurantDAO = (RestaurantDAO) request.getSession().getAttribute("restaurantDAO");
		return restaurantDAO.getOpenRestaurants();
	}
}
