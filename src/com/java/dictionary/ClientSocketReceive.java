package com.java.dictionary;

import java.io.ObjectInputStream;
import java.net.Socket;

import javafx.collections.ObservableList;

public class ClientSocketReceive implements Runnable {
	Socket server;
	static String message;
	static Object object;

	public ClientSocketReceive(Socket server) {
		this.server = server;
	}

	public void run() {
		try {
			ObjectInputStream is = null;
			is = new ObjectInputStream(server.getInputStream());
			while (true) {
				object = is.readObject();
				if (object instanceof User) {
//					User.getInstance().setUsername(((User) object).getUsername());
//					User.getInstance().setPassword(((User) object).getPassword());
					User.getInstance().setFavors(((User) object).getFavors());
					setMessage("User");
					System.out.println(message);
					object = null;
				}

				if (object instanceof ShareWordCard) {
					setMessage("WordCard");
					((ShareWordCard) object).write2File();
					((ShareWordCard) object).showImageCard();
					setMessage("");
					setObject(null);
					DicTest.getEs().execute(new ClientSocketSend<String>("ACK",server));
					System.out.println(message);
				}
				
				if (object instanceof ObservableList<?>) {
					setMessage("online user");
					System.out.println(message);
				}				

				if (object instanceof String) {
					setMessage((String)object);
					object = null;
					System.out.println(message);
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		ClientSocketReceive.message = message;
	}
	
	public static Object getObject() {
		return object;
	}

	public static void setObject(Object object) {
		ClientSocketReceive.object = object;
	}

}
