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

import beans.Deliverer;
import beans.Sex;

public class DelivererDAO {
	private ArrayList<Deliverer> allDeliverers;
	private String pathToRepository;
	
	public DelivererDAO() {
		allDeliverers = new ArrayList<Deliverer>();
		pathToRepository = "WebContent/Repository/";
		loadDeliverers();
	}
	
	public ArrayList<Deliverer> getDeliverers() {
		return allDeliverers;
	}
	
	public void loadDeliverers() {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "deliverers.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray deliverers = (JSONArray) object;

            deliverers.forEach( deliverer -> allDeliverers.add(parseDeliverer( (JSONObject) deliverer ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Deliverer parseDeliverer(JSONObject deliverer) 
    {
        JSONObject delivererObject = (JSONObject) deliverer.get("deliverer");

        String firstName = (String) delivererObject.get("firstName");
        String lastName = (String) delivererObject.get("lastName");
        String email = (String) delivererObject.get("email");
        String username = (String) delivererObject.get("username");
        String password = (String) delivererObject.get("password");
        String gender = (String) delivererObject.get("gender");
        String dateOfBirth = (String) delivererObject.get("dateOfBirth");
        boolean deleted = (boolean) delivererObject.get("deleted");
        
        Deliverer newDeliverer = new Deliverer(firstName, lastName, email, username, password, Sex.valueOf(gender), dateOfBirth, deleted);
        
		return newDeliverer;
    }
	
	public void saveDeliverer(Deliverer deliverer) throws IOException {
		deliverer.setDeleted(false);
		allDeliverers.add(deliverer);
		
		JSONArray deliverers = new JSONArray();
		for (Deliverer d : allDeliverers) {
			JSONObject delivererObject = new JSONObject();
			
			delivererObject.put("firstName", d.getFirstName());
			delivererObject.put("lastName", d.getLastName());
			delivererObject.put("email", d.getEmail());
			delivererObject.put("username", d.getUsername());
			delivererObject.put("password", d.getPassword());
			delivererObject.put("gender", d.getGender().toString());
			delivererObject.put("dateOfBirth", d.getDateOfBirth());
			delivererObject.put("deleted", d.isDeleted());
			
			JSONObject delivererObject2 = new JSONObject(); 
	        delivererObject2.put("deliverer", delivererObject);
			
	        deliverers.add(delivererObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "deliverers.json")) {
            file.write(deliverers.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
