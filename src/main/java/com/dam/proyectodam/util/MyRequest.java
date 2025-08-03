package com.dam.proyectodam.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequest extends HttpServletRequestWrapper{

	public MyRequest(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getContextPath() {
		
		return "/proyectoDAM";
	}
	@Override
	public String getServletPath() {
		return "/proyectoDAM";
	}

}
