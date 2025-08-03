package com.dam.proyectodam.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dam.proyectodam.login.entity.UserCredentialsEntity;
import com.dam.proyectodam.login.facade.UserCredentialsFacade;
import com.dam.proyectodam.user.entity.UserEntity;
import com.dam.proyectodam.user.facade.UserFacade;
import com.dam.proyectodam.util.CookieUtils;
import com.dam.proyectodam.util.EncryptUtil;

@RestController
public class LoginRESTController {
	
	@Autowired
	@Qualifier("userCredentialsFacade")
	private UserCredentialsFacade userCredentialsFacade;
	
	@Autowired
	@Qualifier("userFacade")
	private UserFacade userFacade;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody UserCredentialsEntity credentials, HttpServletResponse response) throws Exception {

		String token = userCredentialsFacade.checkLogin(credentials.getUsername(), credentials.getPassword(), credentials.getRememberMe());
		String codificada = null;
		if(null != token) {
			codificada = EncryptUtil.encode(credentials.getUsername() + ":"+token);
			CookieUtils.addCookie("token", codificada, null, response);
			if(credentials.getRememberMe()) {
				String tokenParsed = parseToken(codificada);
				CookieUtils.addCookie("rememberMe", tokenParsed, 15*24*60*60*1000, response);
			}
		}
		
		return codificada;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public @ResponseBody Boolean logout(@CookieValue String token, HttpServletRequest request,
			HttpServletResponse response) {
		String tknDecoded = EncryptUtil.decode(token);
		String username = tknDecoded.split(":")[0];
		String tkn = tknDecoded.split(":")[1];
		
		CookieUtils.deleteCookie("token", response);
		CookieUtils.deleteCookie("rememberMe", response);
		
		Boolean loggedOut = userCredentialsFacade.logOut(username, tkn);
		
		return loggedOut;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody UserEntity entity) {
		
		Boolean added = Boolean.FALSE;
		
		if(userCredentialsFacade.addUserCredentials(entity.getUserCredentials())){
			added = userFacade.addUser(entity);
		}
		
		return added ? "OK" : "El usuario ya existe";
	}
	
	private String parseToken(String token) {
		//System.out.println("PARSETOKEN --> " + token);
		String tknDecoded = EncryptUtil.decode(token);
		//System.out.println("Token decoded --> " + tknDecoded);
		String tkn = tknDecoded.split(":")[1];
		//System.out.println("TKN: " + tkn);
		String tokenParsed = EncryptUtil.encode(tkn);
		//System.out.println("TOKEN PARSED: " + tokenParsed);
		return tokenParsed;
	}
	
	@RequestMapping(value="/testCookie")
	public @ResponseBody ResponseEntity<Boolean> testCookie(HttpServletResponse response, HttpServletRequest request) {
		Cookie cookie = new Cookie("cookRESPONSE", "valueRespCook");
		cookie.setMaxAge(20000);
		cookie.setPath("/proyectoDAM");
		response.addCookie(cookie);
//		request.getCookies()[request.getCookies().length-1].setValue("SAD");
		
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
	
}
