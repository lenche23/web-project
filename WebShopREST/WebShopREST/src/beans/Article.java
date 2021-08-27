package beans;

public class Article {

	private String logo;
	private String name;
	private double price;
	private ArticleType type;
	private Restaurant restaurant;
	private String quantity;
	private String description;
	private boolean deleted;
	
	public Article() {
		this.name = "";
		this.logo = "";
		this.deleted = false;
		this.price = 0.0;
		this.type = ArticleType.FOOD;
		this.restaurant = new Restaurant();
		this.quantity = "";
		this.description = "";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Article(String logo, String name, double price, ArticleType type, Restaurant restaurant, String quantity,
			String description, boolean deleted) {
		super();
		this.logo = logo;
		this.name = name;
		this.price = price;
		this.type = type;
		this.restaurant = restaurant;
		this.quantity = quantity;
		this.description = description;
		this.deleted = deleted;
	}
	
	public Article(String logo, String name, double price, ArticleType type, String quantity,
			String description, boolean deleted) {
		super();
		this.logo = logo;
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
		this.description = description;
		this.deleted = deleted;
	}
}
