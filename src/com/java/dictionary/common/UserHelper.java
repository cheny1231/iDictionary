package com.java.dictionary.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.java.dictionary.UserDB;
import com.java.dictionary.bean.FavorBean;

public class UserHelper {

	UserDB user;
	
	public UserHelper(UserDB user) {
		this.user = user;
	}
	

	public void addFavor(boolean isLike,String id){
		if(user.getUsername()!=null){
			user.getLikeList().stream().filter(predicate->"LIKE_".concat(predicate.getType()).equals(id)).
			forEach(isLike?FavorBean::add:FavorBean::reduce);
		}
	}
	
	public List<FavorBean> getSortedList(){
		Collections.sort(user.getLikeList(), new Comparator<FavorBean>() {

			@Override
			public int compare(FavorBean o1, FavorBean o2) {
				if(o1.getNumber()<o2.getNumber())
					return 1;
				else if(o1.getNumber()>o2.getNumber())
					return -1;
				return 0;
			}
		});
		return user.getLikeList();
	}

	public void logOut() {
		user.setPassword(null);
		user.setUsername(null);
		user.getLikeList().clear();
		
	}
	
	public void logIn(String userName,String pwd){
		user.setUsername(userName);
		user.setPassword(pwd);
	}
	
	public boolean isLogIn(){
		return getUserName()!=null;
	}


	public String getUserName() {
		return user.getUsername();
	}
	
	public void setDataType(String type){
		user.setType(type);
	}
}
