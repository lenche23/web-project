package beans;

import java.io.Serializable;

public class User implements Serializable {
	
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private Sex gender;
	private String dateOfBirth;
	
	public User() {
	}

	private static final long serialVersionUID = 6640936480584723344L;

}
