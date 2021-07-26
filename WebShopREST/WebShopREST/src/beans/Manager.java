package beans;

public class Manager extends User{

	private Restaurant restaurant;
	
	public Manager() {
		super();
		this.restaurant = new Restaurant();
	}
	
}
