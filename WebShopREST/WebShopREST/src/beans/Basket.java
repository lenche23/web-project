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
}
