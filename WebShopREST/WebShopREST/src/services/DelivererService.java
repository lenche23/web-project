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

import beans.Deliverer;
import dao.DelivererDAO;

@Path("/deliverers")
public class DelivererService {

	@Context
	ServletContext ctx;
	
	public DelivererService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("delivererDAO") == null) {
	    	ctx.setAttribute("delivererDAO", new DelivererDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Deliverer> getDeliverers() {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		return delivererDAO.getDeliverers();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addDeliverer(Deliverer deliverer) {
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		
		try {
			delivererDAO.saveDeliverer(deliverer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
