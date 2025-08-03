package com.dam.proyectodam.contacs.dto;

import com.dam.proyectodam.contacs.relation.RelationType;

public enum FollowType {
	
	FOLLOW(1, "FOLLOW"), NOTFOLLOW(2, "NOT FOLLOW");
	
	private int value;
	private String name;
	
	private FollowType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public static FollowType parse(Integer value) {
		FollowType fol = null;
		switch(value) {
		case 1:
			fol = FOLLOW;
			break;
		case 2:
			fol = NOTFOLLOW;
			break;
		}
		
		return fol;
	}
}
