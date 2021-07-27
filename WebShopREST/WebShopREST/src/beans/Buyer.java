package beans;

import java.util.ArrayList;

public class Buyer extends User {

	private static final long serialVersionUID = 1L;
			
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

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
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
		result = prime * result + ((basket == null) ? 0 : basket.hashCode());
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
		if (basket == null) {
			if (other.basket != null)
				return false;
		} else if (!basket.equals(other.basket))
			return false;
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
		return "Buyer [orders=" + orders + ", basket=" + basket + ", points=" + points + ", type=" + type + "]";
	}

	public Buyer(ArrayList<Order> orders, Basket basket, int points, BuyerType type) {
		super();
		this.orders = orders;
		this.basket = basket;
		this.points = points;
		this.type = type;
	}
	
	
}
