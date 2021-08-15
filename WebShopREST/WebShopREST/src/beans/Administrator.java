package beans;

public class Administrator extends User{

	private static final long serialVersionUID = 1L;

	public Administrator() {
		super();
	}

	public Administrator(String firstName, String lastName, String email, String username, String password, Sex gender,
			String birth) {
		super.setFirstName(firstName);
		super.setLastName(lastName);
		super.setEmail(email);
		super.setUsername(username);
		super.setPassword(password);
		super.setGender(gender);
		super.setDateOfBirth(birth);
	}
}
