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
import beans.Sex;
import beans.TypeName;

public class BuyerDAO {
	private ArrayList<Buyer> allBuyers;
	private String pathToRepository;
	
	public BuyerDAO() {
		allBuyers = new ArrayList<Buyer>();
		pathToRepository = "WebContent/Repository/";
		loadBuyers();
	}

	public ArrayList<Buyer> getBuyers() {
		return allBuyers;
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
}
