package com.dam.proyectodam.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	private static final String COOKIE_PATH = "/proyectoDAM";
	
	public static void addCookie(String name, String value, Integer maxAge, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		if(null != maxAge){
			cookie.setMaxAge(maxAge.intValue());
		}
		cookie.setPath(COOKIE_PATH);
		response.addCookie(cookie);
	}
	
	public static String getCookieValue(String name, HttpServletRequest request) {
		Cookie cookie = findCookie(name, request);
		
		return null != cookie ? cookie.getValue() : null;
	}
	
//	public static void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response) {
//		Cookie cookie = findCookie(name, request);
//		if(null != cookie) {
//			cookie.setMaxAge(0);
//			response.addCookie(cookie);
//		}
//	}
	
	public static void deleteCookie(String name, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, null);
		if(null != cookie) {
			cookie.setMaxAge(0);
			cookie.setPath(COOKIE_PATH);
			response.addCookie(cookie);
		}
	}
	
	private static Cookie findCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(name)){
					return cookie;
				}
			}
		}
		return null;
	}

}
