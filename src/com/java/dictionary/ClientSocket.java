package com.java.dictionary;

import java.net.*;
import java.io.*;

public class ClientSocket {
	static Socket server;

	public ClientSocket() {
		try {
			server = new Socket(InetAddress.getLocalHost(), 5678);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T receive() {
		T t = null;
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(server.getInputStream());
			Object obj = is.readObject();

			if (obj != null) {
				t = (T) obj;
			}
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public <T> void send(T t) {
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(server.getOutputStream());
			os.writeObject(t);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
