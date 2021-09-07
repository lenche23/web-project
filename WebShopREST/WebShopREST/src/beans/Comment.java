package beans;

import java.util.Objects;

public class Comment {

	private int id;
	private Buyer buyer;
	private Restaurant restaurant;
	private String content;
	private Grade grade;
	private Boolean deleted;
	
	public Comment() {
		this.id = 0;
		this.buyer = new Buyer();
		this.restaurant = new Restaurant();
		this.content = "";
		this.grade = Grade.ONE;
		this.deleted = false;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", buyer=" + buyer + ", restaurant=" + restaurant + ", content=" + content
				+ ", grade=" + grade + ", deleted=" + deleted + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(buyer, content, deleted, grade, id, restaurant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(buyer, other.buyer) && Objects.equals(content, other.content)
				&& Objects.equals(deleted, other.deleted) && grade == other.grade && id == other.id
				&& Objects.equals(restaurant, other.restaurant);
	}

	public Comment(int id, Buyer buyer, Restaurant restaurant, String content, Grade grade, Boolean deleted) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.restaurant = restaurant;
		this.content = content;
		this.grade = grade;
		this.deleted = deleted;
	}

	public Comment(int id, Buyer buyer, String content, Grade grade, Boolean deleted) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.content = content;
		this.grade = grade;
		this.deleted = deleted;
	}
}
