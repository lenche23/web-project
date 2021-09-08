package beans;

public class Administrator extends User{

	private static final long serialVersionUID = 1L;

	public Administrator() {
		super();
	}
	
	public Administrator(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted, boolean blocked) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, deleted,blocked);
	}

	
}
