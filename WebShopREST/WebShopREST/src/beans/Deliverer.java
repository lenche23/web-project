package beans;

import java.util.ArrayList;

public class Deliverer extends User{

	private ArrayList<Order> ordersToDeliver;
	
	public Deliverer() {
		super();
		this.ordersToDeliver = new ArrayList<Order>();
	}
}
