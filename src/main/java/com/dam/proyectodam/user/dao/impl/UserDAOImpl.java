package com.dam.proyectodam.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dam.proyectodam.login.dao.UserCredentialsDAO;
import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.user.dao.UserDAO;
import com.dam.proyectodam.user.entity.UserEntity;
import com.dam.proyectodam.user.repository.UserRepository;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("userCredentialsDAO")
	private UserCredentialsDAO userCredentialsDAO;
	
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity findUserByUserCredentials(UserCredentialsEntity credentials) {
		
		return userRepository.findByUserCredentials(credentials);
	}

	@Override
	public Boolean addUser(UserEntity user) {
		if(null != userRepository.findByUserCredentials(user.getUserCredentials())){
			return Boolean.FALSE;
		}
		userRepository.save(user);
		
		return Boolean.TRUE;
	}

	@Override
	public UserEntity removeUser(String username) {
		return userRepository.removeUserByUsername(username);
	}

	@Override
	public UserEntity updateUser(UserEntity user) {
		
		return userRepository.updateUser(user);
		
	}

	@Override
	public UserEntity findById(String id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<UserEntity> findByParams(List<String> params) {
		return userRepository.findByParams(params);
	}

	@Override
	public UserEntity findByUsername(String username) {
		UserCredentialsEntity credentials = userCredentialsDAO.findUserCredentialsByUsername(username);
		return userRepository.findByUserCredentials(credentials);
	}

//	@Override
//	public List<String> follow(UserEntity follower, String toFollow) {
//		
//		if(follower.getFollows() != null && follower.getFollows().contains(toFollow)) {
//			userRepository.unFollow(follower, toFollow);
//		} else {
//			userRepository.follow(follower, toFollow);
//		}
//		
//		return userRepository.findOne(follower.getId()).getFollows();
//	}

	@Override
	public Boolean existsUsername(String username) {
		return userCredentialsDAO.findUserCredentialsByUsername(username) != null;
	}
}
