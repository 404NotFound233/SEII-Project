package com.example.cinema.vo;

import java.sql.Timestamp;

public class VIPRechargeRecord {
    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    private double before_balance;

    private double single_recharge;

    //private double extra;
    /**
     * 会员卡余额
     */
    private double after_balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;


    public VIPRechargeRecord() {

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBefore_Balance() {
        return before_balance;
    }

    public void setBeforeBalance(double before_balance) {
        this.before_balance = before_balance;
    }

    public double getSingle_recharge() {
        return single_recharge;
    }

    public void setSingle_recharge(double single_recharge) {
        this.single_recharge = single_recharge;
    }
    /**
     public double getExtra(){
     return extra;
     }
     public void setExtra(double extra){
     this.extra=extra;
     }
     **/
    public double getAfter_Balance() {
        return after_balance;
    }

    public void setAfterBalance(double after_balance) {
        this.after_balance = after_balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

}
