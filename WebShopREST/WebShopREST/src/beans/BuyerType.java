package beans;

public class BuyerType {

	private TypeName name;
	private int discount;
	private int pointsNeeded;
	
	public BuyerType() {
		this.name = TypeName.BRONZE;
		this.discount = 0;
		this.pointsNeeded = 0;
	}

	public TypeName getName() {
		return name;
	}

	public void setName(TypeName name) {
		this.name = name;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPointsNeeded() {
		return pointsNeeded;
	}

	public void setPointsNeeded(int pointsNeeded) {
		this.pointsNeeded = pointsNeeded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + discount;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pointsNeeded;
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
		BuyerType other = (BuyerType) obj;
		if (discount != other.discount)
			return false;
		if (name != other.name)
			return false;
		if (pointsNeeded != other.pointsNeeded)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BuyerType [name=" + name + ", discount=" + discount + ", pointsNeeded=" + pointsNeeded + "]";
	}

	public BuyerType(TypeName name, int discount, int pointsNeeded) {
		super();
		this.name = name;
		this.discount = discount;
		this.pointsNeeded = pointsNeeded;
	}
	
	
}
