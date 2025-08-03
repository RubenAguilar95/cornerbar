package com.dam.proyectodam.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.login.facade.UserCredentialsFacade;
import com.dam.proyectodam.publication.comment.Comment;
import com.dam.proyectodam.publication.content.Content;
import com.dam.proyectodam.publication.content.ContentType;
import com.dam.proyectodam.publication.entity.PublicationEntity;
import com.dam.proyectodam.publication.facade.PublicationFacade;
import com.dam.proyectodam.test.config.AppTestConfig;
import com.dam.proyectodam.test.config.MongoTestConfig;
import com.dam.proyectodam.user.entity.UserEntity;
import com.dam.proyectodam.user.facade.UserFacade;

import junit.framework.Assert;

/*
 * Además de inyectar las dependencias spring-test y junit, hay que configurar el Build Path
 * y a�adirle la librería JUnit 4
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppTestConfig.class, MongoTestConfig.class })
public class MongoDBTest {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	@Qualifier("userFacade")
	private UserFacade userFacade;
	
	@Autowired
	@Qualifier("userCredentialsFacade")
	private UserCredentialsFacade userCredentialsFacade;
	
	@Autowired
	@Qualifier("publicationFacade")
	private PublicationFacade publicationFacade;
	

	@Test
	public void testWall() {
		UserEntity user1 = new UserEntity();
		
		UserCredentialsEntity credentials1 = new UserCredentialsEntity("username1", "psw1");
		userCredentialsFacade.addUserCredentials(credentials1);
		
		
		user1.setUserCredentials(credentials1);
		user1.setName("Name 1");
		user1.setPhone("61111");
		
		userFacade.addUser(user1);
		
		Assert.assertNotNull(userFacade.findById(user1.getId()));
		
		PublicationEntity publication = new PublicationEntity();
		
		Content content = new Content();
		content.setType(ContentType.TXT);
		content.setText("Esto es el contenido de la publicacion de prueba");
		
		publication.setOwner(user1);
		publication.setContent(content);
		publication.setIdUserWall(new ObjectId(user1.getId()));
		
		publicationFacade.addPublication(publication);
		
		Assert.assertNotNull(publicationFacade.getPublicationsFromUser(user1));
		
		System.out.println(publicationFacade.getPublicationsFromUser(user1).size());
	}
	
	@Test
	public void testMongo() {
//		mongoTemplate.getDb().dropDatabase();
		System.out.println(mongoTemplate.findAll(UserEntity.class));
		PublicationEntity publication = new PublicationEntity();
		UserEntity user1 = new UserEntity();
		UserEntity user2 = new UserEntity();
		UserEntity user3 = new UserEntity();
		UserEntity user4 = new UserEntity();
		
		List<String> prueba = new ArrayList();
		prueba.add("PRUEBITA 1");
		prueba.add("PRUEBITA 2");
		
		UserCredentialsEntity credentials1 = new UserCredentialsEntity("username1", "psw1");
		UserCredentialsEntity credentials2 = new UserCredentialsEntity("username2", "psw2");
		UserCredentialsEntity credentials3 = new UserCredentialsEntity("username3", "psw3");
		
		user1.setUserCredentials(credentials1);
		user1.setName("Name 1");
		user1.setPhone("61111");
		
		user2.setUserCredentials(credentials2);
		user2.setName("Name 2");
		user2.setPhone("62222");
		user2.setSurname1("Surname 2");
		
		user3.setUserCredentials(credentials3);
		user3.setName("Name 3");
		user3.setEmail("email_3@email.com");
		user3.setPhone("63333");
//		user3.getOwnPublications().add(publication);
		
		mongoTemplate.save(credentials1);
		mongoTemplate.save(credentials2);
		mongoTemplate.save(credentials3);
		
		mongoTemplate.save(user1);
		mongoTemplate.save(user2);
		mongoTemplate.save(user3);
		
		List<Comment> comments = new ArrayList<Comment>();
		
		Comment comment1 = new Comment();
		comment1.setOwner(user1);
		comment1.setText("COMENTARIO USUARIO 1");
		
		Comment comment2 = new Comment();
		comment2.setOwner(user2);
		comment2.setText("COMENTARIO USUARIO 2");
		
		comments.add(comment1);
		comments.add(comment2);
		
		Content content = new Content();
		content.setType(ContentType.TXT);
		content.setText("Esto es el contenido de la publicacion de prueba");
		
		
		publication.setComments(comments);
		publication.setContent(content);
		publication.setLikes(Arrays.asList(user1, user2));
//		publication.setPublisherId(new ObjectId(user3.getId()));
		
		mongoTemplate.save(publication);
		
		UserEntity user11 = mongoTemplate.findById(user3.getId(), UserEntity.class);
		
//		user11.getOwnPublications().add(publication);
//		
//		user2.getPrueba().add("pruebita 1");
//		user2.getPrueba().add("PRUEBITA 2");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(user11.getId()));
		
		Update update = new Update();
//		update.set("ownPublications", user11.getOwnPublications());
		
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(user2.getId()));
		
		Update update2 = new Update();
//		update2.set("prueba", user2.getPrueba());
		
		mongoTemplate.updateFirst(query, update, UserEntity.class);
		mongoTemplate.updateFirst(query2, update2, UserEntity.class);
		
	}
	
	@Test
	public void testFind() {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is("Name 3"));
		
		System.out.println(mongoTemplate.findOne(query, UserEntity.class));
	}
	
	@Test
	public void testFacade() {
		System.out.println(mongoTemplate.getDb().getName());
		System.out.println(userFacade.listUsers().size());
	}
	
	@Test
	public void testFindByParams() {
		List<String> params = new ArrayList<String>();
		List<String> params2 = new ArrayList<String>();
		List<String> params3 = new ArrayList<String>();
		List<String> params4 = new ArrayList<String>();
		List<String> params5 = new ArrayList<String>();
		
		params.add("Ciridae95");
		
		params2.add("Sevilla");
		
		params3.add("Sevilla");
		params3.add("Perez");
		
		params4.add("Ciudad");
		params4.add("Fasfas");
		params4.add("asF SF");
		
		params5.add("NombreP");
		params5.add("Apell1");
		params5.add("Ciudad Prueba");
		params5.add("Apell2 Apell3");
		params5.add("SOBRANTE");
		
		List<UserEntity> usersParams = userFacade.findByParams(params);
		List<UserEntity> usersParams2 = userFacade.findByParams(params2);
		List<UserEntity> usersParams3 = userFacade.findByParams(params3);
		List<UserEntity> usersParams4 = userFacade.findByParams(params4);
		List<UserEntity> usersParams5 = userFacade.findByParams(params5);
		
		System.out.println("USER PARAMS 1:");
		printListUsers(usersParams);
		Assert.assertEquals(1, usersParams.size());
		
		System.out.println("USER PARAMS 2:");
		printListUsers(usersParams2);
		Assert.assertEquals(2, usersParams2.size());
		
		System.out.println("USER PARAMS 3:");
		printListUsers(usersParams3);
		Assert.assertEquals(1, usersParams3.size());
		
		System.out.println("USER PARAMS 4:");
		printListUsers(usersParams4);
		Assert.assertEquals(0, usersParams4.size());
		
		System.out.println("USER PARAMS 5:");
		printListUsers(usersParams5);
		Assert.assertEquals(1, usersParams5.size());
	}
	
	private void printListUsers(List<UserEntity> users) {
		for(UserEntity user : users) {
			System.out.println(user.getName() + " " + user.getSurname1() + " " + user.getSurname2() + " " + user.getCity());
		}
	}

}
