//package com.dam.proyectodam.publication.mapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import com.dam.proyectodam.publication.dto.PublicationDTO;
//import com.dam.proyectodam.publication.entity.PublicationEntity;
//import com.dam.proyectodam.user.facade.UserFacade;
//
//@Service
//public class PublicationEntityToDTO {
//	
//	@Autowired
//	@Qualifier("userFacade")
//	private UserFacade userFacade;
//	
//	public PublicationDTO apply(PublicationEntity entity) {
//		PublicationDTO dto = new PublicationDTO();
//		dto.setId(entity.getId());
//		dto.setComments(entity.getComments());
//		dto.setLikes(entity.getLikes());
//		dto.setPublisher(userFacade.findById(entity.getPublisherId().toHexString()));
//		dto.setContent(entity.getContent());
//		
//		return dto;
//	}
//
//}
