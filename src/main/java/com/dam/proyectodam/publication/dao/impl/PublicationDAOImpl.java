package com.dam.proyectodam.publication.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.dao.PublicationDAO;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.publication.repository.PublicationRepository;
import com.dam.proyectodam.user.dao.UserDAO;
import com.dam.proyectodam.user.entity.UserEntity;

@Repository("publicationDAO")
public class PublicationDAOImpl implements PublicationDAO{
	
	@Autowired
	@Qualifier("publicationRepository")
	private PublicationRepository publicationRepository;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Override
	public Boolean addPublication(PublicationEntity entity) {
		return publicationRepository.save(entity);
	}

	@Override
	public List<PublicationEntity> getPublicationsFromUser(UserEntity user) {
		return publicationRepository.findPublicationFromUser(user);
	}

	@Override
	public List<PublicationEntity> getAllPublications() {
		return publicationRepository.findAll();
	}

	@Override
	public List<Comment> comment(String idPub, Comment comment) {
		return publicationRepository.comment(idPub, comment);
	}
	
	@Override
	public List<Comment> deleteComment(String idPub, Comment comment) {
		return publicationRepository.deleteComment(idPub, comment);
	}

	@Override
	public List<UserEntity> like(String idPub, UserEntity user) {
		PublicationEntity pub = publicationRepository.findOne(idPub);
		if(pub != null) {
			List<UserEntity> actualLikes = pub.getLikes();
			if(null != actualLikes && !actualLikes.isEmpty()) {
				if(actualLikes.contains(user)) {
					return publicationRepository.deleteLike(idPub, user);
				} else {
					return publicationRepository.addLike(idPub, user);
				}
			} else {
				return publicationRepository.addLike(idPub, user);
			}
		}
		return null;
	}

}
