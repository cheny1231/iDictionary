package com.java.dictionary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import javafx.application.*;

/**
 * To Receive object from server through socket
 * 
 * @author: cheny1231
 *
 */

public class ClientSocketReceive implements Runnable {
	Socket server;
	static String message;
	static Object object;

	public ClientSocketReceive(Socket server) {
		this.server = server;
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
					}

					if (object instanceof Vector<?>) {
						if (((Vector<String>) object).get(0).equals("onlineUsers")) {
							setMessage("online user");
							System.out.println(message);
						} else {
							setMessage("WordCard");
							Vector<String> card = new Vector<String>();
							for (String i : (Vector<String>) object) {
								card.add(i);
							}
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										ShareWordCard.alphaWords2Image(card.get(2), card.get(3), card.get(4));
										ShareWordCard.showImageCard(card.get(0));
									} catch (IOException e) {
										e.printStackTrace();
									}

								}
							});

							setMessage("");
							DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
							System.out.println(message);
						}
					}

					if (object instanceof String) {
						if (!((String) object).equals("Active")) {
							setMessage((String) object);
							System.out.println(message);
						}
					}
				}
				DicTest.getEs().execute(new ClientSocketSend<String>("Active", server));
				System.out.println("Active");
			}
		} catch (Exception e) {
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
