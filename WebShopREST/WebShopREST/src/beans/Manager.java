package beans;

public class Manager extends User{

	private static final long serialVersionUID = 1L;
	
	private Restaurant restaurant;
	
	public Manager() {
		super();
		this.restaurant = new Restaurant();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Manager other = (Manager) obj;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Manager [restaurant=" + restaurant + "]";
	}

	public Manager(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}

	public Manager(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted);
	}
}
