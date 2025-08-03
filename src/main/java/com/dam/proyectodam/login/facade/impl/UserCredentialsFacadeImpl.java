package com.dam.proyectodam.login.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dam.proyectodam.login.dao.UserCredentialsDAO;
import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.login.facade.UserCredentialsFacade;

@Service("userCredentialsFacade")
public class UserCredentialsFacadeImpl implements UserCredentialsFacade{
	
	@Autowired
	@Qualifier("userCredentialsDAO")
	private UserCredentialsDAO userCredentialsDAO;

	@Override
	public List<UserCredentialsEntity> getUsersCredentials() {
		return userCredentialsDAO.getUsersCredentials();
	}

	@Override
	public UserCredentialsEntity findUserCredentialsByToken(String token) {
		return userCredentialsDAO.findUserCredentialsByToken(token);
	}

	@Override
	public String checkLogin(String username, String password, Boolean rememberMe) {
		return userCredentialsDAO.checkLogin(username, password, rememberMe);
	}

	@Override
	public Boolean logOut(String username, String token) {
		return userCredentialsDAO.logOut(username, token);
	}

	@Override
	public Boolean addUserCredentials(UserCredentialsEntity userCredentials) {
		return userCredentialsDAO.addUserCredentials(userCredentials);
	}

}
