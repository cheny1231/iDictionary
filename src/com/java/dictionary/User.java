package com.java.dictionary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static User INSTANCE = null;
	private String username;
	private String password;
	Map<String, Integer> favors;

	private User() {
		favors = new HashMap<>();
		favors.put("BD", 0);
		favors.put("YD", 0);
		favors.put("BY", 0);
	}

	public static User getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new User();
		}
		return INSTANCE;
	}

	public Map<String, Integer> getFavors() {
		return favors;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void addFavors(boolean flag, String type) {
		int temp = this.favors.get(type);
		if (flag)
			temp++;
		else {
			if (temp > 0)
				temp--;
		}
		this.favors.replace(type, temp);
	}

	public void setFavors(Map<String, Integer> favors) {
		this.favors = favors;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] sort() {
		String[] sortResult = { "BD", "BY", "YD" };
		String temp;
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

}
