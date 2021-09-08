package beans;

import java.util.ArrayList;

public class Deliverer extends User{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Order> ordersToDeliver;
	
	public Deliverer() {
		super();
		this.ordersToDeliver = new ArrayList<Order>();
	}

	public ArrayList<Order> getOrdersToDeliver() {
		return ordersToDeliver;
	}

	public void setOrdersToDeliver(ArrayList<Order> ordersToDeliver) {
		this.ordersToDeliver = ordersToDeliver;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ordersToDeliver == null) ? 0 : ordersToDeliver.hashCode());
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
		Deliverer other = (Deliverer) obj;
		if (ordersToDeliver == null) {
			if (other.ordersToDeliver != null)
				return false;
		} else if (!ordersToDeliver.equals(other.ordersToDeliver))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Deliverer [ordersToDeliver=" + ordersToDeliver + "]";
	}

	public Deliverer(ArrayList<Order> ordersToDeliver) {
		super();
		this.ordersToDeliver = ordersToDeliver;
	}

	public Deliverer(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted,boolean blocked,boolean sus) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted,blocked,sus);
	}
}
