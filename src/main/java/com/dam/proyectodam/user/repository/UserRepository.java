package com.dam.proyectodam.user.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.user.entity.UserEntity;

@Repository("userRepository")
public class UserRepository {
	
	private static final String COLLECTION = "users";

	@Autowired
	private MongoTemplate mongoTemplate;

	
	// CRUD METHODS
	
	public Boolean save(UserEntity entity) {
		mongoTemplate.save(entity);
		return Boolean.TRUE;
	}

	public UserEntity findOne(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
		return mongoTemplate.findOne(query, UserEntity.class);
	}

	public boolean exists(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
		return mongoTemplate.exists(query, UserEntity.class);
	}

	public List<UserEntity> findAll() {
		return mongoTemplate.findAll(UserEntity.class);
	}

	public long count() {
		return mongoTemplate.getCollection(COLLECTION).count();
	}

	public void delete(String id) {
		mongoTemplate.remove(id);
		
	}

	public void delete(UserEntity entity) {
		mongoTemplate.remove(entity, COLLECTION);
		
	}

	public void deleteAll() {
		mongoTemplate.remove(new Query(), UserEntity.class);
		
	}
	
	// PERSONALES
	
	public UserEntity findByUserCredentials(UserCredentialsEntity credentials) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userCredentials").is(credentials));
		
		return mongoTemplate.findOne(query, UserEntity.class);
	}
	
	public UserEntity removeUserByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		
		return mongoTemplate.findAndRemove(query, UserEntity.class);
	}
	
	//TODO 
	public UserEntity updateUser(UserEntity entity) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("_id").is(entity.getId()));
//		
//		Update update = new Update();
//		update.set("ownPublications", entity.getOwnPublications());
//		
//		WriteResult wr = mongoTemplate.updateFirst(query, update, UserEntity.class);
//		return mongoTemplate.findById(entity.getId(), UserEntity.class);
		mongoTemplate.save(entity);
		return findOne(entity.getId());
	}
	
	//TODO (name).in(params)
	public List<UserEntity> findByParams(List<String> params) {
		List<UserEntity> users = new ArrayList<UserEntity>();
		
		Query query = new Query();
		
		if(params.size() == 1) {
			query.addCriteria(new Criteria()
					.orOperator(
//							Criteria.where("userCredentials.username").is(params.get(0)),
							Criteria.where("name").is(params.get(0)),
							Criteria.where("surname1").is(params.get(0)),
							Criteria.where("surname2").is(params.get(0)),
							Criteria.where("city").is(params.get(0))
					)
			);
			
			return mongoTemplate.find(query, UserEntity.class);
		} else {
			switch(params.size()) {
			case 2:
				query.addCriteria(new Criteria()
					.orOperator(
						new Criteria().andOperator(
							Criteria.where("name").in(params),
							new Criteria().orOperator(
								Criteria.where("surname1").in(params),
								Criteria.where("surname2").in(params),
								Criteria.where("city").in(params)
							)
						),
						new Criteria().andOperator(
							Criteria.where("surname1").in(params),
							new Criteria().orOperator(
								Criteria.where("surname2").in(params),
								Criteria.where("city").in(params)
							)
						),
						Criteria.where("surname2").in(params).and("city").in(params)
					)
				);
				break;
			case 3:
				query.addCriteria(new Criteria()
					.orOperator(
						new Criteria().andOperator(
							Criteria.where("name").in(params),
							Criteria.where("surname1").in(params),
							new Criteria().orOperator(
								Criteria.where("surname2").in(params),
								Criteria.where("city").in(params)
							)
						),
						new Criteria().andOperator(
							Criteria.where("surname1").in(params),
							Criteria.where("surname2").in(params),
							Criteria.where("city").in(params)
						)
					)
				);
				
				break;
				// 4 parámetros o más
			default:
				query.addCriteria(new Criteria()
					.andOperator(
						Criteria.where("name").in(params),
						Criteria.where("surname1").in(params),
						Criteria.where("surname2").in(params),
						Criteria.where("city").in(params)
					)
				);
				break;
			}
		}
		
		users.addAll(mongoTemplate.find(query, UserEntity.class));
		
		return users;
	}
	
	public Boolean follow(UserEntity follower, String toFollow) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(follower.getId())));
		
		Update update = new Update();
		update.push("follows", toFollow);
		
		mongoTemplate.updateFirst(query, update, UserEntity.class);
		
		return Boolean.TRUE;
	}
	
	public Boolean unFollow(UserEntity follower, String toFollow) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(follower.getId())));
		
		Update update = new Update();
		update.pull("follows", toFollow);
		
		mongoTemplate.updateFirst(query, update, UserEntity.class);
		
		return Boolean.TRUE;
	}

}
