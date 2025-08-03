package com.dam.proyectodam.login.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;

@Repository("userCredentialsRepository")
public class UserCredentialsRepository {
	
	private static final String COLLECTION = "userCredentials";

	@Autowired
	private MongoTemplate mongoTemplate;

	
	// CRUD METHODS
	public long count() {
		return mongoTemplate.getCollection(COLLECTION).count();
	}

	public void delete(String id) {
		mongoTemplate.remove(id);
	}

	public void delete(UserCredentialsEntity entity) {
		mongoTemplate.remove(entity, COLLECTION);
	}

	public void deleteAll() {
		mongoTemplate.remove(new Query(), UserCredentialsEntity.class);
		
	}

	public boolean exists(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.exists(query, UserCredentialsEntity.class);
	}

	public UserCredentialsEntity findOne(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, UserCredentialsEntity.class);
	}

	public Boolean save(UserCredentialsEntity entity) {
		Boolean saved = Boolean.FALSE;
		if(entity instanceof UserCredentialsEntity) {
			mongoTemplate.save(entity);
			saved = Boolean.TRUE;
		}
		return saved;
	}

	public List<UserCredentialsEntity> findAll() {
		return mongoTemplate.findAll(UserCredentialsEntity.class);
	}
	
	// PERSONAL METHOD
	public UserCredentialsEntity findByToken(String token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("token").is(token));
		return mongoTemplate.findOne(query, UserCredentialsEntity.class);
	}
	
	public UserCredentialsEntity findByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, UserCredentialsEntity.class);
	}
	
	public UserCredentialsEntity findByUsernameAndPswd(String username, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username).and("password").is(password));
		return mongoTemplate.findOne(query, UserCredentialsEntity.class);
	}
	
	public UserCredentialsEntity findByUsernameAndToken(String username, String token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username).and("token").is(token));
		return mongoTemplate.findOne(query, UserCredentialsEntity.class);
	}
	
	

}
