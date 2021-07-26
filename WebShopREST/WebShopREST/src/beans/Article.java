package beans;

public class Article {

	private String name;
	private double price;
	private ArticleType type;
	private Restaurant restaurant;
	private double quantity;
	private String description;
	
	public Article() {
		this.name = "";
		this.price = 0.0;
		this.type = ArticleType.FOOD;
		this.restaurant = new Restaurant();
		this.quantity = 0.0;
		this.description = "";
	}
}
