package beans;

public class ArticleWithQuantity {
	private Article article;
	private int quantity;
	
	public ArticleWithQuantity() {
		this.article = new Article();
		this.quantity = 0;
	}
	
	public ArticleWithQuantity(Article article, int quantity) {
		super();
		this.article = article;
		this.quantity = quantity;
	}
	
	public Article getArticle() {
		return article;
	}
	
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
