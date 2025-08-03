package com.dam.proyectodam.contacs.dto;

import com.dam.proyectodam.user.entity.UserEntity;

public class ContactDTO {
	
	private UserEntity contact;
//	private FollowType followType;
	
	public UserEntity getContact() {
		return contact;
	}
	
	public void setContact(UserEntity contact) {
		this.contact = contact;
	}
	
//	public FollowType getFollowType() {
//		return followType;
//	}
//	
//	public void setFollowType(FollowType followType) {
//		this.followType = followType;
//	}

}
