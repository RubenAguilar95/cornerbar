package com.dam.proyectodam.login.facade;

import java.util.List;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;

public interface UserCredentialsFacade {

	/**
	 * 
	 * @return
	 */
	List<UserCredentialsEntity> getUsersCredentials();
	
	Boolean addUserCredentials(UserCredentialsEntity userCredentials);
	
	UserCredentialsEntity findUserCredentialsByToken(String token);
	
	String checkLogin(String username, String password, Boolean rememberMe);	
	
	Boolean logOut(String username, String token);
	
}
