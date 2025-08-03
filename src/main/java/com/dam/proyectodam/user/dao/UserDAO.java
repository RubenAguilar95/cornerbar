package com.dam.proyectodam.user.dao;

import java.util.List;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.user.entity.UserEntity;

public interface UserDAO {
	
	/**
	 * Busca y devuelve todos los usuarios almacenados en BD
	 * @return List con todos los Usuarios
	 */
	List<UserEntity> findAllUsers();
	
	/**
	 * Busca el usuario que tenga las Credenciales aportadas
	 * @param credentials
	 * @return User encontrado
	 * 			null si no coinciden
	 */
	UserEntity findUserByUserCredentials(UserCredentialsEntity credentials);
	
	/**
	 * Añade un Usuario a la BD, comprobando primero que no exista
	 * @param user
	 * @return True --> Inserción correcta
	 * 			false --> Fallo, Usuario ya existe
	 */
	Boolean addUser(UserEntity user);
	
	/**
	 * Elimina un usuario y lo devuelve
	 * @param username
	 * @return
	 */
	UserEntity removeUser(String username);
	
	/**
	 * Actualiza el Usuario en BD con las propiedades del user proporcionado
	 * @param user
	 * @return User actualizado
	 */
	UserEntity updateUser(UserEntity user);
	
	/**
	 * Busca por el Id del Usuario
	 * @param id
	 * @return Usuario encontrado
	 * 			null si no existe
	 */
	UserEntity findById(String id);
	
	/**
	 * Realiza una búsqueda sobre los Usuarios con un número de filtros indeterminado.
	 * Si params es nulo, devuelve todos los Usuarios
	 * @param params
	 * @return List Usuarios encontrados
	 */
	List<UserEntity> findByParams(List<String> params);
	
	/**
	 * Busca un Usuario con el username proporcionado
	 * @param username
	 * @return Usuario encontrado
	 * 			null si no existe
	 */
	UserEntity findByUsername(String username);
	
	/**
	 * Añade el parámetro "toFollow" (username) a la lista de siguiendo (follows) del Usuario "follower".
	 * Si ya existía previamente, lo elimina de la lista
	 * @param follower
	 * @param toFollow
	 * @return List follows actualizada
	 */
//	List<String> follow(UserEntity follower, String toFollow);
	
	/**
	 * Checkea si existe el username en BD
	 * @param username
	 * @return
	 */
	Boolean existsUsername(String username);

}
