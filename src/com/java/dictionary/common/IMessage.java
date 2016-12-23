package com.java.dictionary.common;

public abstract class IMessage {
	protected String msg;
	
	public IMessage(String msg){
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
}
