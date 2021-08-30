package dao;

import java.util.ArrayList;

import beans.ArticleWithQuantity;
import beans.Basket;
import beans.Deliverer;

public class BasketDAO {
	private Basket basket;
	
	public BasketDAO() {
		basket = new Basket();
	}

	public Basket getBasket() {
		return basket;
	}
	
	public void addArticleWithQuantityToBasket(ArticleWithQuantity articleWithQuantity) {
		boolean articleExists = false;
		for(int i = 0; i < basket.getArticlesWithQuantity().size(); i++)
			if(articleWithQuantity.getArticle().getName().equals(basket.getArticlesWithQuantity().get(i).getArticle().getName())) {
				int quantityBefore = basket.getArticlesWithQuantity().get(i).getQuantity();
				basket.getArticlesWithQuantity().remove(i);
				basket.setPrice(basket.getPrice() + articleWithQuantity.getArticle().getPrice() * articleWithQuantity.getQuantity());
				articleWithQuantity.setQuantity(quantityBefore + articleWithQuantity.getQuantity());
				basket.getArticlesWithQuantity().add(articleWithQuantity);
				articleExists = true;
				break;
			}
		
		if(!articleExists) {
			basket.getArticlesWithQuantity().add(articleWithQuantity);
			basket.setPrice(basket.getPrice() + articleWithQuantity.getArticle().getPrice() * articleWithQuantity.getQuantity());
		}
	}
	
	public void removeArticleWithQuantityFromBasket(ArticleWithQuantity articleWithQuantity) {
		for(int i = 0; i < basket.getArticlesWithQuantity().size(); i++)
			if(articleWithQuantity.getArticle().getName().equals(basket.getArticlesWithQuantity().get(i).getArticle().getName())) {
				basket.getArticlesWithQuantity().remove(i);
				break;
			}
		basket.setPrice(basket.getPrice() - articleWithQuantity.getArticle().getPrice() * articleWithQuantity.getQuantity());
	}
	
	public void changeQuantity(String article, ArticleWithQuantity articleWithQuantity) {
		for(int i = 0; i < basket.getArticlesWithQuantity().size(); i++)
			if(basket.getArticlesWithQuantity().get(i).getArticle().getName().equals(article)) {
				if(articleWithQuantity.getQuantity() > basket.getArticlesWithQuantity().get(i).getQuantity())
					basket.setPrice(basket.getPrice() + basket.getArticlesWithQuantity().get(i).getArticle().getPrice() * (articleWithQuantity.getQuantity() - basket.getArticlesWithQuantity().get(i).getQuantity()));
				else if(articleWithQuantity.getQuantity() < basket.getArticlesWithQuantity().get(i).getQuantity())
					basket.setPrice(basket.getPrice() - basket.getArticlesWithQuantity().get(i).getArticle().getPrice() * (basket.getArticlesWithQuantity().get(i).getQuantity() - articleWithQuantity.getQuantity()));
					
				basket.getArticlesWithQuantity().get(i).setQuantity(articleWithQuantity.getQuantity());
				break;
			}
	}
}
