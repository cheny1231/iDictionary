package com.java.dictionary;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static User INSTANCE = null;
	

	private String username;
	private String password;
	private String type;
	private int BD;
	private int YD;
	private int BY;

	public int getBD() {
		return BD;
	}

	public void setBD(int bD) {
		BD = bD;
	}

	public int getYD() {
		return YD;
	}

	public void setYD(int yD) {
		YD = yD;
	}

	public int getBY() {
		return BY;
	}

	public void setBY(int bY) {
		BY = bY;
	}

	private User() {
		BD = 0;
		YD = 0; 
		BY = 0;
	}

	public static User getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new User();
		}
		return INSTANCE;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void addFavors(boolean flag, String type) {
//		int temp = Integer.valueOf(String.valueOf(this.favors.get(type)));
		switch(type){
		case "BD": if(flag) BD++;
					else BD--;
						break;
		case "YD": if(flag) YD++;
					else YD--;
					break;
		case "BY": if(flag) BY++;
					else BY--;
					break;
		}
//		this.favors.replace(type, temp);
		
	}

//	public void setFavors(HashMap<String, Integer> favors) {
//		this.favors = favors;
//	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] sort() {
		String[] sortResult = { "BD", "BY", "YD" };
		String temp;
		HashMap<String,Integer> favors = new HashMap<String, Integer>();
		favors.put("BD", BD);
		favors.put("BY", BY);
		favors.put("YD", YD);
		for (int i = 0; i < 2; i++) {
			for (int j = i + 1; j < 3; j++) {
				if (favors.get(sortResult[i]) < favors.get(sortResult[j])) {
					temp = sortResult[i];
					sortResult[i] = sortResult[j];
					sortResult[j] = temp;
				}
			}
		}
		return sortResult;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
