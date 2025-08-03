package com.dam.proyectodam.publication.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.content.Content;
import com.dam.proyectodam.user.entity.UserEntity;

@Document(collection = "publications")
public class PublicationEntity {

	@Id
	private String id;
	
	@DBRef
	private UserEntity owner; // String
	
	@DBRef
	private UserEntity sharer; // String
	
	/**
	 * Id del UserEntity
	 * Es el muro-perfil donde se mostrará la publicación
	 */
	private ObjectId idUserWall;
	
//	private ObjectId publisherId; // String 
	
	private Content content;
	private List<Comment> comments;

	private Date publishDate;
	
	@DBRef
	private List<UserEntity> likes;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
//	public ObjectId getPublisherId() {
//		return publisherId;
//	}
//
//	public void setPublisherId(ObjectId publisherId) {
//		this.publisherId = publisherId;
//	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public UserEntity getSharer() {
		return sharer;
	}

	public void setSharer(UserEntity sharer) {
		this.sharer = sharer;
	}

	public ObjectId getIdUserWall() {
		return idUserWall;
	}

	public void setIdUserWall(ObjectId idUserWall) {
		this.idUserWall = idUserWall;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<UserEntity> getLikes() {
		return likes;
	}

	public void setLikes(List<UserEntity> likes) {
		this.likes = likes;
	}
	
	public int countLikes() {
		return this.likes.size();
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	
}
