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
import dao.ManagerDAO;

@Path("/managers")
public class ManagerService {
	
	@Context
	ServletContext ctx;
	
	public ManagerService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("managerDAO") == null) {
	    	ctx.setAttribute("managerDAO", new ManagerDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> getManagers() {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		return managerDAO.getManagers();
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
}
