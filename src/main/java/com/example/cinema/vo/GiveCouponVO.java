package com.example.cinema.vo;

import java.util.*;

/**
 * 
 * @author gumingzheng on 2019/6/1
 *
 */
public class GiveCouponVO {
	
	private int payTarget;
	
	private int couponId;
	
	public int getCouponId() {
		return couponId;
	}
	
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	
	public int getPayTarget() {
		return this.payTarget;
	}
	
	public void setPayTarget(int payTarget) {
		this.payTarget = payTarget;
	}
	
}