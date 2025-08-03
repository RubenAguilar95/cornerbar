package com.dam.proyectodam.util;

import org.apache.commons.codec.binary.Base64;

public class EncryptUtil {
	
	private static final String FORMAT = "#+";
	private static final String FORMAT_REGEXP = "#\\+";
	private static final String TO_CHANGE = "=";
	
	
	public static String format(String str) {
		return str.replaceAll(TO_CHANGE, FORMAT);
	}
	
	public static String unformat(String str) {
		return str.replaceAll(FORMAT_REGEXP, TO_CHANGE);
	}
	
	public static String encode(String str) {
		return format(new String(Base64.encodeBase64(str.getBytes())));
	}
	
	public static String decode(String str) {
		return new String(Base64.decodeBase64(unformat(str).getBytes()));
	}

}
