package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.Administrator;
import beans.Buyer;
import beans.Deliverer;
import beans.Manager;
import beans.Sex;

public class AdministratorDAO {
	private ArrayList<Administrator> allAdministrators;
	private Administrator loggedInAdministrator;
	private String pathToRepository;

	public AdministratorDAO() {
		allAdministrators = new ArrayList<Administrator>();
		loggedInAdministrator = new Administrator();
		pathToRepository = "WebContent/Repository/";
		loadAdministrators();
	}
	
	public void loadAdministrators() {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "administrators.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray administrators = (JSONArray) object;

            administrators.forEach( administrator -> allAdministrators.add(parseAdministrator( (JSONObject) administrator ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Administrator parseAdministrator(JSONObject administrator) 
    {
        JSONObject administratorObject = (JSONObject) administrator.get("administrator");

        String firstName = (String) administratorObject.get("firstName");
        String lastName = (String) administratorObject.get("lastName");
        String email = (String) administratorObject.get("email");
        String username = (String) administratorObject.get("username");
        String password = (String) administratorObject.get("password");
        String gender = (String) administratorObject.get("gender");
        String dateOfBirth = (String) administratorObject.get("dateOfBirth");
        boolean deleted = (boolean) administratorObject.get("deleted");
        boolean blocked = (boolean) administratorObject.get("blocked");
        boolean sus = (boolean) administratorObject.get("sus");
        
        Administrator newAdministrator = new Administrator(firstName, lastName, email, username, password, Sex.valueOf(gender), dateOfBirth, deleted,blocked,sus);
		return newAdministrator;
    }
	
	public void saveProfileChanges(String username, Administrator admin) throws IOException {
		for(Administrator a : allAdministrators) {
			if(a.getUsername().equals(username)) {
				a.setPassword(admin.getPassword());
				a.setDateOfBirth(admin.getDateOfBirth());
				a.setEmail(admin.getEmail());
				a.setFirstName(admin.getFirstName());
				a.setGender(admin.getGender());
				a.setLastName(admin.getLastName());
			}
		}
		
		JSONArray administrators = new JSONArray();
		for (Administrator a : allAdministrators) {
			JSONObject administratorObject = new JSONObject();
			
			administratorObject.put("firstName", a.getFirstName());
			administratorObject.put("lastName", a.getLastName());
			administratorObject.put("email", a.getEmail());
			administratorObject.put("username", a.getUsername());
			administratorObject.put("password", a.getPassword());
			administratorObject.put("gender", a.getGender().toString());
			administratorObject.put("dateOfBirth", a.getDateOfBirth());
			administratorObject.put("deleted", a.isDeleted());
			administratorObject.put("blocked", a.isBlocked());
			administratorObject.put("sus", a.isSus());
			
			
			JSONObject administratorObject2 = new JSONObject(); 
	        administratorObject2.put("administrator", administratorObject);
			
	        administrators.add(administratorObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "administrators.json")) {
            file.write(administrators.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Administrator getLoggedInAdministrator() {
		return loggedInAdministrator;
	}

	public ArrayList<Administrator> getAllAdministrators() {
		return allAdministrators;
	}

	public Administrator login(String username, String password) {
		for(int i = 0; i < allAdministrators.size(); i++)
			if(allAdministrators.get(i).getUsername().equals(username) && allAdministrators.get(i).getPassword().equals(password)) {
				loggedInAdministrator = allAdministrators.get(i);
				return loggedInAdministrator;
			}
		return null;
	}
	
	public ArrayList<Administrator> getSearchedAdministrators(String name, String surname, String username) {
		ArrayList<Administrator> administratorsByName = new ArrayList<Administrator>();
		ArrayList<Administrator> administratorsByNameAndSurname = new ArrayList<Administrator>();
		ArrayList<Administrator> administratorsByNameSurnameAndUsername = new ArrayList<Administrator>();

		if (name.equals("")) {
			for(int i = 0; i < allAdministrators.size(); i++) {
				administratorsByName.add(allAdministrators.get(i));
			}
		} else {
			for (int i = 0; i < allAdministrators.size(); i++) {
				if (allAdministrators.get(i).getFirstName().toLowerCase().contains(name.toLowerCase()))
					administratorsByName.add(allAdministrators.get(i));
			}
		}

		if (surname.equals("")) {
			for (int i = 0; i < administratorsByName.size(); i++) {
				administratorsByNameAndSurname.add(administratorsByName.get(i));
			}
		} else {
			for (int i = 0; i < administratorsByName.size(); i++) {
				if (administratorsByName.get(i).getLastName().toLowerCase().contains(surname.toLowerCase()))
					administratorsByNameAndSurname.add(administratorsByName.get(i));
			}
		}

		if (username.equals("")) {
			for (int i = 0; i < administratorsByNameAndSurname.size(); i++) {
				administratorsByNameSurnameAndUsername.add(administratorsByNameAndSurname.get(i));
			}
		} else {
			for (int i = 0; i < administratorsByNameAndSurname.size(); i++) {
				if (administratorsByNameAndSurname.get(i).getUsername().toLowerCase().contains(username.toLowerCase()))
					administratorsByNameSurnameAndUsername.add(administratorsByNameAndSurname.get(i));
			}
		}

		return administratorsByNameSurnameAndUsername;
	}
}
