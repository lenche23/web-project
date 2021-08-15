package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Administrator;
import beans.Sex;

public class AdministratorDAO { 
	
	//private ArrayList<Administrator> allAdministrators;
	private HashMap<String, Administrator> administrators;

	public AdministratorDAO() {
		administrators = new HashMap<>();
		loadAdministrators(); 
	}

//	  Vraæa administratora za prosleðeno korisnièko ime i šifru. Vraæa null ako administratora
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
  
 
//	  Uèitava korisnike iz WebContent/adminstrators.txt fajla i dodaje ih u mapu
//	  {@link #administrators}. Kljuè je korisnièko ime korisnika.
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
		  
}
		 