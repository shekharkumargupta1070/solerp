package com.sol.erp;


public class Contact {
	String name;
	String number;
	String email;

	public Contact(String paramString1, String paramString2, String paramString3) {
		this.name = paramString1;
		this.number = paramString2;
		this.email = paramString3;
	}

	public String toString() {
		return this.name;
	}
}
