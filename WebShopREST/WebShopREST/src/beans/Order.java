package beans;

import java.util.ArrayList;

public class Order {

	private String id;
	private ArrayList<Article> articles;
	private Restaurant restaurant;
	private String dateAndTime;
	private double price;
	private Buyer buyer;
	private OrderStatus status;
	
	public Order() {
		this.id = "";
		this.articles = new ArrayList<Article>();
		this.restaurant = new Restaurant();
		this.dateAndTime = "";
		this.price = 0.0;
		this.buyer = new Buyer();
		this.status = OrderStatus.PROCESSING;
	}
}
