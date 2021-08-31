package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.Article;
import beans.Deliverer;
import beans.Manager;
import beans.Order;
import beans.OrderStatus;
import beans.Restaurant;
import beans.RestaurantStatus;
import beans.Sex;

public class OrderDAO {
	private ArrayList<Order> allOrders;
	private String pathToRepository;
	
	public OrderDAO(RestaurantDAO restaurantDAO, BuyerDAO buyerDAO, ArticleDAO articleDAO, DelivererDAO delivererDAO) {
		allOrders = new ArrayList<Order>();
		pathToRepository = "WebContent/Repository/";
		loadOrders(restaurantDAO, buyerDAO, articleDAO, delivererDAO);
	}

	public ArrayList<Order> getAllOrders() {
		return allOrders;
	}
	
	public void loadOrders(RestaurantDAO restaurantDAO, BuyerDAO buyerDAO, ArticleDAO articleDAO, DelivererDAO delivererDAO) {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "orders.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray orders = (JSONArray) object;

            orders.forEach( order -> allOrders.add(parseOrder( (JSONObject) order, restaurantDAO, buyerDAO, articleDAO, delivererDAO ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Order parseOrder(JSONObject order, RestaurantDAO restaurantDAO, BuyerDAO buyerDAO, ArticleDAO articleDAO, DelivererDAO delivererDAO) 
    {
        JSONObject orderObject = (JSONObject) order.get("order");

        String id = (String) orderObject.get("id");
        String dateAndTime = (String) orderObject.get("dateAndTime");
        double price = (double) orderObject.get("price");
        String status = (String) orderObject.get("status");
        
        Order newOrder = new Order(id, dateAndTime, price, OrderStatus.valueOf(status));
        
        
    	String name = (String) orderObject.get("restaurant");
    	for(int i = 0; i < restaurantDAO.getAllRestaurants().size(); i++)
    		if(restaurantDAO.getAllRestaurants().get(i).getName().equals(name))
    			newOrder.setRestaurant(restaurantDAO.getAllRestaurants().get(i));
        
    	if(orderObject.get("deliverer") != null) {
	    	String usernameDeliverer = (String) orderObject.get("deliverer");
	    	for(int i = 0; i < delivererDAO.getDeliverers().size(); i++)
	    		if(delivererDAO.getDeliverers().get(i).getUsername().equals(usernameDeliverer))
	    			newOrder.setDeliverer(delivererDAO.getDeliverers().get(i));
    	}
    	
    	String username = (String) orderObject.get("buyer");
    	for(int i = 0; i < buyerDAO.getBuyers().size(); i++)
    		if(buyerDAO.getBuyers().get(i).getUsername().equals(username))
    			newOrder.setBuyer(buyerDAO.getBuyers().get(i));
        
        JSONArray articlesFromJson = (JSONArray) orderObject.get("articles");
        ArrayList<Article> articles = new ArrayList<Article>();
        for (Object articleFromJson : articlesFromJson) {
        	String articleName = (String) articleFromJson;
        	for (Article article : articleDAO.getArticlesFromRestaurant(newOrder.getRestaurant().getName())) {
        		if (articleName.equals(article.getName())) {
        			articles.add(article);
        			break;
        		}
        	}
        }
        
        newOrder.setArticles(articles);
        
		return newOrder;
    }
	
	private String calculateId() {
		String possibleLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(10);
		boolean idExists;
		
		do {
			idExists = false;
			sb = new StringBuilder(10);
			
			for (int i = 0; i < 10; i++) {
				int index = (int)(possibleLetters.length() * Math.random());
				sb.append(possibleLetters.charAt(index));
			}
			
			for(int i = 0; i < allOrders.size(); i++)
				if(allOrders.get(i).getId().equals(sb.toString()))
					idExists = true;
		} while(idExists);
		
		return sb.toString();
	}
	
	public void saveOrder(Order order) throws IOException {
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
		double price = order.getPrice();
		DecimalFormat df = new DecimalFormat("#.##");      
		price = Double.valueOf(df.format(price));
		
		order.setPrice(price);
		order.setId(calculateId());
		order.setDateAndTime(date);
		order.setStatus(OrderStatus.PROCESSING);
		allOrders.add(order);
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void changeToCanceled(String id) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setStatus(OrderStatus.CANCELED);
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void changeToPreparing(String id) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setStatus(OrderStatus.PREPARATING);
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void changeToWaitingForDeliverer(String id) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setStatus(OrderStatus.WAITING_FOR_DELIVERER);
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void addDeliverer(String id, DelivererDAO delivererDAO) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setDeliverer(delivererDAO.getLoggedInDeliverer());;
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void removeDeliverer(String id, DelivererDAO delivererDAO) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setDeliverer(new Deliverer());;
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void changeToInTransport(String id) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setStatus(OrderStatus.TRANSPORTING);
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void changeToDelivered(String id) throws IOException {
		for(int i = 0; i < allOrders.size(); i++) 
			if(allOrders.get(i).getId().equals(id))
				allOrders.get(i).setStatus(OrderStatus.DELIVERED);
		
		JSONArray orders = new JSONArray();
		for (Order o : allOrders) {
			JSONObject orderObject = new JSONObject();
			
			orderObject.put("id", o.getId());
			orderObject.put("dateAndTime", o.getDateAndTime());
			orderObject.put("price", o.getPrice());
			orderObject.put("status", o.getStatus().toString());
			orderObject.put("restaurant", o.getRestaurant().getName());
			orderObject.put("buyer", o.getBuyer().getUsername());
			
			if(o.getDeliverer().getUsername() != "")
				orderObject.put("deliverer", o.getDeliverer().getUsername());
			else
				orderObject.put("deliverer", null);
			
			JSONArray articles = new JSONArray();
			for (Article article : o.getArticles()) {
				articles.add(article.getName());
			}
			orderObject.put("articles", articles);  
			
			JSONObject orderObject2 = new JSONObject(); 
	        orderObject2.put("order", orderObject);
			
	        orders.add(orderObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "orders.json")) {
            file.write(orders.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
