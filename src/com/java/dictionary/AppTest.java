package com.java.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ”√”⁄≤‚ ‘
 * @author rhg
 *
 */
public class AppTest {

	/*public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("User.out");
		UserDB user = UserDB.getInstance();
		user.setUsername("rhg");
		user.setPassword("123");
		user.setType("bd");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(user);
			oos.close();
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object obj = ois.readObject();
			ois.close();
			System.out.println(((UserDB)obj).getUsername());
		
	}*/
}
