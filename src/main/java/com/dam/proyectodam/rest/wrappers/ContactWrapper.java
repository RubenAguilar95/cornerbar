package com.dam.proyectodam.rest.wrappers;

import com.dam.proyectodam.user.entity.UserEntity;

public class ContactWrapper {
	
	private UserEntity user1;
	private UserEntity user2;
	
	public UserEntity getUser1() {
		return user1;
	}
	
	public void setUser1(UserEntity user1) {
		this.user1 = user1;
	}
	
//	public String getToFollow() {
//		return toFollow;
//	}
//	
//	public void setToFollow(String toFollow) {
//		this.toFollow = toFollow;
//	}
	
	public UserEntity getUser2() {
		return user2;
	}
	
	public void setUser2(UserEntity user2) {
		this.user2 = user2;
	}

}
