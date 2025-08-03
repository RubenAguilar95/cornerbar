//package com.dam.proyectodam.publication.mapper;
//
//import org.bson.types.ObjectId;
//
//import com.dam.proyectodam.publication.dto.PublicationDTO;
//import com.dam.proyectodam.publication.entity.PublicationEntity;
//
//public class PublicationDTOToEntity {
//	
//	public static PublicationEntity apply(PublicationDTO dto) {
//		PublicationEntity entity = new PublicationEntity();
//		entity.setId(dto.getId());
//		entity.setComments(dto.getComments());
//		entity.setLikes(dto.getLikes());
//		entity.setPublisherId(new ObjectId(dto.getPublisher().getId()));
//		entity.setContent(dto.getContent());
//		
//		return entity;
//	}
//
//}
