package beans;

import java.util.HashMap;

public class Basket {
	
	private HashMap<Article, Integer> articles;
	private Buyer buyer;
	private double price;
	
	public Basket() {
		this.articles = new HashMap<Article, Integer>();
		this.buyer = new Buyer();
		this.price = 0.0;
	}

	public HashMap<Article, Integer> getArticles() {
		return articles;
	}

	public void setArticles(HashMap<Article, Integer> articles) {
		this.articles = articles;
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
		result = prime * result + ((articles == null) ? 0 : articles.hashCode());
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
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Basket [articles=" + articles + ", buyer=" + buyer + ", price=" + price + "]";
	}

	public Basket(HashMap<Article, Integer> articles, Buyer buyer, double price) {
		super();
		this.articles = articles;
		this.buyer = buyer;
		this.price = price;
	}
	
	
}
