package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.Administrator;
import beans.Article;
import beans.ArticleType;
import beans.Buyer;
import beans.Deliverer;
import beans.Manager;
import beans.Restaurant;
import beans.Sex;

public class ArticleDAO {
	private ArrayList<Article> allArticles;
	private Article updatedArticle;
	private String pathToRepository;
	
	public ArticleDAO(RestaurantDAO restaurantDAO) {
		allArticles = new ArrayList<Article>();
		updatedArticle = new Article();
		pathToRepository = "WebContent/Repository/";
		loadArticles(restaurantDAO);
	}
	
	public ArrayList<Article> getAllArticles() {
		return allArticles;
	}
	
	public Article getUpdatedArticle() {
		return updatedArticle;
	}

	public void setUpdatedArticle(String article, String restaurant) throws IOException {
		for(int i = 0; i < allArticles.size(); i++)
			if(allArticles.get(i).getName().equals(article) && allArticles.get(i).getRestaurant().getName().equals(restaurant))
				updatedArticle = allArticles.get(i);
	}
	
	public ArrayList<Article> getArticlesFromRestaurant(String name) {
		ArrayList<Article> articlesFromRestaurant = new ArrayList<Article>();
		for(int i = 0; i < allArticles.size(); i++)
			if(allArticles.get(i).getRestaurant().getName().equals(name))
				articlesFromRestaurant.add(allArticles.get(i));
		return articlesFromRestaurant;
	}

	public void loadArticles(RestaurantDAO restaurantDAO) {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "articles.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray articles = (JSONArray) object;

            articles.forEach( article -> allArticles.add(parseArticle( (JSONObject) article, restaurantDAO ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Article parseArticle(JSONObject article, RestaurantDAO restaurantDAO) 
    {
        JSONObject articleObject = (JSONObject) article.get("article");

        String name = (String) articleObject.get("name");
        double price = (double) articleObject.get("price");
        String type = (String) articleObject.get("type");
        String quantity = (String) articleObject.get("quantity");
        String description = (String) articleObject.get("description");
        String logo = (String) articleObject.get("logo");
        boolean deleted = (boolean) articleObject.get("deleted");
        
        Article newArticle = new Article(logo, name, price, ArticleType.valueOf(type), quantity, description, deleted);
        
        String restaurantName = (String) articleObject.get("restaurant");
    	for(int i = 0; i < restaurantDAO.getAllRestaurants().size(); i++)
    		if(restaurantDAO.getAllRestaurants().get(i).getName().equals(restaurantName))
    			newArticle.setRestaurant(restaurantDAO.getAllRestaurants().get(i));
        
		return newArticle;
    }
	
	public void saveArticle(Article article) throws IOException {
		article.setDeleted(false);
		allArticles.add(article);
		
		JSONArray articles = new JSONArray();
		for (Article a : allArticles) {
			JSONObject articleObject = new JSONObject();
			
			articleObject.put("name", a.getName());
			articleObject.put("price", a.getPrice());
			articleObject.put("type", a.getType().toString());
			articleObject.put("quantity", a.getQuantity());
			articleObject.put("description", a.getDescription());
			articleObject.put("logo", a.getLogo());
			articleObject.put("deleted", a.isDeleted());
			articleObject.put("restaurant", a.getRestaurant().getName());
			
			JSONObject articleObject2 = new JSONObject(); 
	        articleObject2.put("article", articleObject);
			
	        articles.add(articleObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "articles.json")) {
            file.write(articles.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void deleteArticle(String restaurantName, String articleName) throws IOException {
		for(int i = 0; i < allArticles.size(); i++)
			if(allArticles.get(i).getName().equals(articleName) && allArticles.get(i).getRestaurant().getName().equals(restaurantName))
				allArticles.get(i).setDeleted(true);
		
		JSONArray articles = new JSONArray();
		for (Article a : allArticles) {
			JSONObject articleObject = new JSONObject();
			
			articleObject.put("name", a.getName());
			articleObject.put("price", a.getPrice());
			articleObject.put("type", a.getType().toString());
			articleObject.put("quantity", a.getQuantity());
			articleObject.put("description", a.getDescription());
			articleObject.put("logo", a.getLogo());
			articleObject.put("deleted", a.isDeleted());
			articleObject.put("restaurant", a.getRestaurant().getName());
			
			JSONObject articleObject2 = new JSONObject(); 
	        articleObject2.put("article", articleObject);
			
	        articles.add(articleObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "articles.json")) {
            file.write(articles.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Article> getSearchedArticles(String article, String restaurant) { 
		ArrayList<Article> searchedArticles = new ArrayList<Article>();
		ArrayList<Article> articlesFromRestaurant = getArticlesFromRestaurant(restaurant);
		
		if(article.equals("")) {
			return articlesFromRestaurant;
		}
		else {
			for(int i = 0; i < articlesFromRestaurant.size(); i++) {
				if(articlesFromRestaurant.get(i).getName().toLowerCase().contains(article.toLowerCase()))
					searchedArticles.add(articlesFromRestaurant.get(i));
			}
		}
		
		return searchedArticles;
	}
	
	public void saveArticleChanges(Article article) throws IOException {
		for(Article a : allArticles) {
			if(a.getName().equals(article.getName()) && a.getRestaurant().getName().equals(article.getRestaurant().getName())) {
				a.setPrice(article.getPrice());
				a.setType(article.getType());
				a.setDescription(article.getDescription());
				a.setQuantity(article.getQuantity());
				a.setLogo(article.getLogo());
			}
		}
		
		JSONArray articles = new JSONArray();
		for (Article a : allArticles) {
			JSONObject articleObject = new JSONObject();
			
			articleObject.put("name", a.getName());
			articleObject.put("price", a.getPrice());
			articleObject.put("type", a.getType().toString());
			articleObject.put("quantity", a.getQuantity());
			articleObject.put("description", a.getDescription());
			articleObject.put("logo", a.getLogo());
			articleObject.put("deleted", a.isDeleted());
			articleObject.put("restaurant", a.getRestaurant().getName());
			
			JSONObject articleObject2 = new JSONObject(); 
	        articleObject2.put("article", articleObject);
			
	        articles.add(articleObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "articles.json")) {
            file.write(articles.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
