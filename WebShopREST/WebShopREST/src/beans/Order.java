package beans;

import java.util.ArrayList;

public class Order {

	private String id;
	private ArrayList<Article> articles;
	private Restaurant restaurant;
	private String dateAndTime;
	private double price;
	private Buyer buyer;
	private Deliverer deliverer;
	private OrderStatus status;
	
	public Order() {
		this.id = "";
		this.articles = new ArrayList<Article>();
		this.restaurant = new Restaurant();
		this.dateAndTime = "";
		this.price = 0.0;
		this.buyer = new Buyer();
		this.deliverer = new Deliverer();
		this.status = OrderStatus.PROCESSING;
	}

	public Deliverer getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(Deliverer deliverer) {
		this.deliverer = deliverer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", articles=" + articles + ", restaurant=" + restaurant + ", dateAndTime="
				+ dateAndTime + ", price=" + price + ", buyer=" + buyer + ", deliverer=" + deliverer + ", status="
				+ status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articles == null) ? 0 : articles.hashCode());
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		result = prime * result + ((dateAndTime == null) ? 0 : dateAndTime.hashCode());
		result = prime * result + ((deliverer == null) ? 0 : deliverer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Order other = (Order) obj;
		if (articles == null) {
			if (other.articles != null)
				return false;
		} else if (!articles.equals(other.articles))
			return false;
		if (buyer == null) {
			if (other.buyer != null)
				return false;
		} else if (!buyer.equals(other.buyer))
			return false;
		if (dateAndTime == null) {
			if (other.dateAndTime != null)
				return false;
		} else if (!dateAndTime.equals(other.dateAndTime))
			return false;
		if (deliverer == null) {
			if (other.deliverer != null)
				return false;
		} else if (!deliverer.equals(other.deliverer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public Order(String id, ArrayList<Article> articles, Restaurant restaurant, String dateAndTime, double price,
			Buyer buyer, OrderStatus status, Deliverer deliverer) {
		super();
		this.id = id;
		this.articles = articles;
		this.restaurant = restaurant;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.buyer = buyer;
		this.status = status;
		this.deliverer = deliverer;
	}
	
	public Order(String id, String dateAndTime, double price, OrderStatus status) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.status = status;
		this.deliverer = new Deliverer();
	}
}
