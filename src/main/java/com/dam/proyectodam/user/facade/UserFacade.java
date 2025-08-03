package com.dam.proyectodam.user.facade;

import java.util.List;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public interface UserFacade {
	
	List<UserEntity> listUsers();
	
	UserEntity findUserByUserCredentials(UserCredentialsEntity credentials);
	
	Boolean addUser(UserEntity user);
	
	UserEntity removeUser(String username);
	
	UserEntity updateUser(UserEntity user);
	
	UserEntity findById(String id);
	
	List<UserEntity> findByParams(List<String> params);
	
	UserEntity findByUsername(String username);
	
//	List<String> follow(UserEntity follower, String toFollow);
	
	Boolean existsUsername(String username);


}
