package services;

import java.io.IOException;
import java.text.ParseException;
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

import beans.ArticleWithQuantity;
import beans.Manager;
import beans.Order;
import beans.Restaurant;
import dao.ArticleDAO;
import dao.BasketDAO;
import dao.BuyerDAO;
import dao.DelivererDAO;
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
		if (ctx.getAttribute("delivererDAO") == null) {
	    	ctx.setAttribute("delivererDAO", new DelivererDAO());
		}
		if (ctx.getAttribute("orderDAO") == null) {
	    	ctx.setAttribute("orderDAO", new OrderDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO"), (BuyerDAO) ctx.getAttribute("buyerDAO"), (ArticleDAO) ctx.getAttribute("articleDAO"), (DelivererDAO) ctx.getAttribute("delivererDAO")));
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
		
		if(ctx.getAttribute("basketDAO") != null) {
		     ctx.removeAttribute("basketDAO");
		}
	}
	
	@PUT
	@Path("/changeToCanceled/{id}")
	public void changeToCanceled(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.changeToCanceled(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/changeToPreparing/{id}")
	public void changeToPreparing(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.changeToPreparing(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/changeToWaitingForDeliverer/{id}")
	public void changeToWaitingForDeliverer(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.changeToWaitingForDeliverer(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/changeToInTransport/{id}")
	public void changeToInTransport(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.changeToInTransport(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/changeToDelivered/{id}")
	public void changeToDelivered(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.changeToDelivered(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/addDeliverer/{id}")
	public void addDeliverer(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.addDeliverer(id, (DelivererDAO) ctx.getAttribute("delivererDAO"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/removeDeliverer/{id}")
	public void removeDeliverer(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		try {
			orderDAO.removeDeliverer(id, (DelivererDAO) ctx.getAttribute("delivererDAO"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getFilteredOrders(@QueryParam("orderStatusFilter") String type) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		return orderDAO.getFilteredOrders(type);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getSearchedOrders(@QueryParam("searchPrice") String price, @QueryParam("searchDateAndTime") String date) throws ParseException {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		return orderDAO.getSearchedOrders(price, date);
	}
}
