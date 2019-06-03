package com.example.cinema.po;

//import com.example.cinema.po.Coupon;
import com.example.cinema.vo.RefundVO;
import com.example.cinema.vo.MovieVO;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liying on 2019/4/20.
 */
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zl on 2019/5/24.
 */
public class Refund {
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
    //优惠电影列表
    private List<MovieVO> movieList;


	 public RefundVO getVO() {
        RefundVO vo = new RefundVO();
        vo.setId(id);
        vo.setDiscount(discount);
        vo.setFreetime(freetime);
        vo.setFulltime(fulltime);
        vo.setDiscounttime(discounttime);
        vo.setMovieList(movieList);
        return vo;

    }
	
    public Refund() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
