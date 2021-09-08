package beans;

import java.util.ArrayList;
import java.util.Objects;

public class Restaurant {

	private String name;
	private String type;
	private ArrayList<Article> availableArticles;
	private RestaurantStatus status;
	private Location location;
	private String logo;
	private boolean deleted;
	private double grade;

	public Restaurant() {
		this.name = "";
		this.type = "";
		this.logo = "";
		this.status = RestaurantStatus.OPEN;
		this.deleted = false;
		this.availableArticles = new ArrayList<Article>();;
		this.location = new Location();
		this.grade = 0.00;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public RestaurantStatus getStatus() {
		return status;
	}

	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Restaurant [name=" + name + ", type=" + type + ", availableArticles=" + availableArticles + ", status="
				+ status + ", location=" + location + ", logo=" + logo + ", deleted=" + deleted + ", grade=" + grade
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(availableArticles, deleted, grade, location, logo, name, status, type);
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
		return Objects.equals(availableArticles, other.availableArticles) && deleted == other.deleted
				&& Double.doubleToLongBits(grade) == Double.doubleToLongBits(other.grade)
				&& Objects.equals(location, other.location) && Objects.equals(logo, other.logo)
				&& Objects.equals(name, other.name) && status == other.status && Objects.equals(type, other.type);
	}

	public Restaurant(String name, String type, ArrayList<Article> availableArticles, RestaurantStatus status,
			Location location, String logo, boolean deleted) {
		super();
		this.name = name;
		this.type = type;
		this.availableArticles = availableArticles;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.deleted = deleted;
	}
	
	public Restaurant(String name, String type, RestaurantStatus status, Location location, String logo, boolean deleted, double grade) {
		super();
		this.name = name;
		this.type = type;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.deleted = deleted;
		this.grade = grade;
	}

	public Restaurant(String name, String type, ArrayList<Article> availableArticles, RestaurantStatus status,
			Location location, String logo, boolean deleted, double grade) {
		super();
		this.name = name;
		this.type = type;
		this.availableArticles = availableArticles;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.deleted = deleted;
		this.grade = grade;
	}
}
