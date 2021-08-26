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
import beans.Buyer;
import beans.Manager;
import beans.Sex;

public class ManagerDAO {
	private ArrayList<Manager> allManagers;
	private Manager loggedInManager;
	private String pathToRepository;
	
	public ManagerDAO(RestaurantDAO restaurantDAO) {
		allManagers = new ArrayList<Manager>();
		loggedInManager = new Manager();
		pathToRepository = "WebContent/Repository/";
		loadManagers(restaurantDAO);
	}
	
	public Manager getLoggedInManager() {
		return loggedInManager;
	}

	public ArrayList<Manager> getManagers() {
		return allManagers;
	}
	
	public void loadManagers(RestaurantDAO restaurantDAO) {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "managers.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray managers = (JSONArray) object;

            managers.forEach( manager -> allManagers.add(parseManager( (JSONObject) manager, restaurantDAO ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public Manager login(String username, String password) {
		for(int i = 0; i < allManagers.size(); i++)
			if(allManagers.get(i).getUsername().equals(username) && allManagers.get(i).getPassword().equals(password)) {
				loggedInManager = allManagers.get(i);
				return loggedInManager;
			}
		return null;
	}
	
	private Manager parseManager(JSONObject manager, RestaurantDAO restaurantDAO) 
    {
        JSONObject managerObject = (JSONObject) manager.get("manager");

        String firstName = (String) managerObject.get("firstName");
        String lastName = (String) managerObject.get("lastName");
        String email = (String) managerObject.get("email");
        String username = (String) managerObject.get("username");
        String password = (String) managerObject.get("password");
        String gender = (String) managerObject.get("gender");
        String dateOfBirth = (String) managerObject.get("dateOfBirth");
        boolean deleted = (boolean) managerObject.get("deleted");
        
        Manager newManager = new Manager(firstName, lastName, email, username, password, Sex.valueOf(gender), dateOfBirth, deleted);
        
        if(managerObject.get("restaurant") != null) {
        	String name = (String) managerObject.get("restaurant");
        	for(int i = 0; i < restaurantDAO.getAllRestaurants().size(); i++)
        		if(restaurantDAO.getAllRestaurants().get(i).getName().equals(name))
        			newManager.setRestaurant(restaurantDAO.getAllRestaurants().get(i));
        }
        
		return newManager;
    }
	
	public void saveManager(Manager manager) throws IOException {
		manager.setDeleted(false);
		allManagers.add(manager);
		
		JSONArray managers = new JSONArray();
		for (Manager m : allManagers) {
			JSONObject managerObject = new JSONObject();
			
			managerObject.put("firstName", m.getFirstName());
			managerObject.put("lastName", m.getLastName());
			managerObject.put("email", m.getEmail());
			managerObject.put("username", m.getUsername());
			managerObject.put("password", m.getPassword());
			managerObject.put("gender", m.getGender().toString());
			managerObject.put("dateOfBirth", m.getDateOfBirth());
			managerObject.put("deleted", m.isDeleted());
			if(m.getRestaurant().getName() != "")
				managerObject.put("restaurant", m.getRestaurant().getName());
			else
				managerObject.put("restaurant", null);
			
			JSONObject managerObject2 = new JSONObject(); 
	        managerObject2.put("manager", managerObject);
			
	        managers.add(managerObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "managers.json")) {
            file.write(managers.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void saveProfileChanges(String username, Manager manager) throws IOException {
		for(Manager m : allManagers) {
			if(m.getUsername().equals(username)) {
				m.setPassword(manager.getPassword());
				m.setDateOfBirth(manager.getDateOfBirth());
				m.setEmail(manager.getEmail());
				m.setFirstName(manager.getFirstName());
				m.setGender(manager.getGender());
				m.setLastName(manager.getLastName());
			}
		}
		
		JSONArray managers = new JSONArray();
		for (Manager m : allManagers) {
			JSONObject managerObject = new JSONObject();
			
			managerObject.put("firstName", m.getFirstName());
			managerObject.put("lastName", m.getLastName());
			managerObject.put("email", m.getEmail());
			managerObject.put("username", m.getUsername());
			managerObject.put("password", m.getPassword());
			managerObject.put("gender", m.getGender().toString());
			managerObject.put("dateOfBirth", m.getDateOfBirth());
			managerObject.put("deleted", m.isDeleted());
			
			JSONObject managerObject2 = new JSONObject(); 
	        managerObject2.put("manager", managerObject);
			
	        managers.add(managerObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "managers.json")) {
            file.write(managers.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void addManagerToRestaurant(String username, String name, RestaurantDAO restaurantDAO) throws IOException {
		for(int i = 0; i < allManagers.size(); i++)
			if(allManagers.get(i).getUsername().equals(username))
				for(int j = 0; j < restaurantDAO.getAllRestaurants().size(); j++)
					if(restaurantDAO.getAllRestaurants().get(j).getName().equals(name))
						allManagers.get(i).setRestaurant(restaurantDAO.getAllRestaurants().get(j));

		JSONArray managers = new JSONArray();
		for (Manager m : allManagers) {
			JSONObject managerObject = new JSONObject();
			
			managerObject.put("firstName", m.getFirstName());
			managerObject.put("lastName", m.getLastName());
			managerObject.put("email", m.getEmail());
			managerObject.put("username", m.getUsername());
			managerObject.put("password", m.getPassword());
			managerObject.put("gender", m.getGender().toString());
			managerObject.put("dateOfBirth", m.getDateOfBirth());
			managerObject.put("deleted", m.isDeleted());
			if(m.getRestaurant().getName() != "")
				managerObject.put("restaurant", m.getRestaurant().getName());
			else
				managerObject.put("restaurant", null);
			JSONObject managerObject2 = new JSONObject(); 
	        managerObject2.put("manager", managerObject);
			
	        managers.add(managerObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "managers.json")) {
            file.write(managers.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void deleteManager(String username) throws IOException {
		for(int i = 0; i < allManagers.size(); i++)
			if(allManagers.get(i).getUsername().equals(username))
				allManagers.get(i).setDeleted(true);
		
		JSONArray managers = new JSONArray();
		for (Manager m : allManagers) {
			JSONObject managerObject = new JSONObject();
			
			managerObject.put("firstName", m.getFirstName());
			managerObject.put("lastName", m.getLastName());
			managerObject.put("email", m.getEmail());
			managerObject.put("username", m.getUsername());
			managerObject.put("password", m.getPassword());
			managerObject.put("gender", m.getGender().toString());
			managerObject.put("dateOfBirth", m.getDateOfBirth());
			managerObject.put("deleted", m.isDeleted());
			
			JSONObject managerObject2 = new JSONObject(); 
	        managerObject2.put("manager", managerObject);
			
	        managers.add(managerObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "managers.json")) {
            file.write(managers.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Manager> getSearchedManagers(String name, String surname, String username) { 
		ArrayList<Manager> managersByName = new ArrayList<Manager>();
		ArrayList<Manager> managersByNameAndSurname = new ArrayList<Manager>();
		ArrayList<Manager> managersByNameSurnameAndUsername = new ArrayList<Manager>();
		
		if(name.equals("")) {
			for(int i = 0; i < allManagers.size(); i++) {
				managersByName.add(allManagers.get(i));
			}
		}
		else {
			for(int i = 0; i < allManagers.size(); i++) {
				if(allManagers.get(i).getFirstName().toLowerCase().contains(name.toLowerCase()))
					managersByName.add(allManagers.get(i));
			}
		}
		
		if(surname.equals("")) {
			for(int i = 0; i < managersByName.size(); i++) {
				managersByNameAndSurname.add(managersByName.get(i));
			}
		}
		else {
			for(int i = 0; i < managersByName.size(); i++) {
				if(managersByName.get(i).getLastName().toLowerCase().contains(surname.toLowerCase()))
					managersByNameAndSurname.add(managersByName.get(i));
			}
		}
		
		if(username.equals("")) {
			for(int i = 0; i < managersByNameAndSurname.size(); i++) {
				managersByNameSurnameAndUsername.add(managersByNameAndSurname.get(i));
			}
		}
		else {
			for(int i = 0; i < managersByNameAndSurname.size(); i++) {
				if(managersByNameAndSurname.get(i).getUsername().toLowerCase().contains(username.toLowerCase()))
					managersByNameSurnameAndUsername.add(managersByNameAndSurname.get(i));
			}
		}
		
		return managersByNameSurnameAndUsername;
	}
}
