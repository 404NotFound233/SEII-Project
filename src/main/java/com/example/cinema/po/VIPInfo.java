package com.example.cinema.po;
import com.example.cinema.vo.VIPInfoVO;

public class VIPInfo {
    private double price;
    private String description;
    private double discount;
    private double reach;
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
