package com.java.dictionary;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * ”√”⁄≤‚ ‘
 * 
 * @author rhg
 *
 */
public class AppTest {
	
	public static void main(String[] args) {
		URI uri;
		try {
			uri = new URI("http://java.sun.com/");
			  URL url = uri.toURL();
			  System.out.println(uri.toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) throws FileNotFoundException,
	 * IOException, ClassNotFoundException { File file = new File("User.out");
	 * UserDB user = UserDB.getInstance(); user.setUsername("rhg");
	 * user.setPassword("123"); user.setType("bd"); ObjectOutputStream oos = new
	 * ObjectOutputStream(new FileOutputStream(file)); oos.writeObject(user);
	 * oos.close();
	 * 
	 * ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	 * Object obj = ois.readObject(); ois.close();
	 * System.out.println(((UserDB)obj).getUsername());
	 * 
	 * }
	 */
}
