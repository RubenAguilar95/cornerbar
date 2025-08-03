package com.dam.proyectodam.rest.wrappers;

import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public class PublicationContext {
	
	private PublicationEntity publication;
	private UserEntity publisher;
	private String idUserWall;
	
	public PublicationEntity getPublication() {
		return publication;
	}
	public void setPublication(PublicationEntity publication) {
		this.publication = publication;
	}
	public UserEntity getPublisher() {
		return publisher;
	}
	public void setPublisher(UserEntity publisher) {
		this.publisher = publisher;
	}
	public String getIdUserWall() {
		return idUserWall;
	}
	public void setIdUserWall(String idUserWall) {
		this.idUserWall = idUserWall;
	}

}
