package com.java.dictionary;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketSend <T> implements Runnable {
	T t;
	static int cnt = 0;
	Socket server;
	public ClientSocketSend(T t, Socket server) {
		this.t = t;
		this.server = server;
	}
	
	public void run() {    
		try {
			ObjectOutputStream os = null;		
			if(cnt == 0){
				os = new ObjectOutputStream(server.getOutputStream());
			}
			else
				os = new MyObjectOutputStream(server.getOutputStream());
//			os = new ObjectOutputStream(server.getOutputStream());
			os.writeObject(t);
			os.flush();
			cnt++;
//			os.close();
//			server.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class MyObjectOutputStream extends ObjectOutputStream { 
	 public MyObjectOutputStream() throws IOException {  
	        super(); 
	 }
	  public MyObjectOutputStream(OutputStream out) throws IOException {
	   super(out);
	  } 
	 @Override 

	     protected void writeStreamHeader() throws IOException { 
	    return;
	  }
	 } 
