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
import dao.BuyerDAO;

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
		BuyerDAO userDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		return userDAO.getBuyers();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addBuyer(Buyer buyer) {
		BuyerDAO userDAO = (BuyerDAO) ctx.getAttribute("buyerDAO");
		
		try {
			userDAO.saveBuyer(buyer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
