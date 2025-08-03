package com.dam.proyectodam.rest;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dam.proyectodam.contacs.dto.ContactDTO;
import com.dam.proyectodam.contacs.facade.ContactFacade;
import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.login.facade.UserCredentialsFacade;
import com.dam.proyectodam.publication.facade.PublicationFacade;
import com.dam.proyectodam.rest.wrappers.ContactWrapper;
import com.dam.proyectodam.rest.wrappers.SearchParamsWrapper;
import com.dam.proyectodam.user.entity.UserEntity;
import com.dam.proyectodam.user.facade.UserFacade;
import com.dam.proyectodam.util.CookieUtils;
import com.dam.proyectodam.util.EncryptUtil;

@RestController
public class UserRESTController {
	
	@Autowired
	@Qualifier("userFacade")
	private UserFacade userFacade;
	
	@Autowired
	@Qualifier("userCredentialsFacade")
	private UserCredentialsFacade userCredentialsFacade;
	
	@Autowired
	@Qualifier("publicationFacade")
	private PublicationFacade publicationFacade;
	
	@Autowired
	@Qualifier("contactFacade")
	private ContactFacade contactFacade;
	

	/**
	 * Obtiene el usuario a partir del token almacenado en la Cookie
	 * @param token
	 * @return Usuario
	 * @throws Exception
	 */
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public @ResponseBody UserEntity getUser(@CookieValue("token") String token) throws Exception {
		if(token == null) {
			return null;
		}
		String tknDecoded = EncryptUtil.decode(token);
		String tkn = tknDecoded.split(":")[1];
		UserCredentialsEntity userCredentials = userCredentialsFacade.findUserCredentialsByToken(tkn);
		
		UserEntity user = userFacade.findUserByUserCredentials(userCredentials);
		if(null != user) {
			user.setContacts(contactFacade.getContactsFromUser(user));
		}
		
		return user;
	}
	/**
	 * Obtiene el usuario en el caso de haber seleccionado "Recu�rdame". A su vez, a�ade la Cookie de sesi�n "token" al navegador
	 * @param tknRmbMe
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userRememberMe", method = RequestMethod.GET)
	public @ResponseBody UserEntity getUserRememberMe(@CookieValue(name="rememberMe", defaultValue="DEFECTO") String tknRmbMe,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if("DEFECTO".equals(tknRmbMe)) {
			return null;
		}
		String tknDecoded = EncryptUtil.decode(tknRmbMe);
		UserCredentialsEntity userCredentials = userCredentialsFacade.findUserCredentialsByToken(tknDecoded);
		if(userCredentials == null) {
			CookieUtils.deleteCookie("rememberMe", response);
			return null;
		}
		if (userCredentials.getRememberMe()) {
			if(null == CookieUtils.getCookieValue("token", request)) {
				String tknFinal = userCredentials.getUsername() + ":" + tknDecoded;
				String value = EncryptUtil.encode(tknFinal);
				CookieUtils.addCookie("token", value, -1, response);
			}
			
			UserEntity user = userFacade.findUserByUserCredentials(userCredentials);
			if(null != user) {
				user.setContacts(contactFacade.getContactsFromUser(user));
			}
			
			return user;
		}
		return null;
	}
	
	@RequestMapping(value="getUserByUsername", method = RequestMethod.POST)
	public @ResponseBody UserEntity getUserByUsername(@RequestBody String username) {
		UserEntity user = userFacade.findByUsername(username);
		if(null != user) {
			user.setContacts(contactFacade.getContactsFromUser(user));
		}
		return user;
	}
	
	
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	public @ResponseBody UserEntity updateUser(@RequestBody UserEntity user) {
		UserEntity entity = userFacade.updateUser(user);
		if(null != entity) {
			entity.setContacts(contactFacade.getContactsFromUser(entity));
		}
		
		return entity;
	}
	
	
	@RequestMapping(value="/listUsers", method = RequestMethod.GET)
	public @ResponseBody List<UserEntity> listUsers() {
		List<UserEntity> list = userFacade.listUsers();
		
		Collections.shuffle(list);
		
		return list;
	}
	
	/**
	 * Realiza una b�squeda de usuarios a partir de un n�mero desconocido de filtros. Si viene vac�o, devuelve todos.
	 * @param paramsWrapper N� de filtros de b�squeda
	 * @return Lista de usuarios que coincidan
	 */
	@RequestMapping(value="/findUsers", method = RequestMethod.POST)
	public @ResponseBody List<UserEntity> findUsers(@RequestBody SearchParamsWrapper paramsWrapper) {
//		userFacade.fin
		List<String> params = paramsWrapper.getParams();
		System.out.println(params != null ? params.size() : "Nulo");
		if(params == null || params.isEmpty()) {
			return userFacade.listUsers();
		} else {
			return userFacade.findByParams(params);
		}
	}
	
//	@RequestMapping(value="/followUser", method = RequestMethod.POST)
//	public @ResponseBody List<ContactEntity> followUser(@RequestBody FollowWrapper followWrapper) {
//		UserEntity follower = userFacade.findById(followWrapper.getFollower().getId());
//		UserEntity toFollow = userFacade.findById(followWrapper.getToFollow().getId());
////		String toFollow = followWrapper.getToFollow();
//		
//		 
//		return Boolean.FALSE;
//	}

	@RequestMapping(value = "/followUser", method = RequestMethod.POST)
	public @ResponseBody List<ContactDTO> followUser(@RequestBody ContactWrapper contactWrapper) {
		UserEntity follower = userFacade.findById(contactWrapper.getUser1().getId());
		UserEntity toFollow = userFacade.findById(contactWrapper.getUser2().getId());

		if(null != follower && null != toFollow) {
			return contactFacade.contact(follower, toFollow);
		} 

		return null;
	}
	
}
