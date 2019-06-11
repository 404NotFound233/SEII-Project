package com.example.cinema.vo;

import com.example.cinema.po.Admin;

/**
 * Created by gumingzheng on 2019/5/31.
 */

public class AdminVO {
	private String username;
	
	private int id;
	
	public AdminVO(Admin admin) {
		this.username = admin.getUsername();
		this.id = admin.getId();
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