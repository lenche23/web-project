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

import beans.Location;
import beans.Restaurant;
import beans.RestaurantStatus;

public class RestaurantDAO {
	private ArrayList<Restaurant> allRestaurants;
	private ArrayList<Restaurant> filteredRestaurants;
	private String pathToRepository;
	
	public RestaurantDAO() {
		allRestaurants = new ArrayList<Restaurant>();
		filteredRestaurants = new ArrayList<Restaurant>();
		pathToRepository = "WebContent/Repository/";
		loadRestaurants();
	}
	
	public ArrayList<Restaurant> getAllRestaurants() {
		return allRestaurants;
	}
	
	public ArrayList<Restaurant> getFilteredRestaurants(String type, String open) {
		filteredRestaurants.clear();
		
		if(type.equals("0") && open.equals("false"))
			return allRestaurants;
		else if(!type.equals("0") && open.equals("false")) {
			for(int i = 0; i < allRestaurants.size(); i++) {
				if(allRestaurants.get(i).getType().equals(type))
					filteredRestaurants.add(allRestaurants.get(i));
			}
			return filteredRestaurants;
		}
		else if(type.equals("0") && open.equals("true")) {
			for(int i = 0; i < allRestaurants.size(); i++) {
				if(RestaurantStatus.OPEN == allRestaurants.get(i).getStatus())
					filteredRestaurants.add(allRestaurants.get(i));
			}
			return filteredRestaurants;
		}
		else {
			for(int i = 0; i < allRestaurants.size(); i++) {
				if(allRestaurants.get(i).getType().equals(type) && RestaurantStatus.OPEN == allRestaurants.get(i).getStatus())
					filteredRestaurants.add(allRestaurants.get(i));
			}
			return filteredRestaurants;
		}
	}
	
	public ArrayList<Restaurant> getSearchedRestaurants(String name, String location, String gradeStr, String type) { 
		ArrayList<Restaurant> restaurantsByName = new ArrayList<Restaurant>();
		ArrayList<Restaurant> restaurantsByNameAndLocation = new ArrayList<Restaurant>();
		ArrayList<Restaurant> restaurantsByNameLocationAndGrade = new ArrayList<Restaurant>();
		ArrayList<Restaurant> restaurantsByNameLocationGradeAndType = new ArrayList<Restaurant>();
		if(filteredRestaurants.isEmpty()) {
			if(name.equals("")) {
				for(int i = 0; i < allRestaurants.size(); i++) {
					restaurantsByName.add(allRestaurants.get(i));
				}
			}
			else {
				for(int i = 0; i < allRestaurants.size(); i++) {
					if(allRestaurants.get(i).getName().toLowerCase().contains(name.toLowerCase()))
						restaurantsByName.add(allRestaurants.get(i));
				}
			}
		}
		else {
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
		
		if(type.equals("0")) {
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
	}
	
	private Restaurant parseRestaurant(JSONObject restaurant) 
    {
        JSONObject restaurantObject = (JSONObject) restaurant.get("restaurant");

        String name = (String) restaurantObject.get("name");
        String type = (String) restaurantObject.get("type");
        String status = (String) restaurantObject.get("status");
        double length = (double) restaurantObject.get("length");
        double width = (double) restaurantObject.get("width");
        String address = (String) restaurantObject.get("address");
        String logo = (String) restaurantObject.get("logo");
        boolean deleted = (boolean) restaurantObject.get("deleted");
        
        Location location = new Location(length, width, address);
        Restaurant newRestaurant = new Restaurant(name, type, RestaurantStatus.valueOf(status), location, logo, deleted);
        
		return newRestaurant;
    }
	
	public void saveRestaurant(Restaurant restaurant) throws IOException {
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
