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
}
