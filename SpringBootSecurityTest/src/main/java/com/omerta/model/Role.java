package com.omerta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="onur_role")
public class Role {
	
	public Role() {}
	
	public Role(String  name) {
		super();
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id ;
	String name ;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String  getName() {
		return name;
	}
	public void setName(String  name) {
		this.name = name;
	}
	
	
	
	
	
	
}
