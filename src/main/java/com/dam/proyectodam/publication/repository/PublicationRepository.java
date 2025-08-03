package com.dam.proyectodam.publication.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.user.entity.UserEntity;

@Repository("publicationRepository")
public class PublicationRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final String COLLECTION = "publications";
	
	
	// CRUD METHODS
	
	public Boolean save(PublicationEntity entity) {
		mongoTemplate.save(entity);
		return Boolean.TRUE;
	}

	public PublicationEntity findOne(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, PublicationEntity.class);
	}

	public boolean exists(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.exists(query, PublicationEntity.class);
	}

	public List<PublicationEntity> findAll() {
		return mongoTemplate.findAll(PublicationEntity.class);
	}

	public long count() {
		return mongoTemplate.getCollection(COLLECTION).count();
	}

	public void delete(String id) {
		mongoTemplate.remove(id);
		
	}

	public void delete(PublicationEntity entity) {
		mongoTemplate.remove(entity, COLLECTION);
		
	}

	public void deleteAll() {
		mongoTemplate.remove(new Query(), PublicationEntity.class);
		
	}
	
	// PERSONAL
		
	public List<PublicationEntity> findPublicationFromUser(UserEntity user) {
		Query query = new Query();
		query.addCriteria(new Criteria()
				.orOperator(Criteria.where("owner").is(user).and("sharer").exists(false), Criteria.where("sharer").is(user)));
		
		return mongoTemplate.find(query, PublicationEntity.class);
	}
	
	public List<Comment> comment(String idPub, Comment comment) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(idPub)));
		
		Update update = new Update();
		update.push("comments", comment);
		
		mongoTemplate.updateFirst(query, update, PublicationEntity.class);
		
		return findOne(idPub).getComments();
	}
	
	public List<Comment> deleteComment(String idPub, Comment comment) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(idPub)));
		
		Update update = new Update();
		update.pull("comments", comment);
		
		mongoTemplate.updateFirst(query, update, PublicationEntity.class);
		
		return findOne(idPub).getComments();
	}
	
	public List<UserEntity> addLike(String idPub, UserEntity user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(idPub)));
		
		Update update = new Update();
		update.push("likes", user);
		
		mongoTemplate.updateFirst(query, update, PublicationEntity.class);
		
		return findOne(idPub).getLikes();
	}
	
	public List<UserEntity> deleteLike(String idPub, UserEntity user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(idPub)));
		
		Update update = new Update();
		update.pull("likes", user);
		
		mongoTemplate.updateFirst(query, update, PublicationEntity.class);
		
		return findOne(idPub).getLikes();
	}
		

}
