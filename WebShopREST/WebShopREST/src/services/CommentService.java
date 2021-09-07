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

import beans.Comment;
import dao.CommentDAO;
import dao.RestaurantDAO;

@Path("/comments")
public class CommentService {

	@Context
	ServletContext ctx;
	
	public CommentService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
		
		if (ctx.getAttribute("commentDAO") == null) {
	    	ctx.setAttribute("commentDAO", new CommentDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getComments() {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		return commentDAO.getAllComments();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComment(Comment comment) {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		
		try {
			commentDAO.saveComment(comment);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/commentsFromRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getCommentsFromRestaurant(@QueryParam("restaurantName") String name) {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		return commentDAO.getCommentsFromRestaurant(name);
	}
	
	@PUT
	@Path("/delete/{restaurantName}/{commentId}")
	public void deleteArticle(@PathParam("restaurantName") String restaurantName, @PathParam("commentId") int commentId) {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		
		try {
			commentDAO.deleteComment(restaurantName, commentId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
