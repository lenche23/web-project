package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.print.attribute.standard.NumberOfDocuments;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.Buyer;
import beans.Comment;
import beans.Location;
import beans.Restaurant;
import beans.RestaurantStatus;

public class RestaurantDAO {
	private ArrayList<Restaurant> allRestaurants;
	private ArrayList<Restaurant> filteredRestaurants;
	private Restaurant viewedRestaurant;
	private String pathToRepository;
	
	public RestaurantDAO() {
		allRestaurants = new ArrayList<Restaurant>();
		filteredRestaurants = new ArrayList<Restaurant>();
		viewedRestaurant = new Restaurant();
		pathToRepository = "WebContent/Repository/";
		loadRestaurants();
	}
	
	public ArrayList<Restaurant> getAllRestaurants() {
		return allRestaurants;
	}
	
	public Restaurant getViewedRestaurant() {
		return viewedRestaurant;
	}

	public ArrayList<Restaurant> getFilteredRestaurants(String type, String open) {
		filteredRestaurants.clear();
		for(int i = 0; i < allRestaurants.size(); i++) {
        	filteredRestaurants.add(allRestaurants.get(i));
		}
		
		if(type.equals("0") && open.equals("false"))
			return allRestaurants;
		else if(!type.equals("0") && open.equals("false")) {
			Iterator<Restaurant> i = filteredRestaurants.iterator();
			while(i.hasNext()) {
				Restaurant restaurant = i.next();
				if(!restaurant.getType().equals(type))
					i.remove();
			}
			return filteredRestaurants;
		}
		else if(type.equals("0") && open.equals("true")) {
			Iterator<Restaurant> i = filteredRestaurants.iterator();
			while(i.hasNext()) {
				Restaurant restaurant = i.next();
				if(RestaurantStatus.CLOSED == restaurant.getStatus())
					i.remove();
			}
			return filteredRestaurants;
		}
		else {
			Iterator<Restaurant> i = filteredRestaurants.iterator();
			while(i.hasNext()) {
				Restaurant restaurant = i.next();
				if(!restaurant.getType().equals(type) || RestaurantStatus.CLOSED == restaurant.getStatus())
					i.remove();
			}
			return filteredRestaurants;
		}
	}
	
	public ArrayList<Restaurant> getSearchedRestaurants(String name, String location, String gradeStr, String type) { 
		ArrayList<Restaurant> restaurantsByName = new ArrayList<Restaurant>();
		ArrayList<Restaurant> restaurantsByNameAndLocation = new ArrayList<Restaurant>();
		ArrayList<Restaurant> restaurantsByNameLocationAndGrade = new ArrayList<Restaurant>();
		ArrayList<Restaurant> restaurantsByNameLocationGradeAndType = new ArrayList<Restaurant>();
		
		if(name.equals("")) {
			for(int i = 0; i < filteredRestaurants.size(); i++) {
				restaurantsByName.add(filteredRestaurants.get(i));
			}
		}
		else {
			for(int i = 0; i < filteredRestaurants.size(); i++) {
				if(filteredRestaurants.get(i).getName().toLowerCase().contains(name.toLowerCase()))
					restaurantsByName.add(filteredRestaurants.get(i));
			}
		}
		
		if(location.equals("")) {
			for(int i = 0; i < restaurantsByName.size(); i++) {
				restaurantsByNameAndLocation.add(restaurantsByName.get(i));
			}
		}
		else {
			for(int i = 0; i < restaurantsByName.size(); i++) {
				String[] address = restaurantsByName.get(i).getLocation().getAddress().split("\\s*,\\s*");
				if(address[1].toLowerCase().contains(location.toLowerCase()) || address[3].toLowerCase().contains(location.toLowerCase()))
					restaurantsByNameAndLocation.add(restaurantsByName.get(i));
			}
		}
		
		if(gradeStr.equals("")) {
			for(int i = 0; i < restaurantsByNameAndLocation.size(); i++) {
				restaurantsByNameLocationAndGrade.add(restaurantsByNameAndLocation.get(i));
			}
		}
		else {
			double grade = Double.parseDouble(gradeStr);
			for(int i = 0; i < restaurantsByNameAndLocation.size(); i++) {
				if(4.5 == grade)
					restaurantsByNameLocationAndGrade.add(restaurantsByNameAndLocation.get(i));
			}
		}
		
		if(type.equals("Tip restorana")) {
			for(int i = 0; i < restaurantsByNameLocationAndGrade.size(); i++) {
				restaurantsByNameLocationGradeAndType.add(restaurantsByNameLocationAndGrade.get(i));
			}
		}
		else {
			for(int i = 0; i < restaurantsByNameLocationAndGrade.size(); i++) {
				if(restaurantsByNameLocationAndGrade.get(i).getType().equals(type))
					restaurantsByNameLocationGradeAndType.add(restaurantsByNameLocationAndGrade.get(i));
			}
		}
		
		return restaurantsByNameLocationGradeAndType;
	}

