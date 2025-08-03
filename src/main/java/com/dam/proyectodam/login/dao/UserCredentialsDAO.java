package com.dam.proyectodam.login.dao;

import java.util.List;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;

public interface UserCredentialsDAO {
	
	List<UserCredentialsEntity> getUsersCredentials();
	
	/**
	 * Añade unas credenciales a la BD
	 * @param userCredentials
	 * @return  True si la inserción ha sido correcta
	 * 			False si ya existen esas Credenciales
	 */
	Boolean addUserCredentials(UserCredentialsEntity userCredentials);
	
	/**
	 * Busca las credenciales de un usuario a partir del token de la Cookie
	 * @param token
	 * @return Credenciales de usuario
	 */
	UserCredentialsEntity findUserCredentialsByToken(String token);
	
	/**
	 * Busca las credenciales de un usuario a partir del username
	 * @param username
	 * @return Credenciales de usuario
	 */
	UserCredentialsEntity findUserCredentialsByUsername(String username);
	
	/**
	 * Verifica que las credenciales son correctas. Si es así, genera un token random y actualiza el registro en BD
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @return  String token generado
	 * 			null Credenciales incorrectas
	 */
	String checkLogin(String username, String password, Boolean rememberMe);	
	
	/**
	 * Elimina el token de las credenciales, además se setear el RememberMe a false
	 * @param username
	 * @param token
	 * @return  True si el logout ha sido correcto
	 * 			False si no encuentra Credenciales para ese username y token
	 */
	Boolean logOut(String username, String token);

}
