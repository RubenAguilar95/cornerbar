package com.dam.proyectodam.user.wall;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.dam.proyectodam.publication.entity.PublicationEntity;

public class Wall {
	
	@DBRef
	private List<PublicationEntity> publications;

	public List<PublicationEntity> getPublications() {
		return publications;
	}

	public void setPublications(List<PublicationEntity> publications) {
		this.publications = publications;
	}

}
