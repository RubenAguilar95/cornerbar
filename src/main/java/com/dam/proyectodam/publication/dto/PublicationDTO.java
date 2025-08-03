package com.dam.proyectodam.publication.dto;

import java.util.List;

import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.content.Content;
import com.dam.proyectodam.user.entity.UserEntity;

public class PublicationDTO {
	
	private String id;
	
	/* TODO De momento no se puede "Compartir" */
//	@DBRef
//	private UserEntity owner; // String
	
	private UserEntity publisher; // String 
	
	private Content content;
	private List<Comment> comments;
	
	private List<UserEntity> likes;

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public UserEntity getPublisher() {
		return publisher;
	}

	public void setPublisher(UserEntity publisher) {
		this.publisher = publisher;
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

}
