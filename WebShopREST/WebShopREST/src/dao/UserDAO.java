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
import beans.BuyerType;
import beans.Deliverer;
import beans.Manager;
import beans.Sex;
import beans.TypeName;

public class UserDAO {
	private ArrayList<Buyer> allBuyers;
	private ArrayList<Manager> allManagers;
	private ArrayList<Deliverer> allDeliverers;
	private String pathToRepository;
	
	public UserDAO() {
		allBuyers = new ArrayList<Buyer>();
		allManagers = new ArrayList<Manager>();
		allDeliverers = new ArrayList<Deliverer>();
		pathToRepository = "WebContent/Repository/";
		loadBuyers();
		loadManagers();
		loadDeliverers();
	}

	public ArrayList<Buyer> getBuyers() {
		return allBuyers;
	}

	public ArrayList<Manager> getManagers() {
		return allManagers;
	}

	public ArrayList<Deliverer> getDeliverers() {
		return allDeliverers;
	}
	
	public void loadBuyers() {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "buyers.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray buyers = (JSONArray) object;

            buyers.forEach( buyer -> allBuyers.add(parseBuyer( (JSONObject) buyer ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Buyer parseBuyer(JSONObject buyer) 
    {
        JSONObject buyerObject = (JSONObject) buyer.get("buyer");

        String firstName = (String) buyerObject.get("firstName");
        String lastName = (String) buyerObject.get("lastName");
        String email = (String) buyerObject.get("email");
        String username = (String) buyerObject.get("username");
        String password = (String) buyerObject.get("password");
        String gender = (String) buyerObject.get("gender");
        String dateOfBirth = (String) buyerObject.get("dateOfBirth");
        boolean deleted = (boolean) buyerObject.get("deleted");
        String points = (String) buyerObject.get("points");
        String typeName = (String) buyerObject.get("name");
        String discount = (String) buyerObject.get("discount");
        String pointsNeeded = (String) buyerObject.get("pointsNeeded");
        
        BuyerType buyerType = new BuyerType(TypeName.valueOf(typeName), Integer.parseInt(discount), Integer.parseInt(pointsNeeded));
        Buyer newBuyer = new Buyer(firstName, lastName, email, username, password, Sex.valueOf(gender), dateOfBirth, deleted, Integer.parseInt(points), buyerType);
        
		return newBuyer;
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
	
	public void saveBuyer(Buyer buyer) throws IOException {
		buyer.setPoints(0);
		buyer.setDeleted(false);
		buyer.setType(new BuyerType(TypeName.BRONZE, 0, 3000));
		allBuyers.add(buyer);
		
		JSONArray buyers = new JSONArray();
		for (Buyer b : allBuyers) {
			JSONObject buyerObject = new JSONObject();
			
			buyerObject.put("firstName", b.getFirstName());
			buyerObject.put("lastName", b.getLastName());
			buyerObject.put("email", b.getEmail());
			buyerObject.put("username", b.getUsername());
			buyerObject.put("password", b.getPassword());
			buyerObject.put("gender", b.getGender().toString());
			buyerObject.put("dateOfBirth", b.getDateOfBirth());
			buyerObject.put("deleted", b.isDeleted());
			buyerObject.put("points", Integer.toString(b.getPoints()));
			buyerObject.put("name", b.getType().getName().toString());
			buyerObject.put("discount", Integer.toString(b.getType().getDiscount()));
			buyerObject.put("pointsNeeded", Integer.toString(b.getType().getPointsNeeded()));
			
			JSONObject buyerObject2 = new JSONObject(); 
	        buyerObject2.put("buyer", buyerObject);
			
	        buyers.add(buyerObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "buyers.json")) {
            file.write(buyers.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
