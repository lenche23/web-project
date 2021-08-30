package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Manager;
import beans.Order;
import dao.ArticleDAO;
import dao.BuyerDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;

@Path("/orders")
public class OrderService {

	@Context
	ServletContext ctx;
	
	public OrderService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
		if (ctx.getAttribute("buyerDAO") == null) {
	    	ctx.setAttribute("buyerDAO", new BuyerDAO());
		}
		if (ctx.getAttribute("articleDAO") == null) {
	    	ctx.setAttribute("articleDAO", new ArticleDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
		if (ctx.getAttribute("orderDAO") == null) {
	    	ctx.setAttribute("orderDAO", new OrderDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO"), (BuyerDAO) ctx.getAttribute("buyerDAO"), (ArticleDAO) ctx.getAttribute("articleDAO")));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getOrders() {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		return orderDAO.getAllOrders();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveOrder(Order order) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.saveOrder(order);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
