package com.dam.proyectodam.publication.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.dao.PublicationDAO;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.publication.facade.PublicationFacade;
import com.dam.proyectodam.user.dao.UserDAO;
import com.dam.proyectodam.user.entity.UserEntity;

@Service("publicationFacade")
public class PublicationFacadeImpl implements PublicationFacade {

	@Autowired
	@Qualifier("publicationDAO")
	private PublicationDAO publicationDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Override
	public Boolean addPublication(PublicationEntity entity) {
		return publicationDAO.addPublication(entity);
	}

	@Override
	public List<PublicationEntity> getPublicationsFromUser(UserEntity user) {
		return user == null ? null : publicationDAO.getPublicationsFromUser(user);
	}
	
	@Override
	public List<PublicationEntity> getAllPublications() {
		return publicationDAO.getAllPublications();
	}

	@Override
	public List<Comment> comment(String idPub, Comment comment) {
		return publicationDAO.comment(idPub, comment);
	}

	@Override
	public List<Comment> deleteComment(String idPub, Comment comment) {
		return publicationDAO.deleteComment(idPub, comment);
	}

	@Override
	public List<UserEntity> like(String idPub, String username) {
		UserEntity user = userDAO.findByUsername(username);
		if(user != null) {
			return publicationDAO.like(idPub, user);
		} else {
			return null;
		}
	}

	@Override
	public List<PublicationEntity> getHomePublications(UserEntity user) {
		List<PublicationEntity> pubEntity = new ArrayList<PublicationEntity>();
		
		pubEntity.addAll(getPublicationsFromUser(user));
		if(user.getContacts() != null && !user.getContacts().isEmpty()) {
			for(ContactDTO contact : user.getContacts()) {
				pubEntity.addAll(getPublicationsFromUser(contact.getContact()));
			}
		}
		
		return pubEntity;
	}

}
