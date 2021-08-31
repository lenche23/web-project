package beans;

import java.util.ArrayList;

public class Buyer extends User {

	private static final long serialVersionUID = 1L;
			
	private ArrayList<Order> orders;
	private double points;
	private BuyerType type;
	
	public Buyer() {
		super();
		this.orders = new ArrayList<Order>();
		this.points = 0.0;
		this.type = new BuyerType();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public BuyerType getType() {
		return type;
	}

	public void setType(BuyerType type) {
		this.type = type;
	}
	
	public Buyer(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted, double points, BuyerType type) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted);
		this.points = points;
		this.type = type;
	}

	public Buyer(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted, ArrayList<Order> orders, Basket basket, double points, BuyerType type) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted);
		this.orders = orders;
		this.points = points;
		this.type = type;
	}
}
