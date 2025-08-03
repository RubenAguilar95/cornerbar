package com.dam.proyectodam.contacs.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.contacs.entity.ContactEntity;
import com.dam.proyectodam.contacs.relation.RelationType;
import com.dam.proyectodam.user.entity.UserEntity;

@Repository("contactRepository")
public class ContactRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final String COLLECTION = "CONTACTS";
	
	public ContactEntity findOne(String id) {
		return mongoTemplate.findById(id, ContactEntity.class);
	}
	
	public List<ContactEntity> getContactsFromUser(UserEntity user) {
		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(
				new Criteria().andOperator(
						Criteria.where("user1").is(user),
						new Criteria().orOperator(
								Criteria.where("relation").is(RelationType.U1_U2),
								Criteria.where("relation").is(RelationType.MUTUO)
								)
				),
				new Criteria().andOperator(
						Criteria.where("user2").is(user),
						new Criteria().orOperator(
								Criteria.where("relation").is(RelationType.U2_U1),
								Criteria.where("relation").is(RelationType.MUTUO)
								)
				)
		));
		
		return mongoTemplate.find(query, ContactEntity.class);
	}
	
	public ContactEntity getContact(UserEntity user1, UserEntity user2) {
		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(
				Criteria.where("user1").is(user1).and("user2").is(user2),
				Criteria.where("user1").is(user2).and("user2").is(user1)
		));
		
		return mongoTemplate.findOne(query, ContactEntity.class);
	}
	
	
	public Boolean saveContact(ContactEntity contact) {
		mongoTemplate.save(contact);
		return Boolean.TRUE;
	}
	
	public Boolean deleteContact(ContactEntity contact) {
		mongoTemplate.remove(contact);
		return Boolean.TRUE;
	}

}
