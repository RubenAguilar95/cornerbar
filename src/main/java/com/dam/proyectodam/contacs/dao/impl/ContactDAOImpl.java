package com.dam.proyectodam.contacs.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dam.proyectodam.contacs.dao.ContactDAO;
import com.dam.proyectodam.contacs.entity.ContactEntity;
import com.dam.proyectodam.contacs.relation.RelationType;
import com.dam.proyectodam.contacs.repository.ContactRepository;
import com.dam.proyectodam.user.entity.UserEntity;

@Service("contactDAO")
public class ContactDAOImpl implements ContactDAO {
	
	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;

	@Override
	public List<ContactEntity> getContacsFromUser(UserEntity user) {
		return contactRepository.getContactsFromUser(user);
	}

	@Override
	public List<ContactEntity> contact(UserEntity user1, UserEntity user2) {
		ContactEntity contact = contactRepository.getContact(user1, user2);
		
		if(null == contact) {
			ContactEntity newContact = new ContactEntity();
			newContact.setUser1(user1);
			newContact.setUser2(user2);
			newContact.setRelation(RelationType.U1_U2);
			contactRepository.saveContact(newContact);
		} else {
			if(contact.getUser1().equals(user1)) {
				switch(contact.getRelation()) {
				case U1_U2:
					contactRepository.deleteContact(contact);
					break;
				case U2_U1:
					contact.setRelation(RelationType.MUTUO);
					contactRepository.saveContact(contact);
					break;
				case MUTUO:
					contact.setRelation(RelationType.U2_U1);
					contactRepository.saveContact(contact);
					break;
				}
			} else if(contact.getUser1().equals(user2)) {
				switch(contact.getRelation()) {
				case U1_U2:
					contact.setRelation(RelationType.MUTUO);
					contactRepository.saveContact(contact);
					break;
				case U2_U1:
					contactRepository.deleteContact(contact);
					break;
				case MUTUO:
					contact.setRelation(RelationType.U1_U2);
					contactRepository.saveContact(contact);
					break;
				}
			}
		}
		
		return getContacsFromUser(user1);
	}

}
