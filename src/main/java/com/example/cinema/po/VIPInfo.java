package com.example.cinema.po;
import com.example.cinema.vo.VIPInfoVO;

public class VIPInfo {
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

    public double calculate(double amount) {
        return (int)(amount/reach)*send+amount;

    }

    public VIPInfoVO getVO(){
        VIPInfoVO vip_vo=new VIPInfoVO();
        vip_vo.setPrice(this.getPrice());
        vip_vo.setDescription(this.getDescription());
        vip_vo.setDiscount(this.getDiscount());
        vip_vo.setReach(this.getReach());
        vip_vo.setSend(this.getSend());
        return vip_vo;
    }
}
