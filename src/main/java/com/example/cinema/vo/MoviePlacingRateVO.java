package com.example.cinema.vo;

import java.util.Date;

/**
 * @author gmz
 * @date 2019/5/11 11:00 AM
 */
public class MoviePlacingRateVO {
	private Date date;
	/**
	 * 所有电影某天的上座率
	 * 成员变量为：
	 * 当天上座率
	 */
	
	private double placingRate;
	
	 public Date getDate() {
	        return date;
	    }

	 public void setDate(Date date) {
	        this.date = date;
	    }
	
	public Double getPlacingRate() {
		return placingRate;
	}
	
	public void setPlacingRate(int seatnum, int movienum, int audiencenum) {
		if(seatnum != 0 && movienum != 0 && audiencenum != 0) {
			this.placingRate = audiencenum / movienum / seatnum;
		}
		else {
			this.placingRate = 0.0;
		}
	}
	
}
