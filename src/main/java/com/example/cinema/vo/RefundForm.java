package com.example.cinema.vo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
public class RefundForm {
	private int discount;
    private int freetime;
    private int discounttime;
    private int fulltime;
    private List<Integer> movieList;


    public RefundForm() {

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
	
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public int getDiscount() {
        return discount;
    }



    public List<Integer> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Integer> movieList) {
        this.movieList = movieList;
    }
}
