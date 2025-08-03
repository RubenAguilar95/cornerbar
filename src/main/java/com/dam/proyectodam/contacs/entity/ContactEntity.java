package com.dam.proyectodam.contacs.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dam.proyectodam.contacs.messages.Message;
import com.dam.proyectodam.contacs.relation.RelationType;
import com.dam.proyectodam.user.entity.UserEntity;

@Document(collection="contacs")
public class ContactEntity {

	@Id
	private String id;
	
	@DBRef
	private UserEntity user1;
	
	@DBRef
	private UserEntity user2;
	
	private RelationType relation;
	
	//TODO --> Pendiente de implementación
	private List<Message> message;
	
	
	public String getId() {
		return id;
	}
	
	public List<Message> getMessage() {
		return message;
	}
	
	public void setMessage(List<Message> message) {
		this.message = message;
	}
	
	public RelationType getRelation() {
		return relation;
	}
	
	public void setRelation(RelationType relation) {
		this.relation = relation;
	}
	
	public UserEntity getUser1() {
		return user1;
	}
	
	public void setUser1(UserEntity user1) {
		this.user1 = user1;
	}
	
	public UserEntity getUser2() {
		return user2;
	}
	
	public void setUser2(UserEntity user2) {
		this.user2 = user2;
	}
	
}
