package com.java.dictionary.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.java.dictionary.common.IExchange;
import com.java.dictionary.common.IMessage;
import com.java.dictionary.common.ResultHook;

public class SocketHelper implements IExchange<IMessage> {
	private static final String IP = "172.28.173.38";
	private static final int PORT = 12345;
	public static SocketHelper INSTANCE;
	ResultHook<?> callback;
	private Socket server;
	
	public static SocketHelper getInstance(){
		if(INSTANCE == null)
			INSTANCE = new SocketHelper();
		return INSTANCE;
	}
	
	public void setCallback(ResultHook<?> callback) {
		this.callback = callback;
	}

	
	private SocketHelper() {
		try {
			server = new Socket(IP, PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send(IMessage t) {
		
		
	}

	@Override
	public void receive() {
		// TODO Auto-generated method stub
		
	}

}
