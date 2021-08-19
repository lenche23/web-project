package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Administrator;
import beans.Manager;
import beans.Sex;

public class AdministratorDAO { 
	
	//private ArrayList<Administrator> allAdministrators;
	private HashMap<String, Administrator> administrators;

	public AdministratorDAO() {
		administrators = new HashMap<>();
		loadAdministrators(); 
	}

//	  Vra�a administratora za prosle�eno korisni�ko ime i �ifru. Vra�a null ako administratora
//	  ne postoji
//	  
//	  @param username
//	  @param password
//	  @return
	 

	  public Administrator find(String username, String password) { 
		  if (!administrators.containsKey(username)) { 
			  return null; 
		  } 
		  Administrator administrator = administrators.get(username); 
		  if (!administrator.getPassword().equals(password)) { 
			  return null;
		  } 
		  return administrator; 
	  }
	  
	  public Collection<Administrator> findAll() { 
		  return administrators.values(); 
	  }
  
 
//	  U�itava korisnike iz WebContent/adminstrators.txt fajla i dodaje ih u mapu
//	  {@link #administrators}. Klju� je korisni�ko ime korisnika.
//	  
//	  @param contextPath Putanja do aplikacije u Tomcatu
	 
	  private void loadAdministrators() { 
		  BufferedReader in = null; 
		  try {
			  File file = new File("WebContent/administrators.txt"); 
			  in = new BufferedReader(new FileReader(file)); 
			  String line; 
			  StringTokenizer st; 
			  while ((line = in.readLine()) != null) { 
				  line = line.trim(); 
				  if (line.equals("") || line.indexOf('#') == 0) 
					  continue; 
				  st = new StringTokenizer(line, ";"); 
				  while (st.hasMoreTokens()) { 
					  String firstName = st.nextToken().trim(); 
					  String lastName = st.nextToken().trim(); 
					  String email = st.nextToken().trim();
					  String username = st.nextToken().trim(); 
					  String password = st.nextToken().trim();
					  String genderString = st.nextToken().trim();
					  Sex gender;
					  	if (genderString.equals("M")) {
					  		gender = Sex.MALE;
					  	} else {
					  		gender = Sex.FEMALE;
					  	}
					  String birth = st.nextToken().trim();
					  	
					  administrators.put(username, new Administrator(firstName, lastName, email, username, password, gender, birth)); 
				  }
	  
			  } 
			 } catch (Exception ex) { 
				 ex.printStackTrace(); 
			 } finally { 
				 if (in != null) { 
					 try { in.close(); } catch (Exception e) { } } 
			 } 
	 }
	  
	  public ArrayList<Administrator> getSearchedAdministrators(String name, String surname, String username) { 
		  	ArrayList<Administrator> administratorsByName = new ArrayList<Administrator>();
		  	ArrayList<Administrator> administratorsByNameAndSurname = new ArrayList<Administrator>();
		  	ArrayList<Administrator> administratorsByNameSurnameAndUsername = new ArrayList<Administrator>();
			
			if(name.equals("")) {
				for(Administrator admin : administrators.values()) {
					administratorsByName.add(admin);
				}
			}
			else {
				for(Administrator admin : administrators.values()) {
					if(admin.getFirstName().toLowerCase().contains(name.toLowerCase()))
						administratorsByName.add(admin);
				}
			}
			
			if(surname.equals("")) {
				for(int i = 0; i < administratorsByName.size(); i++) {
					administratorsByNameAndSurname.add(administratorsByName.get(i));
				}
			}
			else {
				for(int i = 0; i < administratorsByName.size(); i++) {
					if(administratorsByName.get(i).getLastName().toLowerCase().contains(surname.toLowerCase()))
						administratorsByNameAndSurname.add(administratorsByName.get(i));
				}
			}
			
			if(username.equals("")) {
				for(int i = 0; i < administratorsByNameAndSurname.size(); i++) {
					administratorsByNameSurnameAndUsername.add(administratorsByNameAndSurname.get(i));
				}
			}
			else {
				for(int i = 0; i < administratorsByNameAndSurname.size(); i++) {
					if(administratorsByNameAndSurname.get(i).getUsername().toLowerCase().contains(username.toLowerCase()))
						administratorsByNameSurnameAndUsername.add(administratorsByNameAndSurname.get(i));
				}
			}
			
			return administratorsByNameSurnameAndUsername;
		}
}
		 