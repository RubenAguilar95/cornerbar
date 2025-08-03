package com.dam.proyectodam.contacs.mapper;

import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.contacs.entity.ContactEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public class ContactEntityToDTOMapper {

	public static ContactDTO apply(UserEntity user, ContactEntity entity) {
		ContactDTO dto = new ContactDTO();
		
//		if(entity.getUser1().equals(entity)) {
//			dto.setContact(entity.getUser2());
//			if(entity.getRelation().equals(RelationType.MUTUO) 
//					|| entity.getRelation().equals(RelationType.U1_U2)){
//				dto.setFollowType(FollowType.FOLLOW);
//			} else {
//				dto.setFollowType(FollowType.NOTFOLLOW);
//			}
//		}
		
		if(entity.getUser1().equals(user)) {
			dto.setContact(entity.getUser2());
		} else {
			dto.setContact(entity.getUser1());
		}
		
		return dto;
	}
	
}
