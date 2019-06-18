package com.example.cinema.vo;



/**
 * Created by liying on 2019/4/15.
 */
public class VIPInfoVO {
    /**
     * 会员卡价格
     */
    private double price;

    /**
     * 会员卡描述
     */
    private String description;

    /**
     * 会员卡购票折扣
     */
    private double discount;

    /**
     * 会员卡充值优惠需满金额
     */
    private double reach;

    /**
     * 会员卡充值优惠达到需满金额后的赠送金额
     */
    private double send;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }

    public double getSend() {
        return send;
    }

    public void setSend(double send) {
        this.send = send;
    }

}
