package com.dam.proyectodam.publication.dao;

import java.util.List;

import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public interface PublicationDAO {
	
	/**
	 * Añade una publicación a la BD
	 * @param entity
	 * @return  True --> Inserción correcta
	 * 			False --> Fallo de inserción
	 */
	Boolean addPublication(PublicationEntity entity);
	
	/**
	 * Devuelve todas las publicaciones hechas por un usuario, tanto las realizadas como las compartidas
	 * @param user
	 * @return List con todas las publicaciones hechas por un usuario
	 */
	List<PublicationEntity> getPublicationsFromUser(UserEntity user);
	
	/**
	 * Devuelve todas las publicaciones realizadas
	 * @return List con todas las publicaciones realizadas
	 */
	List<PublicationEntity> getAllPublications();
	
	/**
	 * Añade un comentario a la publicación con id idPub y devuelve la lista de comentarios actualizada
	 * @param idPub
	 * @param comment
	 * @return 
	 */
	List<Comment> comment(String idPub, Comment comment);
	
	/**
	 * Elimina un comentario de la publicación con id idPub y devuelve la lista de comentarios actualizada
	 * @param idPub
	 * @param comment
	 * @return
	 */
	List<Comment> deleteComment(String idPub, Comment comment);
	
	/**
	 * Añade un like a la publicación. Si el usuario ya estaba en la lista, elimina el like
	 * @param idPub
	 * @param user
	 * @return
	 */
	List<UserEntity> like(String idPub, UserEntity user);

}
