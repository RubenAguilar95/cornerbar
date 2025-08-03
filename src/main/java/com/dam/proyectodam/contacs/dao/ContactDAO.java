package com.dam.proyectodam.contacs.dao;

import java.util.List;

import com.dam.proyectodam.contacs.entity.ContactEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public interface ContactDAO {
	
	public List<ContactEntity> getContacsFromUser(UserEntity user);
	
	public List<ContactEntity> contact(UserEntity user1, UserEntity user2);
}
