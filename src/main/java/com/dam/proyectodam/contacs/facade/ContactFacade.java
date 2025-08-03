package com.dam.proyectodam.contacs.facade;

import java.util.List;

import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.user.entity.UserEntity;

public interface ContactFacade {
	
	public List<ContactDTO> getContactsFromUser(UserEntity user);
	
	public List<ContactDTO> contact(UserEntity user1, UserEntity user2);
	
}
