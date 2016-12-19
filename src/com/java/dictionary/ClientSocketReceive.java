package com.java.dictionary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import javafx.application.*;

public class ClientSocketReceive implements Runnable {
	Socket server;
//	ObjectOutputStream os;
	static String message;
	static Object object;

	public ClientSocketReceive(Socket server) {
		this.server = server;
//		this.os = os;
		message = "";
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			ObjectInputStream is = null;
			is = new ObjectInputStream(server.getInputStream());
			while (true) {
				if (message.equals("")) {
					object = is.readObject();
					if (object instanceof User) {
						User.getInstance().setUsername(((User) object).getUsername());
						User.getInstance().setPassword(((User) object).getPassword());
						User.getInstance().setBD(((User) object).getBD());
						User.getInstance().setBY(((User) object).getBY());
						User.getInstance().setYD(((User) object).getYD());
						setMessage("User");
						System.out.println(message);
//						object = null;
					}

					if (object instanceof Vector<?>) {
						if(((Vector<String>)object).get(0).equals("onlineUsers")){
							setMessage("online user");
							System.out.println(message);
						}
						else{
							setMessage("WordCard");
							Platform.runLater(new Runnable() {
							    @Override
							    public void run() {
							    	try {
										ShareWordCard.alphaWords2Image(((Vector<String>)object).get(2), ((Vector<String>)object).get(3), ((Vector<String>)object).get(4));
										ShareWordCard.showImageCard(((Vector<String>)object).get(0));
									} catch (IOException e) {
										// TODO Auto-generated catch block
//										e.printStackTrace();
									}
									
							    }
							});
							
							setMessage("");
//							setObject(null);
							DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
							System.out.println(message);
						}
					}

//					if (object instanceof Vector<?>) {
//						setMessage("online user");
//						System.out.println(message);
//					}

					if (object instanceof String) {
						setMessage((String) object);
//						object = null;
						System.out.println(message);
					}
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
