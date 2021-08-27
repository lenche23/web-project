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

import beans.Administrator;
import beans.Article;
import beans.Restaurant;
import dao.AdministratorDAO;
import dao.ArticleDAO;
import dao.RestaurantDAO;

@Path("/articles")
public class ArticleService {
	@Context
	ServletContext ctx;
	
	public ArticleService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	ctx.setAttribute("restaurantDAO", new RestaurantDAO());
		}
		
		if (ctx.getAttribute("articleDAO") == null) {
	    	ctx.setAttribute("articleDAO", new ArticleDAO((RestaurantDAO) ctx.getAttribute("restaurantDAO")));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Article> getArticles() {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		return articleDAO.getAllArticles();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArticle(Article article) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		
		try {
			articleDAO.saveArticle(article);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/articlesFromRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Article> getArticlesFromRestaurant(@QueryParam("restaurantName") String name) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		return articleDAO.getArticlesFromRestaurant(name);
	}
	
	@PUT
	@Path("/delete/{restaurantName}/{articleName}")
	public void deleteArticle(@PathParam("restaurantName") String restaurantName, @PathParam("articleName") String articleName) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		
		try {
			articleDAO.deleteArticle(restaurantName, articleName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Article> getSearchedArticles(@QueryParam("searchArticle") String article, @QueryParam("searchRestaurant") String restaurant) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		return articleDAO.getSearchedArticles(article, restaurant);
	}
	
	@GET
	@Path("/setUpdatedArticle/{articleName}/{restaurantName}")
	public void setUpdatedArticle(@PathParam("articleName") String article, @PathParam("restaurantName") String restaurant) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		
		try {
			articleDAO.setUpdatedArticle(article, restaurant);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/getUpdatedArticle")
	@Produces(MediaType.APPLICATION_JSON)
	public Article getUpdatedArticle() {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		return articleDAO.getUpdatedArticle();
	}
	
	@PUT
	@Path("/saveArticleChanges")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveArticleChanges(Article article) {
		ArticleDAO articleDAO = (ArticleDAO) ctx.getAttribute("articleDAO");
		
		try {
			articleDAO.saveArticleChanges(article);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
