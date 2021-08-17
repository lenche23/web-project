package beans;

import java.util.ArrayList;

public class Basket {
	
	private ArrayList<ArticleWithQuantity> articlesWithQuantity;
	private Buyer buyer;
	private double price;
	
	public Basket() {
		this.articlesWithQuantity = new ArrayList<ArticleWithQuantity>();
		this.buyer = new Buyer();
		this.price = 0.0;
	}

	public ArrayList<ArticleWithQuantity> getArticlesWithQuantity() {
		return articlesWithQuantity;
	}

	public void setArticlesWithQuantity(ArrayList<ArticleWithQuantity> articlesWithQuantity) {
		this.articlesWithQuantity = articlesWithQuantity;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Basket other = (Basket) obj;
		if (buyer == null) {
			if (other.buyer != null)
				return false;
		} else if (!buyer.equals(other.buyer))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Basket [buyer=" + buyer + ", price=" + price + "]";
	}

	public Basket(ArrayList<ArticleWithQuantity> articlesWithQuantity, Buyer buyer, double price) {
		super();
		this.articlesWithQuantity = articlesWithQuantity;
		this.buyer = buyer;
		this.price = price;
	}	
}
