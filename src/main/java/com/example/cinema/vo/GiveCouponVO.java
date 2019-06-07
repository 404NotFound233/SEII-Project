package com.example.cinema.vo;

import java.util.*;

/**
 * 
 * @author gumingzheng on 2019/6/1
 *
 */
public class GiveCouponVO {
	
	private List<Integer> userIdList;
	
	private int couponId;
	
	public int getCouponId() {
		return couponId;
	}
	
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	
	public List<Integer> getUserIdList() {
		return this.userIdList;
	}
	
	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}
	
}