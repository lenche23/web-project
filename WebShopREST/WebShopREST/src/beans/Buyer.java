package beans;

import java.util.ArrayList;

public class Buyer extends User {

	private static final long serialVersionUID = 1L;
			
	private ArrayList<Order> orders;
	private int points;
	private BuyerType type;
	
	public Buyer() {
		super();
		this.orders = new ArrayList<Order>();
		this.points = 0;
		this.type = new BuyerType();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public BuyerType getType() {
		return type;
	}

	public void setType(BuyerType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + points;
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
		Buyer other = (Buyer) obj;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (points != other.points)
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
		return "Buyer [orders=" + orders + ", points=" + points + ", type=" + type + "]";
	}
	
	public Buyer(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted, int points, BuyerType type) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted);
		this.points = points;
		this.type = type;
	}

	public Buyer(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted, ArrayList<Order> orders, Basket basket, int points, BuyerType type) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted);
		this.orders = orders;
		this.points = points;
		this.type = type;
	}
}
