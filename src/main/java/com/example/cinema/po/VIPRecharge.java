package com.example.cinema.po;


import com.example.cinema.vo.VIPRechargeRecord;

import java.sql.Timestamp;

/**
 * Created by lmt
 */

public class VIPRecharge {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡充值前余额
     */
    private double before_balance;

    /**
     * 会员卡充值金额
     */
    private double single_recharge;

    //private double extra;
    /**
     * 会员卡余额
     */
    private double after_balance;

    /**
     * 充值时间
     */
    private Timestamp joinDate;


    public VIPRecharge() {

    }

    public VIPRechargeRecord getRecord(){
        VIPRechargeRecord record=new VIPRechargeRecord();
        record.setId(this.getId());
        record.setUserId(this.getUserId());
        record.setBeforeBalance(this.getBefore_Balance());
        record.setSingle_recharge(this.getSingle_recharge());
        record.setAfterBalance(this.getAfter_Balance());
        record.setJoinDate(this.getJoinDate());
        return record;
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

    public double calculate(double amount) {
        return (int)(amount/200)*30+amount;

    }
}
