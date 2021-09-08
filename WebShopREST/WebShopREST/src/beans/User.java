package beans;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String username;
	protected String password;
	protected Sex gender;
	protected String dateOfBirth;
	protected boolean deleted;
	protected boolean blocked;
	
	public User() {
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.username = "";
		this.password = "";
		this.gender = Sex.FEMALE;
		this.dateOfBirth = "";
		this.deleted = false;
		this.blocked = false;
	}

	private static final long serialVersionUID = 6640936480584723344L;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", deleted="
				+ deleted + ", blocked=" + blocked + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(blocked, dateOfBirth, deleted, email, firstName, gender, lastName, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return blocked == other.blocked && Objects.equals(dateOfBirth, other.dateOfBirth) && deleted == other.deleted
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& gender == other.gender && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	public User(String firstName, String lastName, String email, String username, String password, Sex gender,
			String dateOfBirth, boolean deleted,boolean blocked) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.deleted = deleted;
		this.blocked = blocked;
	}
}
