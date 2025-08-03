package com.dam.proyectodam.publication.facade;

import java.util.List;

import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public interface PublicationFacade {

	/**
	 * A�ade una publicaci�n a la BD
	 * @param entity
	 * @return
	 */
	Boolean addPublication(PublicationEntity entity);
	
	/**
	 * Devuelve todas las publicaciones para un usuario, tanto las propias como las 
	 * compartidas.
	 * @param user
	 * @return
	 */
	List<PublicationEntity> getPublicationsFromUser(UserEntity user);
	
	/**
	 * Devuelve todas las publicaciones de los contactos de un usuario, adem�s de las suyas,
	 * que ser�n pintadas en la home.
	 * @param user
	 * @return
	 */
	List<PublicationEntity> getHomePublications(UserEntity user);
	
	/**
	 * Devuelve todas las publicaciones
	 */
	List<PublicationEntity> getAllPublications();
	
	/**
	 * A�ade un comentario a la publicaci�n con id idPub
	 * @param idPub
	 * @param comment
	 * @return
	 */
	List<Comment> comment(String idPub, Comment comment);
	
	/**
	 * Elimina un comentario de la publicaci�n con id idPub y devuelve la lista de comentarios actualizada
	 * @param idPub
	 * @param comment
	 * @return
	 */
	List<Comment> deleteComment(String idPub, Comment comment);
	
	/**
	 * A�ade un like a la publicaci�n. Si el usuario ya estaba en la lista, elimina el like
	 * @param idPub
	 * @param username
	 * @return
	 */
	List<UserEntity> like(String idPub, String username);
}
