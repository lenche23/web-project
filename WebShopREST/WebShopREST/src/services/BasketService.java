package services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.ArticleWithQuantity;
import beans.Basket;
import beans.Deliverer;
import beans.Restaurant;
import dao.BasketDAO;
import dao.DelivererDAO;
import dao.RestaurantDAO;

@Path("/basket")
public class BasketService {

	@Context
	ServletContext ctx;
	
	public BasketService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("basketDAO") == null) {
	    	ctx.setAttribute("basketDAO", new BasketDAO());
		}
	}
	
	@POST
	@Path("/addArticleWithQuantity")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArticleWithQuantityToBasket(ArticleWithQuantity articleWithQuantity) {
		BasketDAO basketDAO = (BasketDAO) ctx.getAttribute("basketDAO");
		basketDAO.addArticleWithQuantityToBasket(articleWithQuantity);
	}
	
	@POST
	@Path("/removeArticleWithQuantity")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeArticleWithQuantityFromBasket(ArticleWithQuantity articleWithQuantity) {
		BasketDAO basketDAO = (BasketDAO) ctx.getAttribute("basketDAO");
		basketDAO.removeArticleWithQuantityFromBasket(articleWithQuantity);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getBasket() {
		BasketDAO basketDAO = (BasketDAO) ctx.getAttribute("basketDAO");
		return basketDAO.getBasket();
	}
	
	@PUT
	@Path("/changeQuantity/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void changeQuantity(@PathParam("id") String article, ArticleWithQuantity articleWithQuantity) {
		BasketDAO basketDAO = (BasketDAO) ctx.getAttribute("basketDAO");
		basketDAO.changeQuantity(article, articleWithQuantity);
	}
}
