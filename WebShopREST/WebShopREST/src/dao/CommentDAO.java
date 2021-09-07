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

import beans.Comment;
import beans.Grade;

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
			if(allComments.get(i).getRestaurantName().equals(name))
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

        String id =  String.valueOf(commentObject.get("id"));
        String buyer = (String) commentObject.get("buyerUsername");
        String restaurant = (String) commentObject.get("restaurantName");
        String content = (String) commentObject.get("content");
        String grade = (String) commentObject.get("grade");
        boolean deleted = (boolean) commentObject.get("deleted");
        
        Comment newComment = new Comment(Integer.parseInt(id), buyer, restaurant, content, Grade.valueOf(grade), deleted);
        
		return newComment;
    }
	
	public void saveComment(Comment comment) throws IOException {
		comment.setDeleted(false);
		comment.setId(getNumOfComments()+1);
		allComments.add(comment);
		
		JSONArray comments = new JSONArray();
		for (Comment a : allComments) {
			JSONObject commentObject = new JSONObject();
			
			commentObject.put("id", a.getId());
			commentObject.put("buyer", a.getBuyerUsername());
			commentObject.put("restaurant", a.getRestaurantName());
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
			if(allComments.get(i).getId() == commentId && allComments.get(i).getRestaurantName().equals(restaurantName))
				allComments.get(i).setDeleted(true);
		
		JSONArray comments = new JSONArray();
		for (Comment a : allComments) {
			JSONObject commentObject = new JSONObject();
			
			commentObject.put("id", a.getId());
			commentObject.put("buyer", a.getBuyerUsername());
			commentObject.put("restaurant", a.getRestaurantName());
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
