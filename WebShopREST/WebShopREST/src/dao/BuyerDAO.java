package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.Administrator;
import beans.Buyer;
import beans.BuyerType;
import beans.Sex;
import beans.TypeName;

public class BuyerDAO {
	private ArrayList<Buyer> allBuyers;
	private Buyer loggedInBuyer;
	private ArrayList<Buyer> filteredBuyers;
	private String pathToRepository;
	
	public BuyerDAO() {
		allBuyers = new ArrayList<Buyer>();
		filteredBuyers = new ArrayList<Buyer>();
		loggedInBuyer = new Buyer();
		pathToRepository = "WebContent/Repository/";
		loadBuyers();
	}

	public ArrayList<Buyer> getBuyers() {
		return allBuyers;
	}

	public Buyer getLoggedInBuyer() {
		return loggedInBuyer;
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
        
        for(int i = 0; i < allBuyers.size(); i++) {
        	filteredBuyers.add(allBuyers.get(i));
		}
	}
	
	public Buyer login(String username, String password) {
		for(int i = 0; i < allBuyers.size(); i++)
			if(allBuyers.get(i).getUsername().equals(username) && allBuyers.get(i).getPassword().equals(password)) {
				loggedInBuyer = allBuyers.get(i);
				return loggedInBuyer;
			}
		return null;
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
        boolean blocked = (boolean) buyerObject.get("blocked");
        boolean sus = (boolean) buyerObject.get("sus");
        double points = (double) buyerObject.get("points");
        String typeName = (String) buyerObject.get("name");
        String discount = (String) buyerObject.get("discount");
        String pointsNeeded = (String) buyerObject.get("pointsNeeded");
        
        BuyerType buyerType = new BuyerType(TypeName.valueOf(typeName), Integer.parseInt(discount), Integer.parseInt(pointsNeeded));
        Buyer newBuyer = new Buyer(firstName, lastName, email, username, password, Sex.valueOf(gender), dateOfBirth, deleted, points, buyerType,blocked,sus);
        
		return newBuyer;
    }
	
