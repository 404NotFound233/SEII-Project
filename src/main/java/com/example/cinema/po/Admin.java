package com.example.cinema.po;

/**
 * Created by gumingzheng on 2019/5/31.
 */

public class Admin {
	private String username;
	
	private int id;
	
	public Admin(int id,String username) {
		this.id = id;
		this.username = username;
	}
	
	public int getId() {
	        return id;
	    }

	public void setId(int id) {
		this.id = id;
	    }
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}