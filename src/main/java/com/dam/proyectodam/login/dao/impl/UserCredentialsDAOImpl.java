package com.dam.proyectodam.login.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.login.dao.UserCredentialsDAO;
import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.login.repository.UserCredentialsRepository;

@Repository("userCredentialsDAO")
public class UserCredentialsDAOImpl implements UserCredentialsDAO{

//	private static List<UserCredentialsEntity> users = createUsers();
	
	@Autowired
	@Qualifier("userCredentialsRepository")
	private UserCredentialsRepository userCredentialsRepository;

//	private static List<UserCredentialsEntity> createUsers() {
//		if(users == null){
//			users = new ArrayList<UserCredentialsEntity>();
//			users.add(new UserCredentialsEntity("Feliponcio", "felipe123"));
//			users.add(new UserCredentialsEntity("Pericote", "perico123"));
//			users.add(new UserCredentialsEntity("Francisquillo", "francisco123"));
//		}
//		return users;
//	}
	
	@Override
	public List<UserCredentialsEntity> getUsersCredentials() {
		return userCredentialsRepository.findAll();
	}

	@Override
	public UserCredentialsEntity findUserCredentialsByToken(String token) {

		return userCredentialsRepository.findByToken(token);
	}

	@Override
	public String checkLogin(String username, String password, Boolean rememberMe) {
		String token = null;
		
		UserCredentialsEntity entity = userCredentialsRepository.findByUsernameAndPswd(username, password);
		
		if(null != entity) {
			token = UUID.randomUUID().toString();
			entity.setToken(token);
			entity.setRememberMe(rememberMe);
			userCredentialsRepository.save(entity);
		}
		
		return token;
	}

	@Override
	public Boolean logOut(String username, String token) {

		UserCredentialsEntity entity = userCredentialsRepository.findByUsernameAndToken(username, token);
		if(null != entity) {
			entity.setToken(null);
			entity.setRememberMe(Boolean.FALSE);
			userCredentialsRepository.save(entity);
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	@Override
	public Boolean addUserCredentials(UserCredentialsEntity userCredentials) {
		
		if(null != userCredentialsRepository.findByUsername(userCredentials.getUsername())) {
			return Boolean.FALSE;
		}
		
		return userCredentialsRepository.save(userCredentials);
	}

	@Override
	public UserCredentialsEntity findUserCredentialsByUsername(String username) {
		return userCredentialsRepository.findByUsername(username);
	}

}
