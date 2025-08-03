package com.dam.proyectodam.publication.content;

public enum ContentType {
	TXT(1, "TXT"), IMG(2, "IMG");
	
	private int value;
	private String name;
	
	ContentType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static ContentType parse(Integer value) {
		ContentType ct = null;
		switch(value) {
		case 1:
			ct = TXT;
			break;
		case 2:
			ct = IMG;
			break;
		}
		
		return ct;
	}
}
