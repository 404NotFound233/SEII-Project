package com.example.cinema.vo;

//import com.example.cinema.po.Coupon;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zl on 2019/5/24.
 */
public class RefundVO {
	//退票策略id
    private int id;
    //退票免费时间期限
    private int freetime;
    //退票打折时间期限
    private int discounttime;
    //退票全票时间期限
    private int fulltime;
	//退票价格所占百分比
	private int discount;
    /**
     * 优惠电影列表
     */
    private List<MovieVO> movieList;



    public RefundVO() {

    }

    public void setFreetime(int freetime){
        this.freetime=freetime;
    }
    public int getFreetime() {
        return freetime;
    }

    public void setDiscounttime(int discounttime){
        this.discounttime=discounttime;
    }
    public int getDiscounttime() {
        return discounttime;
    }

    public void setFulltime(int fulltime){
        this.fulltime=fulltime;
    }
    public int getFulltime() {
        return fulltime;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

	public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public List<MovieVO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieVO> movieList) {
        this.movieList = movieList;
    }

}
