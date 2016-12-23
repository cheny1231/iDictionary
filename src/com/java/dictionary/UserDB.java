package com.java.dictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.java.dictionary.bean.FavorBean;

/**
 * User class to save the information of current user
 * 
 * @author: cheny1231
 *
 */

public class UserDB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserDB INSTANCE = null;
	

	private String username;
	private String password;
	private String type;
	
	List<FavorBean> likeList = new LinkedList<>();
	/*private int BD;
	private int YD;
	private int BY;*/

	/*public int getBD() {
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
	}*/

	private UserDB() {
		FavorBean BDType = new FavorBean(ApplicationUI.ID_BD,0);
		FavorBean BYType = new FavorBean(ApplicationUI.ID_BY,0);
		FavorBean YDType = new FavorBean(ApplicationUI.ID_YD,0);
		likeList.add(BDType);
		likeList.add(BYType);
		likeList.add(YDType);
	}

	public static UserDB getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserDB();
		}
		return INSTANCE;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/*public void addFavors(boolean flag, String type) {
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
		}*/
//		this.favors.replace(type, temp);
		
//	}

//	public void setFavors(HashMap<String, Integer> favors) {
//		this.favors = favors;
//	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public String[] sort() {
		String[] sortResult = { "BD", "BY", "YD" };
		String temp;
//		HashMap<String,Integer> favors = new HashMap<String, Integer>();
//		favors.put("BD", BD);
//		favors.put("BY", BY);
//		favors.put("YD", YD);
		Collections.sort(likeList, new Comparator<TypeBean>() {

			@Override
			public int compare(TypeBean o1, TypeBean o2) {
				if(o1.getNumber()<o2.getNumber())
					return 1;
				else if(o1.getNumber()>o2.getNumber())
					return -1;
				return 0;
			}
		});
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
	}*/
	
	public List<FavorBean> getLikeList() {
		return likeList;
	}
	


	public void setType(String type) {
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	private Object readResolve(){
		return INSTANCE;
	}

	@Override
	public String toString() {
		return "UserDB [username=" + username + ", password=" + password + ", type=" + type + ", likeList=" + likeList
				+ "]";
	}
	
	
}
