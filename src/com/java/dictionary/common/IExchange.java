package com.java.dictionary.common;

public interface IExchange<T extends IMessage> {
	public void send(T t);
	
	public void receive();
	
}