	public void saveBuyer(Buyer buyer) throws IOException {
		loggedInBuyer = buyer;
		
		buyer.setPoints(0.0);
		buyer.setDeleted(false);
		buyer.setBlocked(false);
		buyer.setSus(false);
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
			buyerObject.put("blocked", b.isBlocked());
			buyerObject.put("sus", b.isSus());
			buyerObject.put("points", b.getPoints());
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
	
	public void deleteBuyer(String username) throws IOException {
		for(int i = 0; i < allBuyers.size(); i++)
			if(allBuyers.get(i).getUsername().equals(username))
				allBuyers.get(i).setDeleted(true);
		
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
			buyerObject.put("blocked", b.isBlocked());
			buyerObject.put("sus", b.isSus());
			buyerObject.put("points", b.getPoints());
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
	
	public void blockBuyer(String username) throws IOException {
		for(int i = 0; i < allBuyers.size(); i++)
			if(allBuyers.get(i).getUsername().equals(username))
				allBuyers.get(i).setBlocked(true);
		
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
			buyerObject.put("blocked", b.isBlocked());
			buyerObject.put("sus", b.isSus());
			buyerObject.put("points", b.getPoints());
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
	
	public ArrayList<Buyer> getFilteredBuyers(String type) {
		filteredBuyers.clear();
		for(int i = 0; i < allBuyers.size(); i++) {
        	filteredBuyers.add(allBuyers.get(i));
		}
		
		if(type.equals("Tip kupca"))
			return allBuyers;
		else {
			Iterator<Buyer> i = filteredBuyers.iterator();
			while(i.hasNext()) {
				Buyer buyer = i.next();
				if(!buyer.getType().getName().toString().equals(type))
					i.remove();
			}
			return filteredBuyers;
		}
	}
	
	public ArrayList<Buyer> getSearchedBuyers(String name, String surname, String username) { 
		ArrayList<Buyer> buyersByName = new ArrayList<Buyer>();
		ArrayList<Buyer> buyersByNameAndSurname = new ArrayList<Buyer>();
		ArrayList<Buyer> buyersByNameSurnameAndUsername = new ArrayList<Buyer>();
		
		if(name.equals("")) {
			for(int i = 0; i < filteredBuyers.size(); i++) {
				buyersByName.add(filteredBuyers.get(i));
			}
		}
		else {
			for(int i = 0; i < filteredBuyers.size(); i++) {
				if(filteredBuyers.get(i).getFirstName().toLowerCase().contains(name.toLowerCase()))
					buyersByName.add(filteredBuyers.get(i));
			}
		}
		
		if(surname.equals("")) {
			for(int i = 0; i < buyersByName.size(); i++) {
				buyersByNameAndSurname.add(buyersByName.get(i));
			}
		}
		else {
			for(int i = 0; i < buyersByName.size(); i++) {
				if(buyersByName.get(i).getLastName().toLowerCase().contains(surname.toLowerCase()))
					buyersByNameAndSurname.add(buyersByName.get(i));
			}
		}
		
		if(username.equals("")) {
			for(int i = 0; i < buyersByNameAndSurname.size(); i++) {
				buyersByNameSurnameAndUsername.add(buyersByNameAndSurname.get(i));
			}
		}
		else {
			for(int i = 0; i < buyersByNameAndSurname.size(); i++) {
				if(buyersByNameAndSurname.get(i).getUsername().toLowerCase().contains(username.toLowerCase()))
					buyersByNameSurnameAndUsername.add(buyersByNameAndSurname.get(i));
			}
		}
		
		return buyersByNameSurnameAndUsername;
	}
	
	public ArrayList<Buyer> getBuyersByUsername(String username) { 
		ArrayList<Buyer> buyersByUsername = new ArrayList<Buyer>();
		
		if(username.equals("")) {
			for(int i = 0; i < allBuyers.size(); i++) {
				buyersByUsername.add(allBuyers.get(i));
			}
		}
		else {
			for(int i = 0; i < allBuyers.size(); i++) {
				if(allBuyers.get(i).getUsername().toLowerCase().contains(username.toLowerCase()))
					buyersByUsername.add(allBuyers.get(i));
			}
		}
		
		return buyersByUsername;
	}
	
	public void saveProfileChanges(String username, Buyer buyer) throws IOException {
		for(Buyer b : allBuyers) {
			if(b.getUsername().equals(username)) {
				b.setPassword(buyer.getPassword());
				b.setDateOfBirth(buyer.getDateOfBirth());
				b.setEmail(buyer.getEmail());
				b.setFirstName(buyer.getFirstName());
				b.setGender(buyer.getGender());
				b.setLastName(buyer.getLastName());
			}
		}
		
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
			buyerObject.put("blocked", b.isBlocked());
			buyerObject.put("sus", b.isSus());
			buyerObject.put("points", b.getPoints());
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
	
	public void addPoints(double price) throws IOException {
		double points = 0.133 * price + loggedInBuyer.getPoints();
		DecimalFormat df = new DecimalFormat("#.##");      
		points = Double.valueOf(df.format(points));
		
		loggedInBuyer.setPoints(points);
		if(loggedInBuyer.getPoints() < 3000) {
			loggedInBuyer.setType(new BuyerType(TypeName.BRONZE, 0, 3000));
		}
		else if(loggedInBuyer.getPoints() >= 4000) {
			loggedInBuyer.setType(new BuyerType(TypeName.GOLDEN, 5, 4000));
		}
		else {
			loggedInBuyer.setType(new BuyerType(TypeName.SILVER, 3, 4000));
		}
		
		for(Buyer b : allBuyers) {
			if(b.getUsername().equals(loggedInBuyer.getUsername())) {
				b.setPoints(loggedInBuyer.getPoints());
				b.setType(loggedInBuyer.getType());
			}
		}
		
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
			buyerObject.put("blocked", b.isBlocked());
			buyerObject.put("sus", b.isSus());
			buyerObject.put("points", b.getPoints());
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
	
	public void removePoints(double price) throws IOException {
		double points = loggedInBuyer.getPoints() - 0.532 * price;
		if(points < 0)
			points = 0;
		DecimalFormat df = new DecimalFormat("#.##");      
		points = Double.valueOf(df.format(points));
		
		loggedInBuyer.setPoints(points);
		if(loggedInBuyer.getPoints() < 3000) {
			loggedInBuyer.setType(new BuyerType(TypeName.BRONZE, 0, 3000));
		}
		else if(loggedInBuyer.getPoints() >= 4000) {
			loggedInBuyer.setType(new BuyerType(TypeName.GOLDEN, 5, 4000));
		}
		else {
			loggedInBuyer.setType(new BuyerType(TypeName.SILVER, 3, 4000));
		}
		
		for(Buyer b : allBuyers) {
			if(b.getUsername().equals(loggedInBuyer.getUsername())) {
				b.setPoints(loggedInBuyer.getPoints());
				b.setType(loggedInBuyer.getType());
			}
		}
		
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
			buyerObject.put("blocked", b.isBlocked());
			buyerObject.put("sus", b.isSus());
			buyerObject.put("points", b.getPoints());
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
}
