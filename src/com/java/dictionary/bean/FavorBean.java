package com.java.dictionary.bean;

import java.io.Serializable;

import com.java.dictionary.ApplicationUI;

/**
 * 用于存储用户点赞
 * @author rhg
 *
 */
public class FavorBean implements Serializable{

	/**
	 * 点赞类型 {@link ApplicationUI#ID_BD ApplicationUI#ID_BY ApplicationUI#ID_YD}
	 */
	String type;
	/**
	 * 点赞数量
	 */
	int number;
	
	public FavorBean(String type, int number) {
		this.type = type;
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void add() {
		number++;
		System.out.println(toString());
	}
	
	public void reduce(){
		number--;
		System.out.println(toString());
	}
	
	@Override
	public String toString() {
		return "TypeBean [type=" + type + ", number=" + number + "]";
	}
	
	
}
