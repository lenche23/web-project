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

import beans.Buyer;
import beans.Deliverer;
import beans.Manager;
import dao.UserDAO;

@Path("/users")
public class UserService {
	
	@Context
	ServletContext ctx;
	
	public UserService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDAO") == null) {
	    	ctx.setAttribute("userDAO", new UserDAO());
		}
	}
	
	@GET
	@Path("/buyers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Buyer> getBuyers() {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		return userDAO.getBuyers();
	}
	
	@GET
	@Path("/managers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> getManagers() {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		return userDAO.getManagers();
	}
	
	@GET
	@Path("/deliverers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Deliverer> getDeliverers() {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		return userDAO.getDeliverers();
	}
	
	@POST
	@Path("/saveBuyer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addBuyer(Buyer buyer) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		try {
			userDAO.saveBuyer(buyer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/saveManager")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addManager(Manager manager) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		try {
			userDAO.saveManager(manager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/saveDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addDeliverer(Deliverer deliverer) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		try {
			userDAO.saveDeliverer(deliverer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
