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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Article> getAvailableArticles() {
		return availableArticles;
	}

	public void setAvailableArticles(ArrayList<Article> availableArticles) {
		this.availableArticles = availableArticles;
	}

	public RestaurantStatus getStatus() {
		return status;
	}

	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availableArticles == null) ? 0 : availableArticles.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		if (availableArticles == null) {
			if (other.availableArticles != null)
				return false;
		} else if (!availableArticles.equals(other.availableArticles))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Restaurant [name=" + name + ", type=" + type + ", availableArticles=" + availableArticles + ", status="
				+ status + ", location=" + location + "]";
	}

	public Restaurant(String name, String type, ArrayList<Article> availableArticles, RestaurantStatus status,
			Location location) {
		super();
		this.name = name;
		this.type = type;
		this.availableArticles = availableArticles;
		this.status = status;
		this.location = location;
	}
	
	
}
