package beans;

public class Product {
	
	private String id;
	private String name;
	private double price;
	
	public Product() {
		
	}

	public Product(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public void setId(String i) {
		id = i;
	}

	public String getId() {
		return id;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public void setPrice(double p) {
		price = p;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
	}


}
