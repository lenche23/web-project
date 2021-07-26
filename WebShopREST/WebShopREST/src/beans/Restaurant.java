package beans;

import java.util.ArrayList;

public class Restaurant {

	private String name;
	private String type;
	private ArrayList<Article> availableArticles;
	private RestaurantStatus status;
	private Location location;
	
	public Restaurant() {
		this.name = "";
		this.type = "";
		this.availableArticles = new ArrayList<Article>();
		this.status = RestaurantStatus.OPENED;
		this.location = new Location();
	}
}