	public void loadRestaurants() {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "restaurants.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray restaurants = (JSONArray) object;

            restaurants.forEach( restaurant -> allRestaurants.add(parseRestaurant( (JSONObject) restaurant ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i < allRestaurants.size(); i++) {
        	filteredRestaurants.add(allRestaurants.get(i));
		}
	}
	
	private Restaurant parseRestaurant(JSONObject restaurant) 
    {
        JSONObject restaurantObject = (JSONObject) restaurant.get("restaurant");

        String name = (String) restaurantObject.get("name");
        String type = (String) restaurantObject.get("type");
        String status = (String) restaurantObject.get("status");
        double length = (double) restaurantObject.get("length");
        double width = (double) restaurantObject.get("width");
        double grade = (double) restaurantObject.get("grade");
        String address = (String) restaurantObject.get("address");
        String logo = (String) restaurantObject.get("logo");
        boolean deleted = (boolean) restaurantObject.get("deleted");
        
        Location location = new Location(length, width, address);
        Restaurant newRestaurant = new Restaurant(name, type, RestaurantStatus.valueOf(status), location, logo, deleted, grade);
        
		return newRestaurant;
    }
	
	public Restaurant saveRestaurant(Restaurant restaurant) throws IOException {
		restaurant.setStatus(RestaurantStatus.OPEN);
		restaurant.setDeleted(false);
		allRestaurants.add(restaurant);
		
		JSONArray restaurants = new JSONArray();
		for (Restaurant r : allRestaurants) {
			JSONObject restaurantObject = new JSONObject();
			
			restaurantObject.put("name", r.getName());
			restaurantObject.put("type", r.getType());
			restaurantObject.put("status", r.getStatus().toString());
			restaurantObject.put("length", r.getLocation().getLength());
			restaurantObject.put("width", r.getLocation().getWidth());
			restaurantObject.put("address", r.getLocation().getAddress());
			restaurantObject.put("logo", r.getLogo());
			restaurantObject.put("deleted", r.isDeleted());
			restaurantObject.put("grade", r.getGrade());
			
			JSONObject restoranObject2 = new JSONObject(); 
	        restoranObject2.put("restaurant", restaurantObject);
			
	        restaurants.add(restoranObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "restaurants.json")) {
            file.write(restaurants.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return restaurant;
	}
	
	public void deleteRestaurant(String name) throws IOException {
		for(int i = 0; i < allRestaurants.size(); i++)
			if(allRestaurants.get(i).getName().equals(name))
				allRestaurants.get(i).setDeleted(true);
		
		JSONArray restaurants = new JSONArray();
		for (Restaurant r : allRestaurants) {
			JSONObject restaurantObject = new JSONObject();
			
			restaurantObject.put("name", r.getName());
			restaurantObject.put("type", r.getType());
			restaurantObject.put("status", r.getStatus().toString());
			restaurantObject.put("length", r.getLocation().getLength());
			restaurantObject.put("width", r.getLocation().getWidth());
			restaurantObject.put("address", r.getLocation().getAddress());
			restaurantObject.put("logo", r.getLogo());
			restaurantObject.put("deleted", r.isDeleted());
			restaurantObject.put("grade", r.getGrade());
			
			JSONObject restoranObject2 = new JSONObject(); 
	        restoranObject2.put("restaurant", restaurantObject);
			
	        restaurants.add(restoranObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "restaurants.json")) {
            file.write(restaurants.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void setViewedRestaurant(String name) throws IOException {
		for(int i = 0; i < allRestaurants.size(); i++)
			if(allRestaurants.get(i).getName().equals(name))
				viewedRestaurant = allRestaurants.get(i);
	}
	
	public double calculateNewGrade(String name) {
		double grade = 0.00;
		int numOfComments = 0;
		double newGrade = 0.00;
		CommentDAO commentDAO = new CommentDAO(this);
		ArrayList<Comment> comments = commentDAO.getCommentsFromRestaurant(name);
		
		for(int i = 0; i < comments.size(); i++) {
			if(comments.get(i).getAccepted()) {
				numOfComments++;
				switch(comments.get(i).getGrade()) {
				case ONE: 
					newGrade = 1;
					break;
				case TWO: 
					newGrade = 2;
					break;
				case THREE: 
					newGrade = 3;
					break;
				case FOUR: 
					newGrade = 4;
					break;
				default:
					newGrade = 5;
				}
				grade += newGrade;
			}
		}
		return grade/numOfComments;
	}
	
	public void updateGrade(String name) throws IOException {
		double newGrade = calculateNewGrade(name);
		for(int i = 0; i < allRestaurants.size(); i++) 
			if(allRestaurants.get(i).getName().equals(name)) 
				allRestaurants.get(i).setGrade(newGrade);
		
		JSONArray restaurants = new JSONArray();
		for (Restaurant r : allRestaurants) {
			JSONObject restaurantObject = new JSONObject();
			
			restaurantObject.put("name", r.getName());
			restaurantObject.put("type", r.getType());
			restaurantObject.put("status", r.getStatus().toString());
			restaurantObject.put("length", r.getLocation().getLength());
			restaurantObject.put("width", r.getLocation().getWidth());
			restaurantObject.put("address", r.getLocation().getAddress());
			restaurantObject.put("logo", r.getLogo());
			restaurantObject.put("deleted", r.isDeleted());
			restaurantObject.put("grade", r.getGrade());
			
			JSONObject restoranObject2 = new JSONObject(); 
	        restoranObject2.put("restaurant", restaurantObject);
			
	        restaurants.add(restoranObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "restaurants.json")) {
            file.write(restaurants.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
