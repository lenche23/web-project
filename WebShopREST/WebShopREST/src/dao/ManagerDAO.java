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

import beans.Buyer;
import beans.Manager;
import beans.Sex;

public class ManagerDAO {
	private ArrayList<Manager> allManagers;
	private String pathToRepository;
	
	public ManagerDAO() {
		allManagers = new ArrayList<Manager>();
		pathToRepository = "WebContent/Repository/";
		loadManagers();
	}
	
	public ArrayList<Manager> getManagers() {
		return allManagers;
	}
	
	public void loadManagers() {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "managers.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray managers = (JSONArray) object;

            managers.forEach( manager -> allManagers.add(parseManager( (JSONObject) manager ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Manager parseManager(JSONObject manager) 
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
