package com.dam.proyectodam.contacs.relation;

public enum RelationType {
	MUTUO(0, "MUTUO"), U1_U2(1, "U1_U2"), U2_U1(2, "U2_U1");
	
	private int value;
	private String name;
	
	private RelationType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	
	public static RelationType parse(Integer value) {
		RelationType rel = null;
		switch(value) {
		case 0:
			rel = MUTUO;
			break;
		case 1:
			rel = U1_U2;
			break;
		case 2:
			rel = U2_U1;
			break;
		}
		
		return rel;
	}

}
