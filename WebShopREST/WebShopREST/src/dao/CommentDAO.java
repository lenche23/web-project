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
import beans.Comment;
import beans.Grade;
import beans.Restaurant;

public class CommentDAO {

	private ArrayList<Comment> allComments;
	private String pathToRepository;
	
	public CommentDAO(RestaurantDAO restaurantDAO) {
		allComments = new ArrayList<Comment>();
		pathToRepository = "WebContent/Repository/";
		loadComments(restaurantDAO);
	}
	
	public ArrayList<Comment> getAllComments() {
		return allComments;
	}
	
	public ArrayList<Comment> getCommentsFromRestaurant(String name) {
		ArrayList<Comment> commentsFromRestaurant = new ArrayList<Comment>();
		for(int i = 0; i < allComments.size(); i++)
			if(allComments.get(i).getRestaurant().getName().equals(name))
				commentsFromRestaurant.add(allComments.get(i));
		return commentsFromRestaurant;
	}
	
	public int getNumOfComments() {
		return allComments.size();
	}

	public void loadComments(RestaurantDAO restaurantDAO) {
		JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(pathToRepository + "comments.json"))
        {
            Object object = jsonParser.parse(reader);

            JSONArray comments = (JSONArray) object;

            comments.forEach( comment -> allComments.add(parseComment( (JSONObject) comment, restaurantDAO ) ));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private Comment parseComment(JSONObject comment, RestaurantDAO restaurantDAO) 
    {
        JSONObject commentObject = (JSONObject) comment.get("comment");

        int id = (int) commentObject.get("id");
        Buyer buyer = (Buyer) commentObject.get("buyer");
        String content = (String) commentObject.get("content");
        String grade = (String) commentObject.get("grade");
        boolean deleted = (boolean) commentObject.get("deleted");
        
        Comment newComment = new Comment(id, buyer, content, Grade.valueOf(grade), deleted);
        
        String restaurantName = (String) commentObject.get("restaurant");
    	for(int i = 0; i < restaurantDAO.getAllRestaurants().size(); i++)
    		if(restaurantDAO.getAllRestaurants().get(i).getName().equals(restaurantName))
    			newComment.setRestaurant(restaurantDAO.getAllRestaurants().get(i));
        
		return newComment;
    }
	
	public void saveComment(Comment comment) throws IOException {
		comment.setDeleted(false);
		allComments.add(comment);
		
		JSONArray comments = new JSONArray();
		for (Comment a : allComments) {
			JSONObject commentObject = new JSONObject();
			
			commentObject.put("id", a.getId());
			commentObject.put("buyer", a.getBuyer());
			commentObject.put("restaurant", a.getRestaurant().getName());
			commentObject.put("content", a.getContent());
			commentObject.put("grade", a.getGrade().toString());
			commentObject.put("deleted", a.getDeleted());
			
			
			JSONObject commentObject2 = new JSONObject(); 
			commentObject2.put("comment", commentObject);
			
	        comments.add(commentObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "comments.json")) {
            file.write(comments.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void deleteComment(String restaurantName, int commentId) throws IOException {
		for(int i = 0; i < allComments.size(); i++)
			if(allComments.get(i).getId() == commentId && allComments.get(i).getRestaurant().getName().equals(restaurantName))
				allComments.get(i).setDeleted(true);
		
		JSONArray comments = new JSONArray();
		for (Comment a : allComments) {
			JSONObject commentObject = new JSONObject();
			
			commentObject.put("id", a.getId());
			commentObject.put("buyer", a.getBuyer());
			commentObject.put("restaurant", a.getRestaurant().getName());
			commentObject.put("content", a.getContent());
			commentObject.put("grade", a.getGrade().toString());
			commentObject.put("deleted", a.getDeleted());
			
			
			JSONObject commentObject2 = new JSONObject(); 
			commentObject2.put("comment", commentObject);
			
	        comments.add(commentObject2);
		}
         
        try (FileWriter file = new FileWriter(pathToRepository + "comments.json")) {
            file.write(comments.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
