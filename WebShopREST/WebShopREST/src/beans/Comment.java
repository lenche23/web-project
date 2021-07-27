package beans;

public class Comment {

	private Buyer buyer;
	private Restaurant restaurant;
	private String content;
	private Grade grade;
	
	public Comment() {
		this.buyer = new Buyer();
		this.restaurant = new Restaurant();
		this.content = "";
		this.grade = Grade.ONE;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
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
		Comment other = (Comment) obj;
		if (buyer == null) {
			if (other.buyer != null)
				return false;
		} else if (!buyer.equals(other.buyer))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (grade != other.grade)
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [buyer=" + buyer + ", restaurant=" + restaurant + ", content=" + content + ", grade=" + grade
				+ "]";
	}

	public Comment(Buyer buyer, Restaurant restaurant, String content, Grade grade) {
		super();
		this.buyer = buyer;
		this.restaurant = restaurant;
		this.content = content;
		this.grade = grade;
	}
	
	
}
