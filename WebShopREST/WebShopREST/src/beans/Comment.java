package beans;

import java.util.Objects;

public class Comment {

	private int id;
	private String buyerUsername;
	private String restaurantName;
	private String content;
	private Grade grade;
	private Boolean deleted;
	
	public Comment() {
		this.id = 0;
		this.buyerUsername = "";
		this.restaurantName = "";
		this.content = "";
		this.grade = Grade.ONE;
		this.deleted = false;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getBuyerUsername() {
		return buyerUsername;
	}

	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
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

	@Override
	public int hashCode() {
		return Objects.hash(buyerUsername, content, deleted, grade, id, restaurantName);
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
		return Objects.equals(buyerUsername, other.buyerUsername) && Objects.equals(content, other.content)
				&& Objects.equals(deleted, other.deleted) && grade == other.grade && id == other.id
				&& Objects.equals(restaurantName, other.restaurantName);
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", buyerUsername=" + buyerUsername + ", restaurantName=" + restaurantName
				+ ", content=" + content + ", grade=" + grade + ", deleted=" + deleted + "]";
	}

	public Comment(int id, String buyerUsername, String restaurantName, String content, Grade grade, Boolean deleted) {
		super();
		this.id = id;
		this.buyerUsername = buyerUsername;
		this.restaurantName = restaurantName;
		this.content = content;
		this.grade = grade;
		this.deleted = deleted;
	}

	public Comment(int id, String buyerUsername, String content, Grade grade, Boolean deleted) {
		super();
		this.id = id;
		this.buyerUsername = buyerUsername;
		this.content = content;
		this.grade = grade;
		this.deleted = deleted;
	}
}
