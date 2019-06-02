package com.example.cinema.vo;

/**
 * Created by liying on 2019/4/14.
 */
public class VIPCardForm {

    /**
     * vip卡id
     */
    private int vipId;

    /**
     * 付款金额
     */
    private double amount;

    //wqy
    private double reach;

    //wqy
    private double send;

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

    public int getVipId() {
        return vipId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double calculate(double amount) {
        int num=(int)(amount/reach);
        System.out.println(amount);
        return num*send+amount;
    }
}
