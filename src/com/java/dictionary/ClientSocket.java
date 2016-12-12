package com.java.dictionary;

import java.net.*;
import java.io.*;

public class ClientSocket {
	static Socket server;

	public ClientSocket(String host) throws UnknownHostException, IOException {
		server = new Socket(InetAddress.getLocalHost(), 5678);
	}

	@SuppressWarnings("unchecked")
	public <T> T receive() throws Exception {
		T t = null;
        ObjectInputStream is = null;  
        is = new ObjectInputStream(server.getInputStream());  
        Object obj = is.readObject();  
        if (obj != null) {  
            t = (T)obj;  
        }  
        return t;
	}
	
	public <T> void send(T t) throws Exception{
		  ObjectOutputStream os = null;  
		  os = new ObjectOutputStream(server.getOutputStream());    
          os.writeObject(t);  
          os.flush();  
	}
}
