package com.omerta.model;

public class Greeting {
	int id ;
	String msg ;
	
	
	public Greeting(int id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
