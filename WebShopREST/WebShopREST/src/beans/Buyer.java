package beans;

import java.util.ArrayList;

public class Buyer extends User {

	private ArrayList<Order> orders;
	private Basket basket;
	private int points;
	private BuyerType type;
	
	public Buyer() {
		super();
		this.orders = new ArrayList<Order>();
		this.basket = new Basket();
		this.points = 0;
		this.type = new BuyerType();
	}
}
