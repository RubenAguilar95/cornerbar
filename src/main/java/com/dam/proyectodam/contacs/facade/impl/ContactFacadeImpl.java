package com.dam.proyectodam.contacs.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dam.proyectodam.contacs.dao.ContactDAO;
import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.contacs.entity.ContactEntity;
import com.dam.proyectodam.contacs.facade.ContactFacade;
import com.dam.proyectodam.contacs.mapper.ContactEntityToDTOMapper;
import com.dam.proyectodam.user.entity.UserEntity;

@Service("contactFacade")
public class ContactFacadeImpl implements ContactFacade{

	@Autowired
	@Qualifier("contactDAO")
	private ContactDAO contactDAO;
	
	
	@Override
	public List<ContactDTO> getContactsFromUser(UserEntity user) {
		ArrayList<ContactDTO> dtos = new ArrayList<ContactDTO>();
		List<ContactEntity> contacts = contactDAO.getContacsFromUser(user);
		
		if(contacts != null && !contacts.isEmpty()){
			for(ContactEntity entity : contacts){
				dtos.add(ContactEntityToDTOMapper.apply(user, entity));
			}
		}
		return dtos;
	}

	@Override
	public List<ContactDTO> contact(UserEntity user1, UserEntity user2) {
		ArrayList<ContactDTO> dtos = new ArrayList<ContactDTO>();
		List<ContactEntity> contacts = contactDAO.contact(user1, user2);
		
		if(contacts != null && !contacts.isEmpty()){
			for(ContactEntity entity : contacts){
				dtos.add(ContactEntityToDTOMapper.apply(user1, entity));
			}
		}
		return dtos;
	}

}
