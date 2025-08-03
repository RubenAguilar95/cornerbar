package com.dam.proyectodam.user.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.user.dao.UserDAO;
import com.dam.proyectodam.user.entity.UserEntity;
import com.dam.proyectodam.user.facade.UserFacade;

@Service("userFacade")
public class UserFacadeImpl implements UserFacade {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Override
	public List<UserEntity> listUsers() {
		return userDAO.findAllUsers();
	}

	@Override
	public UserEntity findUserByUserCredentials(UserCredentialsEntity credentials) {
		return userDAO.findUserByUserCredentials(credentials);
	}

	@Override
	public Boolean addUser(UserEntity user) {
		return userDAO.addUser(user);
	}

	@Override
	public UserEntity removeUser(String username) {
		return userDAO.removeUser(username);
	}

	@Override
	public UserEntity updateUser(UserEntity user) {
		if(user == null) {
			return null;
		}
		return userDAO.updateUser(user);
	}

	@Override
	public UserEntity findById(String id) {
		return userDAO.findById(id);
	}

	@Override
	public List<UserEntity> findByParams(List<String> params) {
		return userDAO.findByParams(params);
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

//	@Override
//	public List<String> follow(UserEntity follower, String toFollow) {
//		return userDAO.follow(follower, toFollow);
//	}

	@Override
	public Boolean existsUsername(String username) {
		return userDAO.existsUsername(username);
	}
	
}
